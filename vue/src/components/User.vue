/* eslint-disable vue/no-parsing-error */
<template>
  <div class="User">
    <Form inline :label-width="80">
      <FormItem label="用户名：">
        <Input type="text" v-model="user_name" placeholder="用户名" />
      </FormItem>
      <FormItem>
        <Button type="primary" @click="search()" icon="ios-search">查询</Button>
        <Button type="primary" @click="add_modal = true" icon="md-add">添加</Button>
        <Button type="primary" @click="export_data" icon="md-add">导出用户信息</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns" :data="data" ref="table">
      <template slot-scope="{ row, index }" slot="action">
        <Button
          type="primary"
          size="small"
          style="margin-right: 5px"
          @click="show(row)"
          >查看</Button
        >
        <Button type="error" size="small" @click="remove(row.id)">删除</Button>
      </template>
    </Table>
    <Page class="page"
      :current="page_index"
      :total="total_count"
      :page-size="page_size"
      :page-size-opts="page_size_opts"
      @on-change="page_change"
      @on-page-size-change="size_change"
      show-sizer
    />
    <Modal v-model="add_modal" title="添加用户">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="用户名：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
        </FormItem>
        <FormItem prop="password" label="密码">
          <Input type="password" v-model="add_form.password" />
        </FormItem>
        <FormItem prop="passwordCheck" label="确认密码">
          <Input type="password" v-model="add_form.passwordCheck" />
        </FormItem>
        <FormItem prop="age" label="年龄">
          <Input v-model="add_form.age" type="text" />
        </FormItem>
        <FormItem prop="gender" label="性别：">
          <RadioGroup v-model="add_form.gender">
                <Radio label="male">男</Radio>
                <Radio label="female">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem prop="telephone" label="手机号码：">
          <Input v-model="add_form.telephone" type="text" />
        </FormItem>
        <FormItem prop="email" label="邮箱：">
          <Input v-model="add_form.email" type="text" />
        </FormItem>
        <FormItem prop="role" label="角色：">
          <Input v-model="add_form.role" type="text" />
        </FormItem>
        <FormItem prop="department" label="所属部门：">
          <Select v-model="add_form.department" style="width:200px">
              <Option v-for="item in department_list" :value="item.name" :key="item.name">{{ item.name }}</Option>
          </Select>
        </FormItem>
      </Form>
      <div slot="footer">
        <Button size="large" :loading="modal_loading" @click="add('add_form')"
          >提交</Button
        >
        <Button size="large" @click="add_modal = false">取消</Button>
      </div>
    </Modal>
    <Modal v-model="detail_modal" title="用户信息">
      <Form :model="detail_form" :label-width="120">
        <FormItem label="用户名：">
          <Input v-model="detail_form.name" type="text" readonly />
        </FormItem>
        <FormItem label="年龄：">
          <Input v-model="detail_form.age" type="text" readonly />
        </FormItem>
        <FormItem label="性别：">
          <RadioGroup v-model="detail_form.gender">
                <Radio label="male">男</Radio>
                <Radio label="female">女</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="手机号码：">
          <Input v-model="detail_form.telephone" type="text" readonly />
        </FormItem>
        <FormItem label="邮箱：">
          <Input v-model="detail_form.email" type="text" readonly />
        </FormItem>
        <FormItem label="角色：">
          <Input v-model="detail_form.role" type="text" readonly />
        </FormItem>
        <FormItem label="所属部门：">
          <Input v-model="detail_form.department" type="text" readonly />
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'User',
  data () {
    return {
      user_name: '',
      columns: [
        {
          title: '编号',
          key: 'id'
        },
        {
          title: '用户名',
          key: 'name'
        },
        {
          title: '年龄',
          key: 'age'
        },
        {
          title: '性别',
          key: 'gender',
          render: (h, params) => {
            return h('div', {}, params.row.gender === 'male' ? '男' : '女')
          }
        },
        {
          title: '手机号码',
          key: 'telephone'
        },
        {
          title: '邮箱',
          key: 'email'
        },
        {
          title: '角色',
          key: 'role'
        },
        {
          title: '所属部门',
          key: 'department'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      data: [],
      page_index: 1,
      page_size: 10,
      total_count: 100,
      page_size_opts: [10, 20, 50],
      add_modal: false,
      modal_loading: false,
      add_form: {
        id: 0,
        name: '',
        password: '',
        passwordCheck: '',
        age: '',
        gender: '',
        telephone: '',
        email: '',
        role: '',
        department: ''
      },
      add_rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          { validator: this.validatePass, trigger: 'blur' }
        ],
        passwordCheck: [
          { validator: this.validatePassCheck, trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { type: 'string', pattern: /^\d+$/, message: '请输入数字', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { type: 'string', pattern: /^\d+$/, message: '请输入数字', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ],
        role: [
          { required: true, message: '请输入角色', trigger: 'blur' }
        ],
        department: [
          { required: true, message: '请选择部门', trigger: 'change' }
        ]
      },
      department_list: [],
      detail_modal: false,
      detail_form: {
        id: 0,
        name: '',
        age: '',
        gender: '',
        telephone: '',
        email: '',
        role: '',
        department: ''
      }
    }
  },
  mounted () {
    this.search()
    this.get_department()
  },
  methods: {
    search () {
      this.$http
        .get(
          'user/search?name=' +
            this.user_name +
            '&page_index=' +
            this.page_index +
            '&page_size=' +
            this.page_size
        )
        .then(res => {
          this.total_count = res.data.total
          this.data = res.data.records
        })
    },
    validatePass (rule, value, callback) {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.add_form.passwordCheck !== '') {
          // 对第二个密码框单独验证
          this.$refs.add_form.validateField('passwordCheck')
        }
        callback()
      }
    },
    validatePassCheck (rule, value, callback) {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.add_form.password) {
        callback(new Error('两次密码输入不一致！'))
      } else {
        callback()
      }
    },
    get_department () {
      this.$http
        .get('department/find_all')
        .then(res => {
          this.department_list = res.data
        })
    },
    page_change (index) {
      this.page_index = index
      this.search()
    },
    size_change (index) {
      this.page_size = index
      this.search()
    },
    add (name) {
      this.modal_loading = true
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$http.post('user/add', this.add_form).then(res => {
            this.modal_loading = false
            if (res) {
              this.$Message.success('新增成功')
              this.add_modal = false
              this.$refs[name].resetFields()
              this.search()
            } else {
              this.$Message.error('新增失败!')
            }
          })
        } else {
          this.modal_loading = false
          this.add_modal = true
          this.$Message.error('提交失败!')
        }
      })
    },
    export_data () {
      this.$refs.table.exportCsv({
        filename: '用户信息'
      })
    },
    show (row) {
      this.detail_form = row
      this.detail_modal = true
    },
    remove (id) {
      this.$http.get('user/delete?id=' + id).then(res => {
        if (res) {
          this.$Message.success('删除成功')
          this.search()
        } else {
          this.$Message.error('删除失败!')
        }
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.page {
  margin-top: 10px;
  text-align: right
}
</style>
