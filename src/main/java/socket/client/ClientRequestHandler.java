package socket.client;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientRequestHandler extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		if (msg instanceof ByteBuf) {
    		try {
    			String msgStr = ((ByteBuf)msg).toString(Charset.defaultCharset());
    			System.out.println("send request:"+msgStr);
    		} finally {
    			
    		}
    	}
		super.write(ctx, msg, promise);
	}

	
}
