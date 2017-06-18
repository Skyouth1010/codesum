package test;

import socket.server.netty.TcpNettyServer;

public class TestSocket {

	public static void  main(String[] args) {
		TcpNettyServer server = TcpNettyServer.getInstance(7011, "socket.server.DefaultDataTransformer");
		new Thread(server).start();
	}
}
