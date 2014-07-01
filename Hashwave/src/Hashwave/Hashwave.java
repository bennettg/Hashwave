package Hashwave;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

public class Hashwave {
	private static byte[] iv =
		{ 0x0a, 0x01, 0x78, 0x35, 0x35, 0x21, 0x4f, 0x73 };
	
	protected String defaultCipher = "AES";
	HashwaveGui hwGui;
	
	public static void main(String[] args){
		new Hashwave();
	}
	
	public Hashwave () {
		this.hwGui = new HashwaveGui();
	}
	
	public byte[] encryptData (byte[] input, SecretKey key) throws Exception {
	  Cipher cipher = Cipher.getInstance(this.defaultCipher);
	    IvParameterSpec ips = new IvParameterSpec(Hashwave.iv);
	    cipher.init(Cipher.ENCRYPT_MODE, key, ips);
	    
	    return cipher.doFinal(input);
	}
	
	public byte[] decryptData (byte[] input, SecretKey key) throws Exception {
	  Cipher cipher = Cipher.getInstance(this.defaultCipher);
	    IvParameterSpec ips = new IvParameterSpec(Hashwave.iv);
	    cipher.init(Cipher.DECRYPT_MODE, key, ips);
	    
	    return cipher.doFinal(input);
	}
	
	public String maskData (byte[] input){
		byte[] encoded = Base64.encodeBase64(input);
		return encoded.toString();
	}
	
	public byte[] unmaskData (String input){
		byte[] decoded = Base64.decodeBase64(input);
		return decoded;
	}
}
