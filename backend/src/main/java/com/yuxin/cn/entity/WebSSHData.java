package com.yuxin.cn.entity;

import com.yuxin.cn.enums.OperaTypeEnum;
import lombok.Data;

/**
 * webshell数据传输
 * @Author YuXin
 * @Date 2021/8/15
 **/
@Data
public class WebSSHData {
    // 操作
    private String operate;
    private String username;
    private String host;
    private int port = 22;
    private String password;
    private String command;
}
