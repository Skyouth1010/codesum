package socket.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpNioSocketServer implements Runnable {

	/* 缓冲区大小 */
	private int BLOCK = 4096;
	/* 接受数据缓冲区 */
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* 发送数据缓冲区 */
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	private Selector selector;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Class<DataTransformer> dataTransformerClazz;

	@SuppressWarnings("unchecked")
	public TcpNioSocketServer(int port, String dataTransformerClazz) throws Exception {
		try {
			// 打开服务器套接字通道
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			// 服务器配置为非阻塞
			serverSocketChannel.configureBlocking(false);
			// 检索与此通道关联的服务器套接字
			ServerSocket serverSocket = serverSocketChannel.socket();
			// 进行服务的绑定
			serverSocket.bind(new InetSocketAddress(port));
			// 通过open()方法找到Selector
			selector = Selector.open();
			// 注册到selector，等待连接
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			this.dataTransformerClazz = (Class<DataTransformer>) Class.forName(dataTransformerClazz);
			logger.info("[][TcpNioSocketServer startup at port " + port + "][]");
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public void run() {
		while (true) {
			ServerSocketChannel server = null;
			SocketChannel client = null;
			SelectionKey selectionKey = null;
			try {
				// 选择一组键，并且相应的通道已经打开
				selector.select();
				// 返回此选择器的已选择键集。
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					selectionKey = iterator.next();
					iterator.remove();
					// 接受请求
					server = null;
					client = null;
					int count = 0;
					// 测试此键的通道是否已准备好接受新的套接字连接。
					if (selectionKey.isAcceptable()) {
						// 返回为之创建此键的通道。
						server = (ServerSocketChannel) selectionKey.channel();
						// 接受到此通道套接字的连接。
						// 此方法返回的套接字通道（如果有）将处于阻塞模式。
						client = server.accept();
						// 配置为非阻塞
						client.configureBlocking(false);
						// 注册到selector，等待连接
						client.register(selector, SelectionKey.OP_READ);
					} else if (selectionKey.isReadable()) {
						// 返回为之创建此键的通道。
						client = (SocketChannel) selectionKey.channel();
						// 将缓冲区清空以备下次读取
						receivebuffer.clear();
						// 读取服务器发送来的数据到缓冲区中
						count = client.read(receivebuffer);
						if (count > 0) {
							logger.info("[][reading request from client][]");
							DataTransformer dataTransformer = dataTransformerClazz
									.newInstance();
							String handleResult = dataTransformer
									.read(receivebuffer.array());
							handleResult = handleResult == null ? ""
									: handleResult.trim();
							dataTransformer.setHandleResult(handleResult);
							client.register(selector, SelectionKey.OP_WRITE,
									dataTransformer);
						} else if (count == -1) {
							if (client != null) {
								client.close();
							}
						}
					} else if (selectionKey.isWritable()) {
						logger.info("[][writing response to client][]");
						// 将缓冲区清空以备下次写入
						sendbuffer.clear();
						// 返回为之创建此键的通道。
						client = (SocketChannel) selectionKey.channel();
						DataTransformer dataTransformer = (DataTransformer) selectionKey
								.attachment();
						// 向缓冲区中输入数据
						sendbuffer.put(dataTransformer.write(null,
								dataTransformer.getHandleResult()).getBytes());
						// 将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
						sendbuffer.flip();
						// 输出到通道
						client.write(sendbuffer);
						client.register(selector, SelectionKey.OP_READ);
					}
				}
			} catch (Exception e) {
				logger.error("[EBUS_002][请求处理失败][" + e.getMessage() + "]", e);
				try {
					if (selectionKey != null) {
						selectionKey.cancel();
					}
					if (client != null) {
							client.close();
					}
					if (server != null) {
							server.close();
					}
				} catch (IOException e1) {}
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		TcpNioSocketServer server;
		try {
			server = new TcpNioSocketServer(9002, "com.rb.owk.wolfs.ebox.socket.DefaultDataTransformer");
			Thread thread = new Thread(server);
			thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
