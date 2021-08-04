<template>
  <div class="cursor-pointer" @click="toggleCollpase">
    <transition name="fold-transition" mode="out-in">
      <i v-if="isCollapse" class="el-icon-s-fold" key="fold"></i>
      <i v-else class="el-icon-s-unfold" key="unfold"></i>
    </transition>
  </div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  methods: {
    toggleCollpase () {
      this.$store.commit('Main/COLLAPSE_TOGGLE')
    }
  },
  computed: {
    isCollapse () {
      return this.$store.getters['Main/isCollapse']
    }
  }
}
</script>

<style lang="scss" scoped>

.fold-transition-leave-active {
  transition: all .3s;
  transform: rotate(180deg);
}

</style>
