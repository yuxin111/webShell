<template>
  <el-menu
    class="the-el-menu"
    router
    :collapse="isCollapse"
    :default-active="$route.path"
    :background-color="backgroundColor"
    :text-color="textColor"
    :active-text-color="activeTextColor"
    unique-opened>
    <AsideBarItem v-for="route in routes" :key="route.path" :item="route"></AsideBarItem>
  </el-menu>
</template>

<script>
import constant from '@/utils/constant'
import router from '@/router'
import AsideBarItem from '@/components/layout/AsideBarItem'

export default {
  name: 'Aside',
  components: { AsideBarItem },
  data () {
    return {
    }
  },
  computed: {
    routes () {
      return router.options.routes.find(route => route.path === '/').children
    },
    isCollapse () {
      return this.$store.getters['Main/isCollapse']
    },
    backgroundColor () {
      return constant.NAV_MENU_BACKGROUND_COLOR
    },
    textColor () {
      return constant.NAV_MENU_TEXT_COLOR
    },
    activeTextColor () {
      return constant.NAV_MENU_ACTIVE_TEXT_COLOR
    }
  }
}
</script>

<style lang="scss" scoped>
  .the-el-menu{
    min-height: 100%;
    text-align: start;
    overflow-x: hidden;
    &:not(.el-menu--collapse) {
      width: 175px;
    }
  }
</style>
