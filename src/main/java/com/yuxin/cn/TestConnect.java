package com.yuxin.cn;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class TestConnect {
    public static void main(String[] args){
        JSch jSch = new JSch();
        Session session = null;
        try {
            session = jSch.getSession("yuxin","192.168.1.114",22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(10000);
            session.setPassword("1");
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
