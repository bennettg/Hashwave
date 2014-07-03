package com.hashwave;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;

public class Hashwave {
	//private static byte[] iv =
		//{ 0x0a, 0x01, 0x78, 0x35, 0x35, 0x21, 0x4f, 0x73, 0x0a, 0x01, 0x78, 0x35, 0x35, 0x21, 0x4f, 0x73 };
	
	//protected String defaultCipher = "AES/CBC/PKCS5Padding";
	HashwaveGui hwGui;
	
	public static void main(String[] args){
		Hashwave h = new Hashwave();
		h.initGui();
	}
	
	
	private void initGui() {
		this.hwGui = new HashwaveGui();
		hwGui.setStyle();
	}
	
	public String encryptData (String input, String key) throws Exception {
		MCrypt mcrypt = new MCrypt(key);
		String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(input) );
		
		return encrypted;
	}
	
	public String decryptData (String input, String key) throws Exception {
		MCrypt mcrypt = new MCrypt(key);
		String decrypted = new String( mcrypt.decrypt( input ) );
		
		return decrypted;
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
