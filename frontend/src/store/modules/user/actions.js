import constant from '@/utils/constant'
import cookie from 'js-cookie'
import system from '@/api/system'
import _ from 'lodash'

const login = ({ commit }, userInfo = {}) => {
  return new Promise((resolve, reject) => {
    system.login(userInfo).then(res => {
      userInfo = _.merge(userInfo, res)
      commit('USERINFO_SET', userInfo)
      cookie.set(constant.COOKIE_USERINFO, userInfo)
      console.log('当前用户信息：', userInfo)
      resolve(userInfo)
    }).catch(e => {
      reject(e)
    })
  })
}

const logout = ({ commit }) => {
  return new Promise(resolve => {
    commit('USERINFO_SET')
    cookie.remove(constant.COOKIE_USERINFO)
    commit('Main/TAG_EMPTY', '', { root: true })
    resolve('已退出登录')
  })
}

export default {
  login,
  logout
}
