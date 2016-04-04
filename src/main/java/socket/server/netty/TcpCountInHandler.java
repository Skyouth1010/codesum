package socket.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TcpCountInHandler extends ChannelInboundHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println("TcpCountInHandler channelRead");
    	ctx.fireChannelRead(msg);
    }
    
    

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	System.out.println("TcpCountInHandler channelReadComplete");
        ctx.flush();
    }
}
