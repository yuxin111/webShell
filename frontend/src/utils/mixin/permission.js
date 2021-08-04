import { hasPermission } from '@/utils/common'

export default {
  methods: {
    $_hasPermission (value) {
      return hasPermission(value)
    }
  }
}
