/* eslint-disable vue/no-parsing-error */
<template>
  <div class="app">
    <Layout>
      <Header>
        <Row>
          <Col span="6" class="title">会议管理系统</Col>
          <Col span="1" offset="17" class="logout">
            <Tooltip content="登出" placement="bottom">
              <Button shape="circle" icon="md-contact" @click="logout">{{user}}</Button>
            </Tooltip>
          </Col>
        </Row>
      </Header>
      <Layout>
        <Sider hide-trigger :style="{ background: '#fff' }">
          <Menu
            :active-name='active_name'
            theme="light"
            width="auto"
            @on-select='select'
          >
            <MenuItem name="MeetingRoom">
              会议室管理
            </MenuItem>
            <MenuItem name="Meeting">
              会议管理
            </MenuItem>
            <MenuItem name="User">
              用户管理
            </MenuItem>
            <MenuItem name="Department">
              部门管理
            </MenuItem>
            <MenuItem name="Equipment">
              设备管理
            </MenuItem>
            <MenuItem name="PersonalData">
              个人资料
            </MenuItem>
          </Menu>
        </Sider>
        <Layout :style="{ padding: '0 24px 24px' }">
          <Breadcrumb :style="{ margin: '24px 0' }">
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>{{active_name}}</BreadcrumbItem>
          </Breadcrumb>
          <Content
            :style="{ padding: '24px', background: '#fff', height: Height }"
          >
            <MeetingRoom v-if="active_name == 'MeetingRoom'"></MeetingRoom>
            <MeetingRecord v-if="active_name == 'Meeting'"></MeetingRecord>
            <User v-if="active_name == 'User'"></User>
            <Department v-if="active_name == 'Department'"></Department>
            <Equipment v-if="active_name == 'Equipment'"></Equipment>
            <PersonalData v-if="active_name == 'PersonalData'"></PersonalData>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  </div>
</template>

<script>
import MeetingRoom from '@/components/MeetingRoom'
import MeetingRecord from '@/components/MeetingRecord'
import User from '@/components/User'
import Department from '@/components/Department'
import Equipment from '@/components/Equipment'
import PersonalData from '@/components/PersonalData'
export default {
  name: 'App',
  data () {
    return {
      active_name: 'MeetingRoom',
      title: '',
      user: ''
    }
  },
  components: {
    MeetingRoom,
    MeetingRecord,
    User,
    Department,
    Equipment,
    PersonalData
  },
  methods: {
    query_type () {
      this.$http.get('department', 'params').then(res => {
        this.title = res.data.data[0].name
      })
    },
    select (name) {
      this.active_name = name
    },
    logout () {
      this.$http.get('user/logout')
        .then(res => {
          location.reload()
        })
    },
    get_user () {
      this.$http
        .get('user/get_user')
        .then(res => {
          this.user = res.data.name
        })
    }
  },
  mounted () {
    this.get_user()
  },
  computed: {
    Height () {
      return window.innerHeight - 160 + 'px'
    }
  }
}
</script>

<style scoped>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-logo {
  width: 100px;
  height: 30px;
  background: #5b6270;
  border-radius: 3px;
  float: left;
  position: relative;
  top: 15px;
  left: 20px;
}
.layout-nav {
  width: 420px;
  margin: 0 auto;
  margin-right: 20px;
}
.title {
  font-size: 24px;
  color: #eee;
}
</style>
