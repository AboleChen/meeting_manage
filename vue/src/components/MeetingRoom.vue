/* eslint-disable vue/no-parsing-error */
<template>
  <div class="MeetingRoom">
    <Form inline :label-width="80">
      <FormItem label="会议室名：">
        <Input type="text" v-model="room_name" placeholder="会议室名" />
      </FormItem>
      <FormItem>
        <Button type="primary" @click="search('formInline')" icon="ios-search">查询</Button>
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
    <Page
      class="page"
      :current="page_index"
      :total="total_count"
      :page-size="page_size"
      :page-size-opts="page_size_opts"
      @on-change="page_change"
      @on-page-size-change="size_change"
      show-sizer
    />
    <Modal v-model="add_modal" title="添加会议室">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="会议室名：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
        </FormItem>
        <FormItem prop="number" label="会议室容纳人数：">
          <Input
            v-model="add_form.number"
            type="text"
            placeholder="会议室容纳人数："
          />
        </FormItem>
        <FormItem prop="status" label="状态：">
          <Input v-model="add_form.status" type="text" readonly />
        </FormItem>
      </Form>
      <div slot="footer">
        <Button size="large" :loading="modal_loading" @click="add('add_form')"
          >提交</Button
        >
        <Button size="large" @click="add_modal = false">取消</Button>
      </div>
    </Modal>
    <Modal v-model="detail_modal" title="基本信息">
      <Form :model="detail_form" :label-width="120">
        <FormItem label="会议室名：">
          <Input v-model="detail_form.name" type="text" readonly />
        </FormItem>
        <FormItem label="会议室容纳人数：">
          <Input v-model="detail_form.number" type="text" readonly />
        </FormItem>
        <FormItem label="状态：">
          <Input v-model="detail_form.status" type="text" readonly />
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script>
export default {
  name: 'MeetingRoom',
  data () {
    return {
      room_name: '',
      columns: [
        {
          title: '编号',
          key: 'id'
        },
        {
          title: '会议室名',
          key: 'name'
        },
        {
          title: '会议室容纳人数',
          key: 'number'
        },
        {
          title: '状态',
          key: 'status'
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
        number: '',
        status: '空闲'
      },
      add_rules: {
        name: [
          {
            required: true,
            message: '请输入房间名称',
            trigger: 'blur'
          }
        ],
        number: [
          {
            required: true,
            message: '请输入会议室容纳人数',
            trigger: 'blur'
          },
          {
            type: 'string',
            pattern: /^\d+$/,
            message: '请输入数字',
            trigger: 'blur'
          }
        ]
      },
      detail_modal: false,
      detail_form: {
        id: '',
        name: '',
        number: '',
        status: ''
      }
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    search () {
      this.$http
        .get(
          'meetingRoom/search?name=' +
            this.room_name +
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
          this.$http.post('meetingRoom/add', this.add_form).then(res => {
            this.modal_loading = false
            if (res.data.status === 200) {
              this.$Message.success(res.data.message)
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
    show (row) {
      this.detail_form = row
      this.detail_modal = true
    },
    remove (id) {
      this.$http.get('meetingRoom/delete?id=' + id).then(res => {
        if (res.data.status === 200) {
          this.$Message.success(res.data.message)
          this.search()
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
