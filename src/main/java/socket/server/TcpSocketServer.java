package socket.server;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpSocketServer implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ServerSocket serverSocket;
	
	private Socket socket;

	private DataInputStream dis;
	
	private DataTransformer dataTransformer;
	
	public TcpSocketServer(int port, DataTransformer dataTransformer) throws Exception {
		try {
			this.serverSocket = new ServerSocket(port);
			this.dataTransformer = dataTransformer;
			logger.info("TcpSocketServer startup at port " + port);
		} catch (Exception e) {
			throw e;
		}
	}

	public void run() {
		try {
			while (true) {
				socket = serverSocket.accept();

				dis = new DataInputStream(socket.getInputStream());
				// 处理请求
				String message = dataTransformer.read(dis);
				// 处理数据回写
				socket.getOutputStream().write(dataTransformer.write(null, message).getBytes());
			}
		} catch (Exception e) {
			logger.error("请求处理失败", e);
		}
	}
	
	
	public static void main(String[] args) {
		TcpSocketServer server;
		try {
			server = new TcpSocketServer(9001, new DefaultDataTransformer());
			Thread thread = new Thread(server);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
