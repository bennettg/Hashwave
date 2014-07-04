package com.hashwave;

import org.apache.commons.codec.binary.Base64;

public class Hashwave {

	HashwaveGui hwGui;
	RandomGen randgen;
	MCryptH mcrypt;
	static String versionNumber = "v1.1.0";
	static String aboutMessage = "You are using Hashwave "+versionNumber+" \r\n Developed by Sharetunnel Inc. \r\n "
			+ "Hashwave originally started out as a Web Application developed by Bennett Gibson \r\n kind of for the heck of"
			+ " it but also to allow people to have fun encrypting whatever data they wanted to. Its very bare and simple for now.";
	
	public static void main(String[] args){
		Hashwave h = new Hashwave();
		h.initGui();
	}
	
	
	private void initGui() {
		this.hwGui = new HashwaveGui();
		hwGui.setStyle();
	}
	
	public String encryptData (String input, String key) throws Exception {
		try{
			mcrypt = new MCryptH(key);
		}catch(Exception e){
			return e.toString();
		}
		
			String encrypted = mcrypt.encrypt(input);
		return encrypted;
	}
	
	public String decryptData (String input, String key) throws Exception {
		randgen = new RandomGen();
		
		try {
			mcrypt = new MCryptH(key);
		}catch(Exception e){
			return e.toString();
		}
		try {
			String decrypted = mcrypt.decrypt( input );
			return decrypted.trim();
		}catch(ArrayIndexOutOfBoundsException e){
			return "";
		}
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
