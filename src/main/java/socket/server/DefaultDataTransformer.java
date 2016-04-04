package socket.server;

import java.io.DataInputStream;
import java.io.IOException;

public class DefaultDataTransformer extends DataTransformer {
	private String testValue = "";

	@Override
	public String read(DataInputStream dis) throws IOException {
		byte[] btSize = new byte[10 * 1024];
		dis.read(btSize, 0, 10 * 1024);
		testValue = new String(btSize);
		String read = "read " + testValue.trim();
		System.out.println(read + " start:\t" + System.currentTimeMillis());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		System.out.println(read + " end:\t" + System.currentTimeMillis());
		return read;
	}

	@Override
	public String write(String username, String s) {
		String write = "read " + testValue.trim() + ",then write " + s.trim();
		System.out.println(write + " start:\t" + System.currentTimeMillis());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		System.out.println(write + " end:\t" + System.currentTimeMillis());
		return write;
	}

	@Override
	public String read(byte[] input) throws IOException {
		testValue = new String(input).trim();
		String read = "read " + testValue.trim();
		System.out.println(read + " start:\t" + System.currentTimeMillis());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		System.out.println(read + " end:\t" + System.currentTimeMillis());
		return read;
	}
}
