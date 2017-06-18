package socket.server.netty;

import java.io.IOException;
import java.nio.charset.Charset;

import socket.server.DataTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TcpBussinessInHandler extends ChannelInboundHandlerAdapter {
	
	private DataTransformer dataTransformer;
	public TcpBussinessInHandler(DataTransformer dataTransformer) {
		this.dataTransformer = dataTransformer;
	}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	if (msg instanceof ByteBuf) {
    		try {
    			byte[] msgs = ((ByteBuf)msg).toString(Charset.defaultCharset()).getBytes();
    			ByteBuf bb = ctx.alloc().buffer();
				bb.writeBytes(dataTransformer.read(msgs).getBytes());
				ctx.pipeline().write(bb);
    		} catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			ReferenceCountUtil.release(msg);
    		}
    	}
    }
}
