package socket.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import socket.server.DataTransformer;

public class ChatroomDataTransformer extends DataTransformer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String read(DataInputStream dis) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
//		return br.readLine();
		byte[] btSize = new byte[10 * 1024];
		dis.read(btSize, 0, 10 * 1024);// bio，这里会阻塞
		return new String(btSize, "GBK").trim();
	}

	@Override
	public String read(byte[] input) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String write(String username, String s) {
		if (s == null) {
			return "";
		} else if (s.contains("#login#")) {
			logger.info(username + "进入了聊天室。");
			try {
				return new String((username + "进入了聊天室。").getBytes("UTF-8"),"GBK");
			} catch (UnsupportedEncodingException e) {
				return "error";
			}
		} else {
			logger.info(username + " 说: " + s);
			return "server get the message.";
		}
	}
}
