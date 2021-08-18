package com.yuxin.cn.service.impl;

import com.yuxin.cn.service.IWebSSHService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSSH的业务实现
 * @Author YuXin
 * @Date 2021/8/18
 **/
@Service
public class WebSSHServiceImpl implements IWebSSHService {
    @Override
    public void initConnection(WebSocketSession session) {

    }

    @Override
    public void recvHandle(String buffer, WebSocketSession session) {

    }

    @Override
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException {

    }

    @Override
    public void close(WebSocketSession session) {

    }
}
