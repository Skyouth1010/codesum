package socket.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TcpCountOutHandler extends ChannelOutboundHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    	System.out.println("TcpCountOutHandler write");
    	super.write(ctx, msg, promise);
    }
    
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("TcpCountOutHandler read");
        ctx.read();
    }
}
