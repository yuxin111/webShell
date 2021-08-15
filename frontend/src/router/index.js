import VueRouter from 'vue-router'
import store from '@/store'
import Layout from '@/components/layout/Index'
import Login from '@/views/login/Index'
import Welcome from './modules/welcome'
import Xterm from './modules/xterm'

// const originalPush = VueRouter.prototype.push
// VueRouter.prototype.push = function push (location) {
//   return originalPush.call(this, location).catch(err => err)
// }

const routes = [
  {
    path: '/login',
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/welcome',
    children: [
      ...Welcome,
      ...Xterm
    ],
    meta: {
      validate: true
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const isLogin = store.getters['User/isLogin'] // 是否已登录
  const require2Login = JSON.parse(process.env.VUE_APP_IS_LOGIN) && to.matched.some(item => item.meta.validate) // 是否需要登录
  if (!isLogin && require2Login) {
    next('/login')
  } else {
    if (require2Login) {
      store.commit('Main/TAG_PUSH', {
        name: to.meta.title || '未知名称',
        url: to.fullPath
      })
    }
    next()
  }
})

export default router
