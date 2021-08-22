package com.yuxin.cn.service.impl;

import com.jcraft.jsch.JSch;
import com.yuxin.cn.constant.Constant;
import com.yuxin.cn.entity.ConnectInfo;
import com.yuxin.cn.service.IWebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSSH的业务实现
 * @Author YuXin
 * @Date 2021/8/18
 **/
@Service
public class WebSSHServiceImpl implements IWebSSHService {

    //存放ssh连接信息的map
    private static Map<String, Object> sshMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(WebSSHServiceImpl.class);

    @Override
    public void initConnection(WebSocketSession session) {
        JSch jSch = new JSch();
        ConnectInfo connectInfo = new ConnectInfo();
        connectInfo.setJSch(jSch);
        connectInfo.setWebSocketSession(session);
        String uuid = String.valueOf(session.getAttributes().get(Constant.USER_UUID_KEY));
        //将这个ssh连接信息放入map中
        sshMap.put(uuid, connectInfo);
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
