package test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import security.base64.CinBase64;

public class TestHex {
	
	static int a = 0;

	public static void main(String[] args) {

		try {
			int x = 2,y = 2;
			x = (y = 1);
			System.out.println(Runtime.getRuntime().availableProcessors());
			System.out.println(x);
			System.out.println(y);
			
//			System.out.println(new String(hex2byte("030105048D0A850300")));
//			System.out.println(new String(hex2byte("030105809CAE220300"), "utf8"));
			System.out.println(TestHex.class.getClassLoader().getSystemClassLoader());
			System.out.println((int)(~(-3)));
			int i = (15>>2);
			// -15=10001111 12=00001100 
			System.out.println(i);
			System.out.println(Integer.MAX_VALUE);
			System.out.println(Integer.MIN_VALUE);
			System.out.println(testint());
			System.out.println(a);
			System.out.println(15116963076L);
			System.out.println(getInt64(setInt64(15116963076L)));
//			System.out.println(CinBase64.decode("gAENMTAuMC4wLjc6OTA4NgcMdXJhcHBvcnQubmV0CAgxMjUyMDAwMQk8aHR0cHM6Ly96aC5jbWJjaGluYS5jb20vcXIvYmY5NTEyNTFlOWFiNjUyZTFiZmQ3N2ZhY2E1YTgzMTMvCk5eMSgzWzQtOV18NVswMTI3ODldfDhbNzhdKVxkezh9JHxeMThbMDI5XVxkezh9JHxeMSgzWzAtM118NVswNTZdfDhbMjU2XSlcZHs4fSQLJ15bXEBBLVphLXowLTlcIVwjXCRcJVxeXCZcKlwuXH5dezYsMTZ9JAwkaHR0cDovLzEyNC4xOTMuMTgzLjk2OjgwODkvYXBwL2luZGV4DSNodHRwOi8vMTI0LjE5My4xODMuOTY6ODA4Ny9jaGFubmVsLxclaHR0cDovLzk5LjEyLjkwLjEwMjo5MDE2L2Rvd25sb2FkLmpzcBgjaHR0cDovLzQ5LjQwLjIuMjQ6ODA4MC9vZmZpY2lhbC9mYXEZMmh0dHA6Ly8xMC4xMC4yMDkuMzY6ODA4Ny9jb25kaXRpb24vY29uZGl0aW9ucy5odG1sHDVodHRwczovL3B1cHN0LnpoLmNtYmNoaW5hLmNvbTo4MTEwL2hpc3RvcnlMaXN0LmFjdGlvbhogaHR0cDovLzk5LjEyLjkwLjEwMjo5NzE2L3JlY2VpdmXhBDIwNDjiAzEyOP0saHR0cDovLzk5LjYuMTM2LjUzOjkxMTAvYXV0aG9yaXphdGlvbi5hY3Rpb24hACIAIwAA"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int testint() {
		return a++;
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
	
	public static byte[] setInt64(long value)
	{
		if (value != 0)
		{
			int zeros = Long.numberOfLeadingZeros(value);
			int length = 8 - zeros / 8;
			byte[] rawValue = new byte[length];
			for (int i = 0; i < length; i++)
			{
				rawValue[i] = (byte) (value >>> ((i) * 8));
			}
			return setValue(rawValue);
		}
		else
		{
			return setValue(new byte[]{(byte) 0});
		}
	}
	
	public static long getInt64(byte[] value)
	{
		if (value.length > 8)
			return -1;
		byte[] buff = new byte[8];
		int i = 7;
		for (byte b : value)
		{
			buff[i--] = b;
		}
		return ByteBuffer.wrap(buff).getLong();
	}

	public static byte[] setValue(byte[] value)
	{
		if (value != null && value.length > 255)
		{
			return null;
		}
		else
		{
			return value;
		}
	}
}
