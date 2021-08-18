package com.yuxin.cn;

import com.yuxin.cn.constant.Constant;
import com.yuxin.cn.service.IWebSSHService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired
    private IWebSSHService webSSHService;

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * 建立连接后
     * @param: [session]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/11
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("用户:{},连接WebSSH", session.getAttributes().get(Constant.USER_UUID_KEY));
        //调用初始化连接
        webSSHService.initConnection(session);
    }

    /**
     * 消息处理
     * @param: [session, message]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/11
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            log.info("用户:{},发送命令:{}", session.getAttributes().get(Constant.USER_UUID_KEY), session.toString());
            //调用service接收消息
            webSSHService.recvHandle(((TextMessage) message).getPayload(), session);
        } else if (message instanceof BinaryMessage) {

        } else if (message instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + message);
        }
    }

    /**
     * 出错处理
     * @param: [session, exception]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/11
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("传输出现异常：{}",exception);
    }

    /**
     * 关闭连接后
     * @param: [session, status]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/11
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("用户:{}断开webssh连接", String.valueOf(session.getAttributes().get(Constant.USER_UUID_KEY)));
        //调用service关闭连接
        webSSHService.close(session);
    }
}
