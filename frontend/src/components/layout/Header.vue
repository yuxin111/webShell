<template>
  <div class="header flex-justify-end flex-align-center">
    <HeaderAvatar v-show="headerAvatarShow" class="header-avatar m-r-15"></HeaderAvatar>
  </div>
</template>

<script>
import HeaderAvatar from '@/components/layout/HeaderAvatar'

export default {
  name: 'Header',
  components: { HeaderAvatar },
  data () {
    return {
      headerAvatarShow: JSON.parse(process.env.VUE_APP_IS_LOGIN)
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  height: 100%;
  background-color: #646a75;
}
</style>
