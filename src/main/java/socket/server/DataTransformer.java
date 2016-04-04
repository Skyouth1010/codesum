package socket.server;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class DataTransformer {

	private String handleResult = "";
	// 读取数据处理
	public abstract String read(DataInputStream dis) throws IOException;
	
	// 读取数据处理
	public abstract String read(byte[] input) throws IOException;
	
	// 返回数据组装
	public abstract String write(String username, String s);
	
	// 读取NIOSOCKET方式，此方法必须实现
	public String getHandleResult() {
		return handleResult;
	}
	
	// 读取NIOSOCKET方式，此方法必须实现
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
}
