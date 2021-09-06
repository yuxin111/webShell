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
      wsUrl: process.env.VUE_APP_WEBSHELL_WS_URL, // websocket连接地址
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
        cursorBlink: true // 光标闪烁
        // theme: {
        //   foreground: '#7e9192', // 字体
        //   background: '#002833', // 背景色
        //   cursor: 'help', // 设置光标
        //   lineHeight: 16
        // }
      })

      term.open(document.getElementById('terminal'))

      term.prompt = () => {
        term.write('\r\n')
      }
      // term.prompt()

      term.onData(key => {
        this.sendClientData(key)
      })

      _this.term = term
    },
    initWs () {
      this.ws = new WebSocket(this.wsUrl)
      this.ws.onopen = this.onOpen
      this.ws.onerror = this.onError
      this.ws.onclose = this.onClose
      this.ws.onmessage = this.getMessage
    },
    onOpen () {
      this.sendInitData({
        operate: 'CONNECT',
        host: '192.168.1.114', // IP
        port: 22, // 端口号
        username: 'yuxin', // 用户名
        password: '1' // 密码
      })
      this.term.write('\r\nconnect success！')
    },
    onError () {
      this.term.write('\r\nthe connection is wrong')
    },
    onClose () {
      this.term.write('\r\nthe connection is closed')
    },
    getMessage (event) {
      const data = event.data.toString()
      this.term.write(data)
    },
    sendInitData (options) {
      this.ws.send(JSON.stringify(options))
    },
    sendClientData (data) {
      this.ws.send(JSON.stringify({
        operate: 'COMMAND',
        command: data
      }))
    }
  },
  beforeRouteLeave (to, from, next) {
    if (this.ws) {
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
