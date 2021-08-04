import Vue from 'vue'
import permission from './permission'
import iconRotate from './icon-rotate'
import iconShake from './icon-shake'

const install = function (Vue) {
  Vue.directive('permission', permission)
  Vue.directive('iconRotate', iconRotate)
  Vue.directive('iconShake', iconShake)
}

if (window.Vue) {
  window.permission = permission
  window.iconRotate = iconRotate
  window.iconShake = iconShake
  Vue.use(install)
}

export default install
