package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class TestUrlDecode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String decode;
		try {
			decode = URLDecoder.decode("","UTF-8");
			System.out.println(decode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
