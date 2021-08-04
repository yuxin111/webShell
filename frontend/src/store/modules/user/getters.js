import cookie from 'js-cookie'
import constant from '@/utils/constant'
import _ from 'lodash'

const userInfo = state => {
  return !_.isEmpty(state.userInfo)
    ? state.userInfo
    : cookie.get(constant.COOKIE_USERINFO)
      ? JSON.parse(cookie.get(constant.COOKIE_USERINFO))
      : {}
}
const isLogin = state => !_.isEmpty(state.userInfo) || !!cookie.get(constant.COOKIE_USERINFO)
export default {
  userInfo,
  isLogin
}
