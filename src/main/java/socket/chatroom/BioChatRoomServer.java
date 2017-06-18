package socket.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.DataTransformer;

public class BioChatRoomServer implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ServerSocket serverSocket;
	
	private DataTransformer dataTransformer;
	
	private Executor executor = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(2), new ChatroomRejectedExecutionHandler());
	
	public BioChatRoomServer(int port, DataTransformer dataTransformer) throws Exception {
		try {
			this.serverSocket = new ServerSocket(port);
			this.dataTransformer = dataTransformer;
			logger.info("聊天室（一）启动，端口：" + port);
		} catch (Exception e) {
			throw e;
		}
	}

	public void run() {
		try {
			while (true) {
				executor.execute(new HandleThread(serverSocket.accept()));
			}
		} catch (Exception e) {
			logger.error("请求处理失败", e);
		}
	}
	
	class HandleThread implements Runnable {
		
		Socket socket;
		String username;
		
		public HandleThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					// 处理请求
					String message = dataTransformer.read(dis);
					if (message == null || "".equals(message)) {
						logger.info(this.username + "退出了聊天室");
						return;
					}
					if (message!=null && message.contains("#login#")) {
						this.username = message.substring(0, message.length() - 8);
					}
					// 处理数据回写
					socket.getOutputStream().write(dataTransformer.write(this.username, message).getBytes("GBK"));
				}
			} catch (IOException e) {
				logger.info(this.username + "掉线了");
			}
		}
	}
	
	public static void main(String[] args) {
		BioChatRoomServer server;
		try {
			server = new BioChatRoomServer(9001, new ChatroomDataTransformer());
			Thread thread = new Thread(server);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
