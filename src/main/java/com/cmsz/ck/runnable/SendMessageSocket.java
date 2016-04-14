package com.cmsz.ck.runnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.cmsz.ck.model.UserModel;
import com.cmsz.ck.service.KafkaService;

/**
 * @author oysj
 *
 */
public class SendMessageSocket implements Runnable {
	private KafkaService kafkaService;
	private Socket socket;
	private DataOutputStream out;
	private InetAddress addr;
	private KafkaConsumer consumer;

	public SendMessageSocket(Socket socket,KafkaService kafkaService) {
		this.kafkaService = kafkaService;
		this.socket = socket;
		addr = socket.getInetAddress();
	}

	@Override
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String str = dis.readUTF();
			System.out.println(str);
			System.out.println(kafkaService);
			UserModel user = kafkaService.checkAndReturnUserInfo(str);
			out = new DataOutputStream(socket.getOutputStream());
			boolean flag = user==null?false:true;
			System.out.println(flag);
			if(flag){
				consumer = new KafkaConsumer(user.getTopic(), user.getTopic());
				while (flag) {
					str = consumer.getMessage();
					System.out.println(str);
					out.writeUTF(str);
					out.flush();
					kafkaService.saveMessage(str);
					System.out.println("消息发送成功");
					consumer.commitOffsets();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	public void close() {
		if (addr != null)
			addr = null;
		try {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (socket != null)
				socket.close();
			if(consumer!=null)
				consumer.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.gc();
	}
}
