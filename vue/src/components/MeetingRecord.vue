/* eslint-disable vue/no-parsing-error */
<template>
  <div class="Meeting">
    <Form inline :label-width="80">
      <FormItem label="会议名：">
        <Input type="text" v-model="meeting_name" placeholder="会议名" />
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
    <Page class="page"
      :current="page_index"
      :total="total_count"
      :page-size="page_size"
      :page-size-opts="page_size_opts"
      @on-change="page_change"
      @on-page-size-change="size_change"
      show-sizer
    />
    <Modal v-model="add_modal" title="添加会议">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="会议名称：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
        </FormItem>
        <FormItem prop="begin" label="开始时间：">
          <DatePicker v-model="add_form.begin" type="datetime" @on-change="choose_time"></DatePicker>
        </FormItem>
        <FormItem prop="end" label="结束时间：">
          <DatePicker v-model="add_form.end" type="datetime" @on-change="choose_time"></DatePicker>
        </FormItem>
        <FormItem prop="room" label="会议室：">
          <Select v-model="add_form.room" style="width:200px">
            <Option v-for="item in roomList" :value="item.id" :key="item.id">{{ item.name }}</Option>
          </Select>
        </FormItem>
        <FormItem prop="equipment" label="设备：">
          <Select v-model="add_form.equipment" style="width:200px">
              <Option v-for="item in equipList" :value="item.id" :key="item.id">{{ item.name }}</Option>
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
  name: 'Meeting',
  data () {
    return {
      meeting_name: '',
      columns: [
        {
          title: '编号',
          key: 'id'
        },
        {
          title: '会议名',
          key: 'name'
        },
        {
          title: '预订人',
          key: 'user'
        },
        {
          title: '会议室名',
          key: 'room'
        },
        {
          title: '设备',
          key: 'equipment'
        },
        {
          title: '开始时间',
          key: 'begin',
          render: (h, params) => {
            return h('div',
              this.formatDate(new Date(params.row.begin), 'yyyy-MM-dd hh:mm')
            )
          }
        },
        {
          title: '结束时间',
          key: 'end',
          render: (h, params) => {
            return h('div',
              this.formatDate(new Date(params.row.end), 'yyyy-MM-dd hh:mm')
            )
          }
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
        begin: '',
        end: '',
        room: '',
        equipment: ''
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
        ],
        room: [
          { required: true, message: '请选择会议室', trigger: 'change' }
        ],
        equipment: [
          { required: true, message: '请选择设备', trigger: 'change' }
        ]
      },
      roomList: [],
      equipList: [],
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
    this.get_meetingRoom()
    this.get_equipment()
  },
  methods: {
    search () {
      this.$http
        .get(
          'meetingRecord/search?name=' +
            this.meeting_name +
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
    get_meetingRoom () {
      this.$http
        .get('meetingRoom/find_all')
        .then(res => {
          this.roomList = res.data.data
        })
    },
    get_equipment () {
      this.$http
        .get('equipment/find_all')
        .then(res => {
          this.equipList = res.data.data
        })
    },
    choose_time () {
      if (this.add_form.begin !== '' && this.add_form.end !== '') {
        if (this.add_form.begin > this.add_form.end) {
          this.$Message.warning('结束时间必须大于开始时间!')
        } else {
          var begin = this.formatDate(new Date(this.add_form.begin), 'yyyy-MM-dd hh:mm:ss')
          var end = this.formatDate(new Date(this.add_form.end), 'yyyy-MM-dd hh:mm:ss')
          this.$http.get('meetingRecord/filter_room?begin_time=' + begin + '&end_time=' + end).then(res => {
            this.roomList = res.data
          })
          this.$http.get('meetingRecord/filter_equip?begin_time=' + begin + '&end_time=' + end).then(res => {
            this.equipList = res.data
          })
        }
      }
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
          this.$http.post('meetingRecord/add', this.add_form).then(res => {
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
    },
    formatDate (date, fmt) {
      let o = {
        'M+': date.getMonth() + 1, // 月份
        'd+': date.getDate(), // 日
        'h+': date.getHours(), // 小时
        'm+': date.getMinutes(), // 分
        's+': date.getSeconds(), // 秒
        'S': date.getMilliseconds() // 毫秒
      }
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
        }
      }
      return fmt
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
