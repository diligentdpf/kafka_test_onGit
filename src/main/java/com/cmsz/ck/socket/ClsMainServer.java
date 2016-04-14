package com.cmsz.ck.socket;

import java.io.IOException;

public class ClsMainServer {
    TcpServer server;
	public void execute() {
		int port = 30000;
		 server= new TcpServer(port) {

			@Override
			public void onConnect(SocketTransceiver client) {
				printInfo(client, "Connect");
			}

			@Override
			public void onConnectFailed() {
				System.out.println("Client Connect Failed");
			}

			@Override
			public void onReceive(SocketTransceiver client, String s) throws IOException {
				printInfo(client, "Send Data: " + s);
				if(s.equals("1")){
				  client.send("甘少波");
				}else if(s.equals("2")){
                  client.send("甘少波");
                }else if(s.equals("3")){
                  client.send("甘少波");
                }
			}

			@Override
			public void onDisconnect(SocketTransceiver client) {
				printInfo(client, "Disconnect");
			}

			@Override
			public void onServerStop() {
				System.out.println("--------Server Stopped--------");
			}
		};
		System.out.println("--------Server Started--------");
		server.start();
	}
	
	public void stop(){
	    server.stop();
	}
	static void printInfo(SocketTransceiver st, String msg) {
		System.out.println("Client " + st.getInetAddress().getHostAddress());
		System.out.println("  " + msg);
	}
}
