package socket.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.DataTransformer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpNettyServer implements Runnable {

	private static volatile TcpNettyServer ts;
	private int port;
	private DataTransformer dataTransformer;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@SuppressWarnings("unchecked")
	private TcpNettyServer(int port, String dataTransformerClazz) throws Exception {
		this.port = port;
		try {
			dataTransformer = ((Class<DataTransformer>) Class.forName(dataTransformerClazz)).newInstance();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static TcpNettyServer getInstance(int port, String dataTransformerClazz) {
		if (ts == null) {
			synchronized (TcpNettyServer.class) {
				if (ts == null) {
					try {
						ts = new TcpNettyServer(port, dataTransformerClazz);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return ts;
	}
	
	@Override
	public void run() {
        // Configure the server.,reactor线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new TcpCountInHandler());
                     p.addLast(new TcpBussinessInHandler(dataTransformer));
                     p.addLast(new TcpBussinessOutHandler(dataTransformer));
                     p.addLast(new TcpCountOutHandler());
                 }
             });

            // Start the server.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        	logger.error("[EBUS_002][tcpserver start error][" + e.getMessage() + "]", e);
		} finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
