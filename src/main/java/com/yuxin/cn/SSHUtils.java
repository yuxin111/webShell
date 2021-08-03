package com.yuxin.cn;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class SSHUtils {
    private static final String ENCODING = "UTF-8";
    public static Session getJSchSession(String username,String host,int port,String password) throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(username,host,port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }
    public static String execCommandByShell(Session session)throws IOException,JSchException{
        String result = "";
        ChannelShell channelShell = (ChannelShell) session.openChannel("shell");
        InputStream inputStream = channelShell.getInputStream();
        channelShell.setPty(true);
        channelShell.connect();
        OutputStream outputStream = channelShell.getOutputStream();

        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("cd ~");
        printWriter.println("ls");
        printWriter.println("exit");
        printWriter.flush();

        byte[] tmp = new byte[1024];
        while(true){
            while(inputStream.available() > 0){
                int i = inputStream.read(tmp, 0, 1024);
                if(i < 0) break;
                String s = new String(tmp, 0, i);
                if(s.indexOf("--More--") >= 0){
                    outputStream.write((" ").getBytes());
                    outputStream.flush();
                }
                System.out.println(s);
            }
            if(channelShell.isClosed()){
                System.out.println("exit-status:"+channelShell.getExitStatus());
                break;
            }
            try{Thread.sleep(1000);}catch(Exception e){}
        }
        outputStream.close();
        inputStream.close();
        channelShell.disconnect();
        session.disconnect();
        System.out.println("DONE");

        return result;
    }

    public static void main(String[] args) {
        try {
            Session session = getJSchSession("yuxin","192.168.1.114",22,"1");
            String result = execCommandByShell(session);
            System.out.println(result);
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
