package com.hashwave;

import java.security.NoSuchAlgorithmException;





import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MCrypt {

        static char[] HEX_CHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        private String iv = initIV();//"47fj568fjdk586kfj";//Dummy iv (CHANGE IT!)0
        private IvParameterSpec ivspec; 

        private SecretKeySpec keyspec;
        private Cipher cipher;

        private String SecretKey = "0123456789abcdeg";//Dummy secretKey (CHANGE IT!)f

        public MCrypt(String key)
        {

                if(key!=null && !key.isEmpty()){
                	this.SecretKey = key;
                }
            	keyspec = new SecretKeySpec(SecretKey.getBytes(), "AES");


                try {
                        cipher = Cipher.getInstance("AES/CBC/NoPadding");
                } catch (NoSuchAlgorithmException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }

        public String encrypt(String text) throws Exception {
            
        	ivspec = new IvParameterSpec(iv.getBytes());
        	
                if(text == null || text.length() == 0)
                        throw new Exception("Empty string");

                byte[] encryptedData = null;
                String finalEncrypted = null;

                try {
                        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

                        encryptedData = cipher.doFinal(padString(text).getBytes());
                        
                        finalEncrypted = MCrypt.bytesToHex(encryptedData);
                        finalEncrypted = MCrypt.bytesToHex(((encryptedData +"_*()*_"+iv).getBytes()));

                } catch (Exception e)
                {                       
                        throw new Exception("[encrypt] " + e.getMessage());
                }

                return finalEncrypted;
        }

        public String decrypt(String code) throws Exception {
                if(code == null || code.length() == 0)
                        throw new Exception("Empty string");

                String finalDecrypted = null;
                String decryptedData = null;
                byte[] decryptedE = null;
                String thisiv;

                try {   
                		byte[] decryptedA = hexToBytes(code);
                		String decryptedB = new String(decryptedA, "UTF-8");;
                		String[] decryptedC = code.split("_*()*_");	
                        	decryptedData = decryptedC[0];
                        	thisiv = decryptedC[1];
                        	
                        	
                        ivspec = new IvParameterSpec(iv.getBytes());
                        
                        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
                        	
                        decryptedE = cipher.doFinal(hexToBytes(decryptedData));
  
                        //Remove trailing zeroes
                        if( decryptedE.length > 0)
                        {
                            int trim = 0;
                            for( int i = decryptedE.length - 1; i >= 0; i-- ) if( decryptedE[i] == 0 ) trim++;

                            if( trim > 0 )
                            {
                                byte[] newArray = new byte[decryptedE.length - trim];
                                System.arraycopy(decryptedE, 0, newArray, 0, decryptedE.length - trim);
                                decryptedE = newArray;
                            }
                        }
                } catch (Exception e)
                {
                        throw new Exception("[decrypt] " + e.getMessage());
                }
                
                finalDecrypted = new String(decryptedE,"UTF-8");
                return finalDecrypted;
        }      


        public static String bytesToHex(byte[] buf)
        {
            char[] chars = new char[2 * buf.length];
            for (int i = 0; i < buf.length; ++i)
            {
                chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
                chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
            }
            return new String(chars);
        }


        public static byte[] hexToBytes(String str) {
                if (str==null) {
                        return null;
                } else if (str.length() < 2) {
                        return null;
                } else {
                        int len = str.length() / 2;
                        byte[] buffer = new byte[len];
                        for (int i=0; i<len; i++) {
                                buffer[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2),16);
                        }
                        return buffer;
                }
        }

        
        private static String initIV() {
			String iv = "";
	
			RandomGen rand = new RandomGen();
			iv = iv + rand.generateRandom(9, 16);
			return iv;
        }
        
        private static String appendSpecial() {
        	String sp = "";
        	
        	RandomGen rand = new RandomGen();
        	sp = sp + rand.generateRandom(9, 9);
        	return sp;
        }


        private static String padString(String source)
        {
          char paddingChar = 0;
          int size = 16;
          int x = source.length() % size;
          int padLength = size - x;

          for (int i = 0; i < padLength; i++)
          {
                  source += paddingChar;
          }

          return source;
        }
}