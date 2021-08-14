package com.yuxin.cn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();
    /**
     * 建立连接后
     * @param: [session]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/11
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("ws连接已建立");
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
        log.info("发送文本消息");
        session.sendMessage(new TextMessage("hello"));
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
        log.info("ws连接已断开");
    }
}
