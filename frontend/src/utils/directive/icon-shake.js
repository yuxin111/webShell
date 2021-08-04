import Velocity from 'velocity-animate'

export default {
  inserted (el, binding) {
    // 获取第一个图标
    const iconEl = el.getElementsByTagName('i')[0]
    if (iconEl) {
      el.onmouseenter = e => {
        Velocity(iconEl, { rotateZ: '90deg' }, { loop: 1 })
      }
      el.onmouseleave = e => {
      }
    }
  }
}
