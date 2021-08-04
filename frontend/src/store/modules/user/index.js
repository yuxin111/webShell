import actions from './actions'
import getters from './getters'
import mutations from './mutations'

const state = {
  userInfo: {} // 用户信息
}

export default {
  namespaced: true,
  state,
  actions,
  getters,
  mutations
}
