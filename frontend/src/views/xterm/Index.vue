<template>
  <div class="xterm-view">
    <div id="terminal" ref="terminal"></div>
  </div>
</template>

<script>
import { Terminal } from 'xterm'
// import { FitAddon } from 'xterm-addon-fit'
import 'xterm/css/xterm.css'

export default {
  data () {
    return {
      ws: null, // websocket本体
      // wsUrl: process.env.VUE_APP_WEBSHELL_WS_URL, // websocket连接地址
      wsUrl: 'ws://127.0.0.1:8081/webshell', // websocket连接地址
      term: null
    }
  },
  created () {
    this.initWs()
  },
  mounted () {
    this.initXterm()
  },
  methods: {
    initXterm () {
      const _this = this
      const term = new Terminal({
        rendererType: 'canvas', // 渲染类型
        rows: 40,
        convertEol: true, // 启用时，光标将设置为下一行的开头
        disableStdin: false, // 是否应禁用输入。
        cursorStyle: 'underline', // 光标样式
        cursorBlink: true, // 光标闪烁
        theme: {
          foreground: '#7e9192', // 字体
          background: '#002833', // 背景色
          cursor: 'help', // 设置光标
          lineHeight: 16
        }
      })

      term.open(document.getElementById('terminal'))

      term.prompt = () => {
        term.write('\r\n$ ')
      }
      term.prompt()

      term.onData(key => {
        _this.term.write(key)
      })

      _this.term = term
    },
    initWs () {
      this.ws = new WebSocket(this.wsUrl)
      this.ws.onopen = this.wsOnOpen
      this.ws.onerror = this.wsOnError
      this.ws.onmessage = this.wsGetMessage
    },
    wsOnOpen () {
      console.log('socket连接成功')
    },
    wsOnError () {
      console.log('连接错误')
    },
    wsGetMessage (event) {
      console.log(event)
    }
  },
  beforeRouteLeave (to, from, next) {
    if (this.ws) {
      console.log('socket已经关闭')
      this.ws.close()
    }
    next()
  }
}
</script>

<style lang="scss" scoped>
.xterm-view {
  width: 100%;
  min-height: 100%;
  background: #002833;
}
</style>
