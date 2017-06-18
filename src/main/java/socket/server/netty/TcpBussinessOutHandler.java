package socket.server.netty;

import java.nio.charset.Charset;

import socket.server.DataTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TcpBussinessOutHandler extends ChannelOutboundHandlerAdapter {

	private DataTransformer dataTransformer;
	public TcpBussinessOutHandler(DataTransformer dataTransformer) {
		this.dataTransformer = dataTransformer;
	}
	
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    	if (msg instanceof ByteBuf) {
    		try {
    			String msgStr = ((ByteBuf)msg).toString(Charset.defaultCharset());
    			ByteBuf bb = ctx.alloc().buffer();
				bb.writeBytes(dataTransformer.write(null, msgStr).getBytes());
				super.write(ctx, bb, promise);
				super.flush(ctx);
    		} finally {
    			// 
    		}
    	}
    }
}
