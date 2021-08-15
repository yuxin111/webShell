package com.yuxin.cn.entity;

import lombok.Data;

/**
 * ssh连接数据
 * @Return
 * @Author YuXin
 * @Date 2021/8/15
 **/
@Data
public class SSHConnectData {
    private String username;
    private String host;
    private int port = 22;
    private String password;
}
