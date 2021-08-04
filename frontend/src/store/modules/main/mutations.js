import _ from 'lodash'

/**
 * 改变侧边栏collapse状态
 */
const COLLAPSE_TOGGLE = (state, count = null) => {
  state.isCollapse = count == null ? !state.isCollapse : count
}

/**
 * 往tagList中新增一个tag
 * @example { name: 详情页面, url: /detail(路由、唯一键) }
 */
const TAG_PUSH = (state, tag) => {
  const tagList = state.tagList
  if (!_.some(tagList, tag)) {
    tagList.push(tag)
  }
}

/**
 * 往tagList中删除一个tag
 * @example { name: 详情页面, url: /detail(路由、唯一键) }
 */
const TAG_DEL = (state, tag) => {
  const tagList = state.tagList
  _.remove(tagList, tagItem => {
    return _.eq(tagItem, tag)
  })
  state.tagList = []
  state.tagList = tagList
}

/**
 * 往tagList中删除几个tag
 * @example { name: 详情页面, url: /detail(路由、唯一键) }
 */
const TAG_DEL_LIST = (state, inputTagList) => {
  const urls = inputTagList.map(tag => tag.url)
  const tagList = state.tagList
  _.remove(tagList, tagItem => {
    return urls.includes(tagItem.url)
  })
  state.tagList = []
  state.tagList = tagList
}

/**
 * 清空tagList
 */
const TAG_EMPTY = state => {
  state.tagList = []
}

export default {
  COLLAPSE_TOGGLE,
  TAG_PUSH,
  TAG_DEL,
  TAG_DEL_LIST,
  TAG_EMPTY
}
