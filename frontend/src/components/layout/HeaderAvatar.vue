<template>
  <el-dropdown @command="handleCommand" class="cursor-pointer">
    <el-avatar :src="avatarDefault"></el-avatar>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="info" icon="el-icon-user-solid">个人信息</el-dropdown-item>
      <el-dropdown-item command="logout" icon="el-icon-delete-solid">退出登录</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import avatarDefault from '@/assets/avatar_default.png'

export default {
  name: 'HeaderAvatar',
  data () {
    return {
      avatarDefault
    }
  },
  methods: {
    handleCommand (command) {
      if (command === 'info') {
        this.$message({
          message: '暂未开发',
          type: 'warning',
          showClose: true,
          duration: 1500
        })
      } else if (command === 'logout') {
        this.$store.dispatch('User/logout')
          .then(res => {
            this.$message({
              message: res,
              type: 'success',
              showClose: true,
              duration: 1500
            })
            this.$router.push('/login')
          })
          .catch(e => {
            this.$message({
              message: e.message || '退出登录发生异常，请联系管理员',
              type: 'error',
              showClose: true,
              duration: 1500
            })
          })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
