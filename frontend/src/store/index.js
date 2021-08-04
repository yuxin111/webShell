import Vuex from 'vuex'
import Main from './modules/main'
import User from './modules/user'

export default new Vuex.Store({
  modules: {
    Main,
    User
  }
})
