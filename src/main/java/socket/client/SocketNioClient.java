package socket.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketNioClient {
	private static final Logger logger = LoggerFactory
			.getLogger(SocketNioClient.class);
	private SocketAddress address;
	private SocketChannel client;
	/* 缓冲区大小 */
	private int BLOCK = 4096;

	public SocketNioClient(String host, int port) throws IOException {
		try {
			address = new InetSocketAddress(host, port);
			client = SocketChannel.open(address);
			client.configureBlocking(false);
		} catch (IOException e) {
			//logger.error("SOCKET创建失败", e);
			throw e;
		}
	}

	public void send(String request) {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(BLOCK);
			buffer.put(request.getBytes());
			buffer.clear();
			client.write(buffer);
			logger.info("发送请求："+request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}