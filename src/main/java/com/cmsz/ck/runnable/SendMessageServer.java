package com.cmsz.ck.runnable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cmsz.ck.service.KafkaService;

import scala.actors.threadpool.ExecutorService;
import scala.actors.threadpool.Executors;

public class SendMessageServer {
    @Autowired
    private KafkaService kafkaService;

    private ServerSocket server;
    private List<SendMessageSocket> mlist = new ArrayList<SendMessageSocket>();
    private Map<SendMessageSocket, DataOutputStream> socketMap =
            new HashMap<SendMessageSocket, DataOutputStream>();
    private ExecutorService service = Executors.newCachedThreadPool();
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            testAlive();
        }
    });

    public SendMessageServer() {
        try {
            server = new ServerSocket(30000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        thread.start();
        service.execute(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                try {
                    while (flag) {
                        System.out.println("正在等待用户上线");
                        Socket socket = server.accept();
                        System.out.println("用户" + socket.getInetAddress() + "上线了");
                        SendMessageSocket m = new SendMessageSocket(socket, kafkaService);
                        service.execute(m);
                        // mlist.add(m);
                        // socketMap.put(m, new DataOutputStream(socket.getOutputStream()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void testAlive() {
        boolean flag = true;
        DataOutputStream dot = null;
        loop: while (flag) {
            if (!mlist.isEmpty()) {
                for (SendMessageSocket mSocket : mlist) {
                    dot = socketMap.get(mSocket);
                    try {
                        dot.writeUTF("##");
                    } catch (IOException e) {
                        e.printStackTrace();
                        socketMap.remove(mSocket);
                        mlist.remove(mSocket);
                        mSocket.close();
                        continue loop;
                    }
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (!mlist.isEmpty()) {
                for (SendMessageSocket mSocket : mlist) {
                    mSocket.close();
                }
            }
            if (server != null) server.close();
            service.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
