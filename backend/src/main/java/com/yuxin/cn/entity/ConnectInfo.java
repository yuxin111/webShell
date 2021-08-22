package com.yuxin.cn.entity;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;

/**
 * ssh连接数据
 * @Author YuXin
 * @Date 2021/8/18
 **/
@Data
public class ConnectInfo {
    private WebSocketSession webSocketSession;
    private JSch jSch;
    private Channel channel;
    private LocalDateTime lastReplyTime;    //最后响应时间
}
