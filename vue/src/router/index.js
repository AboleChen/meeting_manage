import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import axios from 'axios'

Vue.use(Router)
Vue.prototype.$http = axios
Vue.prototype.$http = axios.create({
  // 打包时配置
  // baseURL: '/meeting'
  // 本地运行时配置
  baseURL: 'http://127.0.0.1:8090/meeting'
})

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    }
  ]
})
