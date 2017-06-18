package socket.client;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientResponseHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
    	if (msg instanceof ByteBuf) {
    		try {
    			byte[] msgs = ((ByteBuf)msg).toString(Charset.defaultCharset()).getBytes();
    			System.out.println("get response from server:" + new String(msgs));
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			ReferenceCountUtil.release(msg);
    		}
    	}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("ClientResponseHandler channelActive...");
		super.channelActive(ctx);
	}
	
	
}
