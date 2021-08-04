<template>
  <div class="contextmenu" :style="{left: left + 'px',top: top + 'px'}">
    <div class="contextmenu-item" v-for="(cont,i) in content" :key="i" @click="doSomething(cont)">
      <i :class="cont.icon" class="contextmenu-item__icon"></i>
      <div class="contextmenu-item__title">{{ cont.title }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ContextMenu',
  props: {
    content: Array,
    top: {
      type: Number,
      default: () => 0
    },
    left: {
      type: Number,
      default: () => 0
    }
  },
  methods: {
    doSomething (cont) {
      if (cont.onClick) {
        cont.onClick()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.contextmenu {
  position: fixed;

  width: 200px;
  font-size: 14px;
  background-color: #fff;
  z-index: 2;
  border: 1px solid #ececec;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgb(0 0 0 / 12%), 0 0 6px rgb(0 0 0 / 4%);

  .contextmenu-item {
    height: 35px;
    display: flex;
    align-items: center;

    &__icon {
      width: 30px;
      text-align: center;
    }

    &:hover {
      background: #ecf5ff;
      color: #409eff;
      cursor: pointer;
    }
  }
}
</style>
