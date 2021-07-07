/* eslint-disable vue/no-parsing-error */
<template>
  <div class="Equipment">
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
    <Modal v-model="add_modal" title="添加设备">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="设备名称：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
        </FormItem>
      </Form>
      <div slot="footer">
        <Button size="large" :loading="modal_loading" @click="add('add_form')"
          >提交</Button
        >
        <Button size="large" @click="add_modal = false">取消</Button>
      </div>
    </Modal>
    <Modal v-model="detail_modal" title="设备信息">
      <Form :model="detail_form" :label-width="120">
        <FormItem label="设备名称：">
          <Input v-model="detail_form.name" type="text" readonly />
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'Equipment',
  data () {
    return {
      user_name: '',
      columns: [
        {
          title: '设备编号',
          key: 'id'
        },
        {
          title: '设备名称',
          key: 'name'
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
        name: ''
      },
      add_rules: {
        name: [
          {required: true, message: '请输入设备名称', trigger: 'blur'}
        ]
      },
      detail_modal: false,
      detail_form: {
        id: '',
        name: ''
      }
    }
  },
  mounted () {
    this.get_equipment()
  },
  methods: {
    get_equipment () {
      this.$http
        .get('equipment/find_all')
        .then(res => {
          this.data = res.data
        })
    },
    add (name) {
      this.modal_loading = true
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$http.post('equipment/add', this.add_form).then(res => {
            this.modal_loading = false
            if (res) {
              this.$Message.success(res.data.message)
              this.add_modal = false
              this.$refs[name].resetFields()
              this.get_equipment()
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
      this.$http.get('equipment/delete?id=' + id).then(res => {
        if (res) {
          this.$Message.success(res.data.message)
          this.get_equipment()
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
