<template>
  <div v-if="isShowMenu(item)">
    <!-- 单层菜单 -->
    <el-menu-item v-if="!item.children || item.children.length === 0" :index="item.path">
      <i v-if="item.meta.icon" :class="item.meta.icon"></i>
      <span slot="title">{{ item.meta.title }}</span>
    </el-menu-item>
    <!-- 多层菜单 -->
    <el-submenu v-else :index="item.path" popper-append-to-body>
      <template slot="title">
        <i v-if="item.meta.icon" :class="item.meta.icon"></i>
        <span slot="title">{{ item.meta.title }}</span>
      </template>
      <AsideBarItem
        v-for="child in item.children"
        :key="child.path"
        :item="child">
      </AsideBarItem>
    </el-submenu>
  </div>
</template>

<script>
import { hasPermission } from '@/utils/common'
import { mapGetters } from 'vuex'

export default {
  name: 'AsideBarItem',
  props: {
    item: {
      type: Object,
      require: true,
      default: () => {
        return {}
      }
    }
  },
  methods: {
    isShowMenu (item) {
      return hasPermission(item.meta.permission, this.userInfo.menus)
    }
  },
  computed: {
    ...mapGetters({
      userInfo: 'User/userInfo'
    })
  }
}

</script>

<style scoped>
.el-menu--collapse > div > .el-submenu > .el-submenu__title span {
  height: 0;
  width: 0;
  overflow: hidden;
  visibility: hidden;
  display: inline-block;
}

.el-menu--collapse > div > .el-submenu > .el-submenu__title .el-submenu__icon-arrow {
  display: none;
}
</style>
