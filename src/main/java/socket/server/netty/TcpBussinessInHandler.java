package socket.server.netty;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.DataTransformer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class TcpBussinessInHandler extends ChannelInboundHandlerAdapter {
	
	private DataTransformer dataTransformer;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public TcpBussinessInHandler(DataTransformer dataTransformer) {
		this.dataTransformer = dataTransformer;
	}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	System.out.println("TcpBussinessInHandler channelRead");
    	if (msg instanceof ByteBuf) {
    		try {
    			byte[] msgs = ((ByteBuf)msg).toString(Charset.defaultCharset()).getBytes();
    			ByteBuf bb = ctx.alloc().buffer();
				bb.writeBytes(dataTransformer.read(msgs).getBytes());
				ctx.write(bb);
    		} catch (IOException e) {
    			logger.error("[EBUS_002][请求处理失败][" + e.getMessage() + "]", e);
    		} finally {
    			ReferenceCountUtil.release(msg);
    		}
    	}
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    	System.out.println("TcpBussinessInHandler channelReadComplete");
        ctx.flush();
    }
}
