package socket.server.netty;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.DataTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

public class TcpBussinessOutHandler extends ChannelOutboundHandlerAdapter {

	private DataTransformer dataTransformer;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public TcpBussinessOutHandler(DataTransformer dataTransformer) {
		this.dataTransformer = dataTransformer;
	}
	
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    	System.out.println("TcpBussinessOutHandler write");
    	if (msg instanceof ByteBuf) {
    		try {
    			String msgStr = ((ByteBuf)msg).toString(Charset.defaultCharset());
    			ByteBuf bb = ctx.alloc().buffer();
				bb.writeBytes(dataTransformer.write(null, msgStr).getBytes());
				ctx.write(bb);
    		} finally {
    			ReferenceCountUtil.release(msg);
    		}
    	}
    }
    
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
    	System.out.println("TcpBussinessOutHandler read");
        ctx.read();
    }
}
