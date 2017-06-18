package socket.server.netty;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TcpCountOutHandler extends ChannelOutboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	final static AtomicInteger count = new AtomicInteger();
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    	System.out.println("Server sends request times:" + count.incrementAndGet());
    	super.write(ctx, msg, promise);
    }
}
