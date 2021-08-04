import actions from './actions'
import getters from './getters'
import mutations from './mutations'

const state = {
  isCollapse: false, // 侧边栏是否收缩
  tagList: [] // tag列表，{ name: 详情页面, url: /detail(路由、唯一键) }
}

export default {
  namespaced: true,
  state,
  actions,
  getters,
  mutations
}
