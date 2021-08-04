<template>
  <el-container class="layout-base">
    <el-header>
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside width="auto" class="aside-base">
        <Aside></Aside>
      </el-aside>
      <el-main>
        <div class="breadcrumb-container flex-align-center">
          <FoldIcon class="m-r-10"></FoldIcon>
          <Breadcrumb></Breadcrumb>
        </div>
        <div class="tag-container">
          <History></History>
        </div>
        <div class="main-container">
          <router-view></router-view>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from './Aside'
import Header from './Header'
import Breadcrumb from './Breadcrumb'
import FoldIcon from './FoldIcon'
import History from './History'

export default {
  components: {
    Aside,
    Header,
    Breadcrumb,
    FoldIcon,
    History
  }
}
</script>

<style lang="scss" scoped>
.layout-base {
  min-height: 100vh;
  overflow: hidden;

  .aside-base {
    min-height: calc(100vh - 60px);
  }

  .breadcrumb-container {
    padding: 10px;
    background-color: #e2e6ea;
  }

  .tag-container {
    padding: 6px 0 6px 10px;
    background-color: #f3f3f3;
  }

  .main-container {
    padding: 15px;
  }

  .el-main{
    height: calc(100vh - 60px);
    overflow: scroll;
  }

  .el-main, .el-header {
    padding: 0;
  }

}
</style>
