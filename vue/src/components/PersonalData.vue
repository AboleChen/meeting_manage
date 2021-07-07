<template>
  <div class="PersonData">
    <Card title="个人信息" style="width: 50%">
      <Form
        ref="add_form"
        :model="add_form"
        :rules="add_rules"
        :label-width="120"
      >
        <FormItem prop="name" label="用户名：">
          <Input v-model="add_form.name" type="text" placeholder="会议室名" />
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
      </Form>
      <div slot="footer">
        <Button size="large" @click="update('add_form')"
          >提交</Button
        >
        <Button size="large" @click="reset">重置</Button>
      </div>
    </Card>
  </div>
</template>

<script>
export default {
  name: 'PersonData',
  data () {
    return {
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
      user: {},
      add_rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
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
        ]
      }
    }
  },
  mounted () {
    this.get_user()
  },
  methods: {
    get_user () {
      this.$http
        .get('user/get_user')
        .then(res => {
          this.add_form = res.data
          this.user = res.data
        })
    },
    update (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.$http.post('user/update', this.add_form).then(res => {
            if (res) {
              this.$Message.success('修改成功')
              this.get_user()
            } else {
              this.$Message.error('修改失败!')
            }
          })
        } else {
          this.modal_loading = false
          this.$Message.error('修改失败!')
        }
      })
    },
    reset () {
      this.add_form = this.user
    }
  }
}
</script>

<style scoped>
</style>
