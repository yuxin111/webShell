import store from '@/store'

/**
 * 树形结构数据 <- 列表
 * @param list 列表
 * @param id 当前项id名称，与parentId对应
 * @param parentId 父项id名称，与id对应
 * @param children 子列表名称
 * @returns {*}
 */
function formatToTree (list, id = 'id', parentId = 'parentId', children = 'children') {
  return list.filter(parent => {
    const branchArr = list.filter(child => {
      return parent[id] === child[parentId]
    })
    if (branchArr.length !== 0) {
      parent[children] = branchArr
    } else {
      delete parent[children]
    }
    return !parent[parentId]
  })
}

/**
 * 是否拥有权限（菜单）
 * @param menuName 菜单名。
 *        例：'system:user:list'
 * @param menus 权限列表，通常在登录时获取。不传时默认为当前登录用户的权限列表
 *        例：['system:user:list','system:user:add','system:user:edit'...]
 */
function hasPermission (menuName = '', menus = []) {
  if (menus.length === 0) {
    menus = store.getters['User/userInfo'].menus || []
  }
  return menuName ? menus.includes(menuName) : true
}

export { formatToTree, hasPermission }
