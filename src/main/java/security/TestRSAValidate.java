package security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import javax.security.cert.CertificateException;

import sun.misc.BASE64Decoder;
import test.TestHex;

public class TestRSAValidate {

	private static final String key = "";
	private static final String token = "";
	public static void main(String[] args) {
		String data = "{\"openid\":\"4CF6820EDB3777CAF24E57BAD7C138E5\", \"datetime\":\"1457419359168\"}";
		try {
			System.out.println(verify(data, token));
		} catch (CertificateException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean verify(String data, String signature) throws CertificateException {

		try {
			X509EncodedKeySpec keySpec=new X509EncodedKeySpec(TestHex.hex2byte(key));
			KeyFactory keyFactory=KeyFactory.getInstance("RSA"); 
            PublicKey publickey=keyFactory.generatePublic(keySpec);  //生成公钥时报错 

			Signature signatureChecker = Signature.getInstance("SHA1withRSA");
			signatureChecker.initVerify(publickey);
			signatureChecker.update(data.getBytes());
			return signatureChecker.verify(new BASE64Decoder().decodeBuffer(signature));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
