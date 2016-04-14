package com.cmsz.ck.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.cmsz.ck.kafka.KafkaConsumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Socket收发器 通过Socket发送数据，并使用新线程监听Socket接收到的数据
 * 
 * @author jzj1993
 * @since 2015-2-22
 */
public abstract class SocketTransceiver implements Runnable {

    protected Socket socket;
    protected InetAddress addr;
    protected DataInputStream in;
    protected DataOutputStream out;
    private boolean runFlag;
    private String topic;

    /**
     * 实例化
     * 
     * @param socket 已经建立连接的socket
     */
    public SocketTransceiver(Socket socket) {
        this.socket = socket;
        this.addr = socket.getInetAddress();
    }


    public String getTopic() {
        return topic;
    }


    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * 获取连接到的Socket地址
     * 
     * @return InetAddress对象
     */
    public InetAddress getInetAddress() {
        return addr;
    }

    /**
     * 开启Socket收发
     * <p>
     * 如果开启失败，会断开连接并回调{@code onDisconnect()}
     */
    public void start() {
        runFlag = true;
        new Thread(this).start();
    }

    /**
     * 断开连接(主动)
     * <p>
     * 连接断开后，会回调{@code onDisconnect()}
     */
    public void stop() {
        runFlag = false;
        try {
            socket.shutdownInput();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送字符串
     * 
     * @param s 字符串
     * @return 发送成功返回true
     * @throws IOException
     */
    public boolean send(String s) throws IOException {
        if (out != null) {
            out.writeUTF(s);
            out.flush();
            return true;
        }
        return false;
    }

    /**
     * 监听Socket接收的数据(新线程中运行)
     */
    @Override
    public void run() {
        try {
            in = new DataInputStream(this.socket.getInputStream());// 这个方法会阻塞
            out = new DataOutputStream(this.socket.getOutputStream());
            out.writeUTF(socket.getInetAddress().getHostAddress() + " 连接成功！");
        } catch (IOException e) {
            e.printStackTrace();
            runFlag = false;
        }
        while (runFlag) {
            try {
                // final String s = in.readUTF();会被阻塞
                KafkaConsumer consumerThread = new KafkaConsumer(topic);
                KafkaStream<byte[], byte[]> stream = consumerThread.getKafkaStream(topic);
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    send(new String(iterator.next().message()));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // this.onReceive(addr, s);
            } catch (IOException e) {
                // 连接被动断开
                runFlag = false;
            }
        }
        // 断开连接
        try {
            in.close();
            out.close();
            socket.close();
            in = null;
            out = null;
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.onDisconnect(addr);
    }

    /**
     * 接收到数据
     * <p>
     * 注意：此回调是在新线程中执行的
     * 
     * @param addr 连接到的Socket地址
     * @param s 收到的字符串
     */
    public abstract void onReceive(InetAddress addr, String s) throws IOException;

    /**
     * 连接断开
     * <p>
     * 注意：此回调是在新线程中执行的
     * 
     * @param addr 连接到的Socket地址
     */
    public abstract void onDisconnect(InetAddress addr);
}
