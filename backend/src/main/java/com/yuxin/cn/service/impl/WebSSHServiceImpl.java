package com.yuxin.cn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.yuxin.cn.constant.Constant;
import com.yuxin.cn.entity.ConnectInfo;
import com.yuxin.cn.entity.WebSSHData;
import com.yuxin.cn.enums.OperaTypeEnum;
import com.yuxin.cn.service.IWebSSHService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * WebSSH的业务实现
 *
 * @Author YuXin
 * @Date 2021/8/18
 **/
@Service
public class WebSSHServiceImpl implements IWebSSHService {

    private static final int DURATION_MINUTE = 5;

    //存放ssh连接信息的map
    private static Map<String, Object> sshMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(WebSSHServiceImpl.class);

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Scheduled(cron = "*/5 * * * * ?")
    public void checkTaskExecutor(){
        System.out.println("当前活跃线程数：" + taskExecutor.getActiveCount());
    }

    @Scheduled(cron = "0 */5 * * * ?")
    private void clearConnectTask() {
        List<Object> objects = new ArrayList<>(sshMap.values());
        WebSocketSession session;
        for (Object object : objects) {
            ConnectInfo connectInfo = (ConnectInfo) object;
            if (connectInfo.getLastReplyTime() != null) {
                Duration duration = Duration.between(connectInfo.getLastReplyTime(), LocalDateTime.now());
                if (duration.toMinutes() >= DURATION_MINUTE) {
                    session = connectInfo.getWebSocketSession();
                    logger.info("已清理线程{}", String.valueOf(session.getAttributes().get(Constant.USER_UUID_KEY)));
                    try {
                        close(connectInfo.getWebSocketSession());
                        session.close();
                    } catch (IOException e) {
                        logger.error("session关闭异常:{}", e.getMessage());
                    }


                }
            }
        }
    }

    @Override
    public void initConnection(WebSocketSession session){
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
        ObjectMapper objectMapper = new ObjectMapper();
        WebSSHData webSSHData = null;
        try {
            webSSHData = objectMapper.readValue(buffer, WebSSHData.class);
        } catch (IOException e) {
            logger.error("Json转换异常:{}", e.getMessage());
            return;
        }
        String userId = String.valueOf(session.getAttributes().get(Constant.USER_UUID_KEY));
        if (OperaTypeEnum.CONNECT.toString().equals(webSSHData.getOperate())) {
            //找到刚才存储的ssh连接对象
            ConnectInfo connectInfo = (ConnectInfo) sshMap.get(userId);
            //启动线程异步处理
            WebSSHData finalWebSSHData = webSSHData;
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connectToSSH(connectInfo, finalWebSSHData, session);
                    } catch (JSchException | IOException e) {
                        logger.error("webssh连接异常:{}", e.getMessage());
                        close(session);
                    }
                }
            });

        } else if (OperaTypeEnum.COMMAND.toString().equals(webSSHData.getOperate())) {
            String command = webSSHData.getCommand();
            ConnectInfo connectInfo = (ConnectInfo) sshMap.get(userId);
            if (connectInfo != null) {
                try {
                    //设置最后响应时间
                    connectInfo.setLastReplyTime(LocalDateTime.now());
                    transToSSH(connectInfo.getChannel(), command);
                } catch (IOException e) {
                    logger.error("webssh连接异常:{}", e.getMessage());
                    close(session);
                }
            }
        } else {
            logger.error("不支持的操作");
            close(session);
        }
    }

    @Override
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException {
        session.sendMessage(new TextMessage(buffer));
    }

    @Override
    public void close(WebSocketSession session) {
        String userId = String.valueOf(session.getAttributes().get(Constant.USER_UUID_KEY));
        ConnectInfo connectInfo = (ConnectInfo) sshMap.get(userId);
        if (connectInfo != null) {
            //断开连接
            if (connectInfo.getChannel() != null) connectInfo.getChannel().disconnect();
            //map中移除
            sshMap.remove(userId);
        }
    }

    /**
     * 使用jsch连接终端
     *
     * @param: [connectInfo, webSSHData, webSocketSession]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/23
     */
    private void connectToSSH(ConnectInfo connectInfo, WebSSHData webSSHData, WebSocketSession webSocketSession) throws JSchException, IOException {
        Session session = null;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        session = connectInfo.getJSch().getSession(webSSHData.getUsername(), webSSHData.getHost(), webSSHData.getPort());
        session.setConfig(config);
        //设置密码
        session.setPassword(webSSHData.getPassword());
        //连接  超时时间30s
        session.connect(30000);

        //开启shell通道
        Channel channel = session.openChannel("shell");

        //通道连接 超时时间3s
        channel.connect(3000);

        //设置channel
        connectInfo.setChannel(channel);

        //设置最后响应时间
        connectInfo.setLastReplyTime(LocalDateTime.now());

        //转发消息
        transToSSH(channel, "\r");

        //读取终端返回的信息流
        InputStream inputStream = channel.getInputStream();
        try {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                sendMessage(webSocketSession, Arrays.copyOfRange(buffer, 0, i));
            }
        } finally {
            //断开连接后关闭会话
            session.disconnect();
            channel.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    /**
     * 将消息转发到终端
     *
     * @param: [channel, command]
     * @return: void
     * @author: yuxin
     * @date: 2021/8/23
     */
    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }
}
