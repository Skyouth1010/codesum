package socket.server.netty;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpCountInHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	final static AtomicInteger count = new AtomicInteger();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println("Server received request times:" + count.incrementAndGet());
    	ctx.fireChannelRead(msg);
    }
}
