package com.yuxin.cn.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSSH的业务逻辑
 * @author yuxin
 * @date 2021/8/18
 */
public interface IWebSSHService {
    /**
     * 初始化ssh连接
     * @param: [session]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/18
     */
    public void initConnection(WebSocketSession session);

    /**
     * 处理客户段发的数据
     * @param: [buffer, session]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/18
     */
    public void recvHandle(String buffer, WebSocketSession session);

    /**
     * 数据写回前端
     * @param: [session, buffer]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/18
     */
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException;

    /**
     * 关闭连接
     * @param: [session]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/18
     */
    public void close(WebSocketSession session);
}
