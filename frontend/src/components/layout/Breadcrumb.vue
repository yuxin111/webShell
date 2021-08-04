<template>
  <el-breadcrumb class="breadcrumb-layout" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item
        class="breadcrumb-item"
        v-for="matchedItem in matched"
        :key="matchedItem.path">
        {{ matchedItem.meta.title }}
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>
<script>

export default {
  name: 'Breadcrumb',
  data () {
    return {
      matched: []
    }
  },
  watch: {
    $route: {
      handler (route) {
        this.matched = route.matched.slice(1, route.meta.length)
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss" scoped>

.breadcrumb-layout {
  width: fit-content;
  min-height: 1em;
}

.breadcrumb-item {
  transition: all .5s;
}

.breadcrumb-enter,
.breadcrumb-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

.breadcrumb-leave-active {
  position: absolute;
}
</style>
