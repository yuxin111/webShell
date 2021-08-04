// 全局参数
const path = require('path')
const resolve = dir => path.join(__dirname, dir)
const isCompressionGzip = JSON.parse(process.env.VUE_APP_IS_COMPRESSION_GZIP)

// 引入webpack-bundle-analyzer
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin

// 引入compression-webpack-plugin
const CompressionWebpackPlugin = require('compression-webpack-plugin')
const productionGzipExtensions = /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i

module.exports = {
  publicPath: '/',
  lintOnSave: true,
  productionSourceMap: false,
  configureWebpack: config => {
    const plugins = []
    // gzip压缩
    if (isCompressionGzip) {
      plugins.push(
        // new CompressionWebpackPlugin({
        //   // filename: '[path].gz[query]',
        //   deleteOriginalAssets: false,
        //   algorithm: 'gzip',
        //   test: productionGzipExtensions,
        //   threshold: 10240, // 10k以下不压缩
        //   minRatio: 0.8
        // })
      )
    }
    config.plugins = [...config.plugins, ...plugins]
    // CDN依赖分离
    config.externals = {
      vue: 'Vue',
      vuex: 'Vuex',
      'vue-router': 'VueRouter',
      'element-ui': 'ELEMENT'
    }
  },
  chainWebpack: config => {
    // 依赖分析
    if (process.env.use_analyzer) {
      config
        .plugin('webpack-bundle-analyzer')
        .use(new BundleAnalyzerPlugin())
    }
    // 添加别名
    config.resolve.alias
      .set('@', resolve('src'))
  }
}
