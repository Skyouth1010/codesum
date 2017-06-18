package socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpNettyClient {

	public static void main(String[] args) {
        String host = "localhost";
        int port = 7011;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientResponseHandler());
                    ch.pipeline().addLast(new ClientRequestHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)
            // 发送数据
            for (int i = 0; i < 10; i++) {
            	ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10);
            	buf.writeBytes(("n_"+i).getBytes());
            	f.channel().writeAndFlush(buf).sync();
            	Thread.sleep(1000);
//            	buf.release();
            }

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
