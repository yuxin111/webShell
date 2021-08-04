<template>
  <div>
    <transition-group
      name="tag-group-complete"
      tag="div"
      class="flex">
      <div
        v-for="(tag,i) in tagList"
        :key="tag.url"
        class="tag-content m-r-10 cursor-pointer"
        @contextmenu.prevent="openMenu($event,tag,i)">
        <el-tag
          type="info"
          size="small"
          effect="plain"
          :class="[{'tag-select': isCurRouter(tag)},'tag-item']"
          @close="closeTag(tag,i)"
          @click="switchRouter(tag)"
          :closable="tagList.length !== 1">
          {{ tag.name }}
        </el-tag>
      </div>
    </transition-group>

      <ContextMenu
        class="contextmenu"
        v-show="contextmenu.visible"
        :content="contextmenu.content"
        :top="contextmenu.top"
        :left="contextmenu.left">
      </ContextMenu>

  </div>
</template>

<script>
import ContextMenu from '@/components/ContextMenu'

export default {
  components: { ContextMenu },
  data () {
    return {
      curRoute: null,
      contextmenu: {
        visible: false,
        content: [
          {
            title: '关闭',
            icon: 'el-icon-document-delete',
            onClick: () => {
              const {
                tag,
                i
              } = this.contextmenu
              this.closeTag(tag, i)
            }
          },
          {
            title: '关闭其他',
            icon: 'el-icon-folder-delete',
            onClick: () => {
              const {
                tag,
                i
              } = this.contextmenu
              this.closeOtherTag(tag, i)
            }
          }
        ],
        tag: null,
        i: 0,
        top: 0,
        left: 0
      }
    }
  },
  mounted () {
    // document.addEventListener('click', e => {
    //   const flag = e.target.contains(document.getElementsByClassName('contextmenu')[0])
    //   if (flag) return
    //   this.contextmenu.visible = false
    // })
  },
  methods: {
    openMenu (e, tag, i) {
      this.contextmenu.visible = true
      this.contextmenu.left = e.clientX
      this.contextmenu.top = e.clientY
      this.contextmenu.tag = tag
      this.contextmenu.i = i
      document.addEventListener('click', this.foo)
    },
    foo () {
      this.contextmenu.visible = false
      document.removeEventListener('click', this.foo)
    },
    closeTag (tag, i) {
      // 删除的是当前访问的tag时
      if (this.isCurRouter(tag)) {
        if (this.tagList.length === 1) return
        // 获取最近的tag
        const nearlyTag = (i => {
          let nearlyTagIndex = 0
          const tagListLength = this.tagList.length
          nearlyTagIndex = tagListLength === i + 1 ? i - 1 : i + 1
          return this.tagList[nearlyTagIndex]
        })(i)
        this.$router.push(nearlyTag.url)
      }
      this.$store.commit('Main/TAG_DEL', tag)
    },
    closeOtherTag (tag, i) {
      // 获取要删除的其他tagList
      const inputTagList = this.tagList.filter((_, j) => j !== i)
      // 跳转到选中的tag中
      if (!this.isCurRouter(tag)) {
        this.$router.push(tag.url)
      }
      this.$store.commit('Main/TAG_DEL_LIST', inputTagList)
    },
    isCurRouter (tag) {
      return this._.isEqual(tag, this.curRoute)
    },
    switchRouter (tag) {
      if (tag.url !== this.$route.fullPath) {
        this.$router.push(tag.url)
      }
    }
  },
  computed: {
    tagList () {
      return this.$store.getters['Main/tagList']
    }
  },
  watch: {
    $route: {
      handler: function (to) {
        /**
         * 格式与tagList中对象一致，方便判断
         * @type {{name, url: string}}
         */
        this.curRoute = {
          name: to.meta.title,
          url: to.fullPath
        }
      },
      immediate: true
    }
  }
}
</script>

<style lang="scss" scoped>
.tag-content {
  display: inline-block;
  transition: all .5s;
}

.tag-group-complete-enter, .tag-group-complete-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

.tag-group-complete-leave-active {
  position: absolute;
  z-index: -1;
}

.tag-select {
  background-color: #3995f3;
  color: #fff;

  ::v-deep {
    .el-icon-close {
      color: #fff;
    }
  }
}

.tag-item {
  transition: background-color .2s;
}
</style>
