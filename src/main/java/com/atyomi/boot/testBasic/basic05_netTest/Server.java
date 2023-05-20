package com.atyomi.boot.testBasic.basic05_netTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("正在等待连接..");
        Socket accept = serverSocket.accept();
//        InputStream inputStream = accept.getInputStream();
//        byte[] buf = new byte[1024];
//        int readline=0;
//        while((readline = inputStream.read(buf))!=-1){
//            System.out.println(new String(buf,0,readline));
//        };
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write(("first callback").getBytes());
        outputStream.close();

    }
}
