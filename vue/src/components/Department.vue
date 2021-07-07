/* eslint-disable vue/no-parsing-error */
<template>
  <div class="Department">
    <Form inline>
      <FormItem>
        <Button type="primary" @click="add_modal = true" icon="md-add">添加</Button>
      </FormItem>
    </Form>
    <Table border :columns="columns" :data="data">
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
    <Modal v-model="add_modal" title="添加部门">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="部门名称：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
        </FormItem>
        <FormItem prop="password" label="部门人数：">
          <Input type="password" v-model="add_form.number" />
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
        <FormItem label="部门名称：">
          <Input v-model="detail_form.name" type="text" readonly />
        </FormItem>
        <FormItem label="部门人数：">
          <Input v-model="detail_form.number" type="text" readonly />
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'Department',
  data () {
    return {
      user_name: '',
      columns: [
        {
          title: '部门编号',
          key: 'id'
        },
        {
          title: '部门名称',
          key: 'name'
        },
        {
          title: '部门人数',
          key: 'number'
        },
        {
          title: '操作',
          slot: 'action',
          width: 150,
          align: 'center'
        }
      ],
      data: [],
      add_modal: false,
      modal_loading: false,
      add_form: {
        id: 0,
        name: '',
        number: ''
      },
      add_rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        number: [
          { required: true, message: '请输入人数', trigger: 'blur' },
          { type: 'string', pattern: /^\d+$/, message: '请输入数字', trigger: 'blur' }
        ]
      },
      detail_modal: false,
      detail_form: {
        id: '',
        name: '',
        number: ''
      }
    }
  },
  mounted () {
    this.get_department()
  },
  methods: {
    get_department () {
      this.$http
        .get('department/find_all')
        .then(res => {
          this.data = res.data
        })
    },
    add (name) {
      this.modal_loading = true
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$http.post('department/add', this.add_form).then(res => {
            this.modal_loading = false
            if (res) {
              this.$Message.success(res.data.message)
              this.add_modal = false
              this.$refs[name].resetFields()
              this.get_department()
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
    show (row) {
      this.detail_form = row
      this.detail_modal = true
    },
    remove (id) {
      this.$http.get('department/delete?id=' + id).then(res => {
        if (res) {
          this.$Message.success(res.data.message)
          this.get_department()
        } else {
          this.$Message.error('新增失败!')
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
