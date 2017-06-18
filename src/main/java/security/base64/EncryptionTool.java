package security.base64;

import java.security.MessageDigest;
import java.util.Date;

public class EncryptionTool {

	public static void main(String[] args) throws Exception {
		System.out.println("user_id:" + EncryptionTool.getUserid());
		System.out.println("password:" + EncryptionTool.getPassword());
	}
	
	public final static String getUserid()
	{
		Date d = new Date();
		long longtime = d.getTime();
		return EncryptionTool.MD5(String.valueOf(longtime));
	}
	public final static String getPassword()
	{
		Date d = new Date();
		long longtime = d.getTime();
		return EncryptionTool.MD5(String.valueOf(longtime));
	}


    public  final static String MD5(String s){
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try{
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
