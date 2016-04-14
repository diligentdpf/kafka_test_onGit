
package com.cmsz.ck.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天室客户端
 * @author soft01
 *
 */
public class Client {
	/*
	 * 客户端的Socket，用于练肩负无端的Serverocket
	 * 并与服务器链接
	 */
	private Socket socket;
	private PrintWriter pw;
	private DataInputStream isr;
	private Scanner scan;
	public Client(){
		try {
			/**
			 * 初始化Socket常使用带两个参数的构造方法
			 * 参数1：服务端的地址
			 * 参数2：服务端开启的端口
			 * 需要注意的是：创建的过程就是链接的过程，若链接失败这里会
			 * 抛出异常
			 */
			socket = new Socket("192.168.1.231",30000);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	/**
	 * 客户端开始工作的方法
	 */
	public void start(){
		try {
			/*
			 * 启动用来读取服务端发送过来的消息的线程
			 */
			GetServerMessageHandler handler = new GetServerMessageHandler();
			Thread t1 = new Thread(handler);
			t1.start();
			SendMessageToServer  sm = new SendMessageToServer();
			Thread t2 = new Thread(sm);
			t2.start();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
		}
	}
	public static void main(String[] args) {
		Client client =new Client();		
		client.start();
	}
	private class GetServerMessageHandler implements Runnable{
		public void run() {
			try {
				InputStream is = socket.getInputStream();
				isr = new DataInputStream(is);
				String serverMessage = null;
				while((serverMessage=isr.readUTF())!=null){
					System.out.println(serverMessage);
				}
			} catch (Exception e) {
			}finally{
				try {
					isr.close();
					socket.close();
					System.out.println("客户端关闭连接");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class SendMessageToServer implements Runnable {
		@Override
		public void run() {
			try {
				/*
				 * 若想将消息发送至服务端，我们需要通过socket获取
				 * 输出流
				 * OutputStream getOutputStream()
				 * Socket提供了该方法用来获取一个字节输出流
				 * 便于我们将数据发送至远程计算机
				 */
				OutputStream out = socket.getOutputStream();//这里是输出点socket；
				DataOutputStream oos = new DataOutputStream(out);
				//获取用户数入
				Scanner scan = new Scanner(System.in);
				String message = "";
				message = scan.nextLine();
				oos.writeUTF(message);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				scan.close();
				System.out.println("输出流关闭");
			}
		}
	}
}
