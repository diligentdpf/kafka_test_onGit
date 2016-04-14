package com.cmsz.ck.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 仅仅使用Socket建立与指定ip，指定端口的连接，并接收输入流发送过来的数据
 * 
 * @createTime 2016年3月10日16:17
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        // 连接到服务端的指定IP，指定端口
        // Socket s = new Socket("192.168.1.231", 1234);
        Socket s = new Socket("192.168.1.142", 30000);
        // 设置连接服务器的超时时间，如果服务器在这个时间内没有返回数据（任何数据），则抛出异常，：read timed out!
        // s.setSoTimeout(10000);
        // Socket s1 = new Socket();
        // s1.connect(new InetAddress("127.0.0.1", 30000), 10000);
        // 将Socket对应的输入流包装成BufferedReader(缓冲流),在这之前需要先包装成字符流(InputStreamReader)
        // BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataInputStream bf = new DataInputStream(s.getInputStream());
        // 进行io操作
        String line;
        while ((line = bf.readUTF()) != null) {
            System.out.println("服务器那头发来消息 ：" + line);
        }
        // 关闭流与Socket
        bf.close();
        s.close();
    }
}
