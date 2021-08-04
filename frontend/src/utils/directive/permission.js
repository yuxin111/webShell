import { hasPermission } from '@/utils/common'

export default {
  inserted (el, binding) {
    const { value } = binding
    if (!hasPermission(value)) {
      el.style.display = 'none'
    }
  }
}
