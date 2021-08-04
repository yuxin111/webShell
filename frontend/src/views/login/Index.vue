<template>
  <div class="login-view">
    <div class="login-container">
      <el-card class="login-card">
        <label>管理系统</label>
        <el-divider></el-divider>
        <el-form label-width="80px" :model="loginForm">
          <el-form-item label="用户名">
            <el-input v-model="loginForm.username" placeholder="请输入用户名" clearable></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" clearable></el-input>
          </el-form-item>
        </el-form>
        <div class="login-btn flex">
          <el-button type="primary" @click="toLogin" :loading="loginLoading">登录</el-button>
          <el-button @click="reset">重置</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      loginForm: {
        username: 'yuxin',
        password: '123456'
      },
      loginLoading: false
    }
  },
  mounted () {
    this.$keymaster.bind('enter', this.toLogin)
  },
  methods: {
    toLogin () {
      this.loginLoading = true
      this.$store.dispatch('User/login', this.loginForm)
        .then(res => {
          this.$message({
            message: `欢迎回来，${res.username}`,
            type: 'success',
            showClose: true,
            duration: 1500
          })
          this.$router.push('/')
        })
        .finally(() => {
          this.loginLoading = false
        })
    },
    reset () {
      this.loginForm = {
        username: '',
        password: ''
      }
    }
  },
  beforeDestroy () {
    this.$keymaster.unbind('enter')
  }
}
</script>

<style lang="scss" scoped>
.login-view {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-color: #324057;

  .login-container {
    width: fit-content;
    margin: auto;
    position: relative;
    top: 50%;
    transform: translateY(-70%);
  }

  .login-card {
    width: 360px;
  }

  .login-btn {
    button {
      flex-grow: 1;
    }
  }
}
</style>
