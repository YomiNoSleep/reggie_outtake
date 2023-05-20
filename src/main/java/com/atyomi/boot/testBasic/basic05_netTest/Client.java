package com.atyomi.boot.testBasic.basic05_netTest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(),9999);
//        OutputStream outputStream = socket.getOutputStream();
//        String s = "hello";
////        byte[] buf = {-50,-51,-53};
////        String s1 = new String(buf);
//        byte[] bytes = s.getBytes();
//        outputStream.write(bytes);
//        outputStream.write("second transport".getBytes());
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024];
        int readline = 0;
        while((readline = inputStream.read(buf))!= -1){
            System.out.println(new String(buf,0,readline));
        }
//        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
