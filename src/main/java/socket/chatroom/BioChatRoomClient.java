package socket.chatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioChatRoomClient {
	private static final Logger logger = LoggerFactory.getLogger(BioChatRoomClient.class);

	private Socket conn = null;
	private BufferedReader input = null;
	private InetSocketAddress address = null;	
	private int timeOut = 2000000;	
	private int readTimeOut = 20000;	
	private boolean needResponse = true;	
	private int bufferSize = 20 * 1024;	
	private String encode = "GBK";

	public BioChatRoomClient(String host, int port) throws IOException {
		try {
			conn = new Socket();
			conn.setSoTimeout(timeOut);
			address = new InetSocketAddress(host, port);
		} catch (IOException e) {
			logger.error("无法连接聊天室", e);
			throw e;
		}
	}

	/**     
	 * send(socket交互)
	 * @param request
	 * @return
	 * @throws BusinessException  
	 */
	public void say(String request) throws Exception {
		byte[] rcvbuf = null;
		if (connect()) {
			try {
				conn.getOutputStream().write(request.getBytes(encode));
			} catch (IOException e) {
				//logger.error("发送数据失败", e);
				throw e;
			}
			if (needResponse && connect()) {
				rcvbuf = receiveData();
			}
		}
		if (rcvbuf != null) {
			String result = new String(rcvbuf);
			logger.info("server answer:"+result);
		}
	}
	
	/**     
	 * 登录聊天室
	 * @param request
	 * @return
	 * @throws BusinessException  
	 */
	public void login(String username) throws Exception {
		byte[] rcvbuf = null;
		if (connect()) {
			try {
				conn.getOutputStream().write((username + " #login#").getBytes(encode));
			} catch (IOException e) {
				//logger.error("发送数据失败", e);
				throw e;
			}
			if (needResponse && connect()) {
				rcvbuf = receiveData();
			}
		}
		if (rcvbuf != null) {
			String result = new String(rcvbuf);
			logger.info("server answer:"+result);
		}
	}
	
	/**     
	 * 退出聊天室
	 * @param request
	 * @return
	 * @throws BusinessException  
	 */
	public void logout() throws IOException {
		disconnect();
	}

	/**     
	 * receiveData(接收报文)     
	 * @return
	 * @throws BusinessException  
	 */
	private byte[] receiveData() throws Exception {
		try{
    		char[] cbuf = new char[bufferSize];
    		int len = 0;
    		int time = 0;
    		while (true) {
    			if (time >= readTimeOut) {
    				logger.error("error","receiveData timeOut");
    				return null;
    			}
//    			if (!input.ready()) {
//    				time += 100;
//    				Thread.sleep(100);
//    				continue;
//    			}
    			len = input.read(cbuf, 0, bufferSize);
    			break;
//    			int lenTemp = input.read(cbuf, len, bufferSize-len);
//    			if (lenTemp == -1) {
//    				break;
//    			}
//    			len += lenTemp;
    		}
    		if (len < 0) {
    			return null;
    		}
    		char[] buffer = new char[len];
    		System.arraycopy(cbuf, 0, buffer, 0, len);
    		Arrays.fill(cbuf, (char) 0);
    		return String.valueOf(buffer).getBytes(encode);
		}catch(Exception e){
			//logger.error("接收返回包出错", e);
			throw e;
		}
	}

	/**     
	 * connect(建立连接)     
	 * @return
	 * @throws BusinessException  
	 */
	private boolean connect() throws IOException {
		if (!conn.isConnected()) {
			try {
				conn.connect(address, timeOut);
				input = new BufferedReader(new InputStreamReader(conn.getInputStream(), encode));
			} catch (IOException e) {
				logger.error("无法连接聊天室", e);
				throw e;
			}
		}
		return true;
	}

	/**     
	 * disconnect(断开连接)     
	 * @throws BusinessException  
	 */
	private void disconnect() throws IOException {
		try {
			if (input != null) {
				input.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (IOException e) {
			//logger.error("SOCKET断连异常",e);
			throw e;
		}
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	
	/**   
	 * @param bufferSize the bufferSize to set   
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	
	/**   
	 * @param encode the encode to set   
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public void setNeedResponse(boolean needResponse) {
		this.needResponse = needResponse;
	}
	
	public static void main(String[] s) {
		Scanner input;
		try {
			BioChatRoomClient sc = new BioChatRoomClient("127.0.0.1", 9002);
			System.out.print("请输入用户名：");
			while(true) {
				input = new Scanner(System.in);
				String is = input.nextLine();
				if (is != null && !"".equals(is)) {
					sc.login("[用户]"+is);
					break;
				} else {
					continue;
				}
			}
			while(true) {
				input = new Scanner(System.in);
				String is = input.nextLine();
				if ("logout".equals(is)) {
					sc.logout();
					break;
				} else if (is != null && !"".equals(is)) {
					sc.say(is);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}