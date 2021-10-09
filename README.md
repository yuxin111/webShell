## 环境
前端：vue2 + xterm \
后端：springboot + jsch

## 启动

### 前端frontend
```
npm install   // 安装依赖 
npm run serve   // 启动项目
```
注意在@/views/xterm/Index中修改终端连接信息

```javascript
this.sendInitData({
  operate: 'CONNECT',
  host: '192.168.1.114', // IP
  port: 22, // 端口号
  username: 'yuxin', // 用户名
  password: '1' // 密码
})
```

### 后端backend
导入maven依赖启动即可
