/**
 * @author YuXin
 * @desc 封装axios
 */
import axios from 'axios'
import { Message } from 'element-ui'
import store from '@/store'
import router from '@/router'
import _ from 'lodash'
// axios.defaults.headers.post['Content-type'] = 'application/json'

const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: process.env.VUE_APP_REQUEST_TIMEOUT,
  withCredentials: false
})

service.interceptors.request.use(
  config => {
    const userInfo = store.getters['User/userInfo'] || {}
    if (!_.isEmpty(userInfo)) {
      config.headers.Token = userInfo.token
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

service.interceptors.response.use(
  response => {
    const res = response.data
    // 下载请求
    if (res.type === 'application/octet-stream') {
      return Promise.resolve(response)
    }

    // 错误处理
    if (res.code !== 200) {
      Message({
        message: res.message || res.msg || 'Error',
        type: 'error',
        duration: process.env.VUE_APP_REQUEST_DURATION
      })
      // shiro权限拦截错误
      if (res.code === 46000) {
        store.dispatch('User/logout').then(() => {
          router.push('/login')
        })
      }
      return Promise.reject(new Error(res.message || res.msg || 'Error'))
    } else {
      return Promise.resolve(res.result)
    }
  },
  error => {
    error.message = error.message === 'Network Error' ? '网络连接异常' : error.message
    Message({
      message: error.message || error.msg,
      type: 'error',
      duration: process.env.VUE_APP_REQUEST_DURATION
    })
    return Promise.reject(error)
  }
)

export default service
