package test;

import java.io.UnsupportedEncodingException;

public class TestHex {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			System.out.println(new String(hex2byte("D4C4B15BB0BD4298BD7DCD26488558F1"),"GB3213"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] hex2byte(String hexStr) {
		if (null == hexStr) {
			return null;
		}
		byte[] bts = new byte[hexStr.length() / 2];
		int i = 0;
		int j = 0;
		for (; j < bts.length; j++) {
			bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
			i += 2;
		}
		return bts;
	}
	
}
