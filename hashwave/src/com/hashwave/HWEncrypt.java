package com.hashwave;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class HWEncrypt {

        static char[] HEX_CHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        private String iv = initIV();
        private String ivl2 = "fh37fjght7586jf8";
        
        private IvParameterSpec ivspec1;
        private IvParameterSpec ivspec12;
        private SecretKeySpec keyspec;
        private Cipher cipher;

        private String Key = "hw7314";
        /*
         * hw special pre key (this means no other program or algorithm aside from Hashwave could decrypt this
         */

        public HWEncrypt(String key) throws NoSuchAlgorithmException {
                ivspec1 = new IvParameterSpec(iv.getBytes());
                ivspec12 = new IvParameterSpec(ivl2.getBytes());

                //add the two keys together (the 12 digit static key + the 4 digit user defined combo)
                byte[] useKey = (this.Key + key).getBytes();
                
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                useKey = sha.digest(useKey);
                useKey = Arrays.copyOf(useKey, 16); // use only the first 128 bit
            	keyspec = new SecretKeySpec(useKey, "AES");


                try {
                        cipher = Cipher.getInstance("AES/CBC/NoPadding");
                } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                }
        }

        public String encrypt(String text) throws Exception {
        	
                if(text == null || text.length() == 0)
                        throw new Exception("Empty string");

                byte[] encrypted = null;
                byte[] finalEncrypted = null;

                try {
                        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec1);

                        encrypted = cipher.doFinal(padString(text).getBytes());
                        String encryptedE = new String(bytesToHex(encrypted))+"--"+appendIV();
                        
                        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec12);

                        encrypted = cipher.doFinal(padString(encryptedE).getBytes()); //returns bytes
                        finalEncrypted = encrypted;

                } catch (Exception e)
                {                       
                        throw new Exception("[encrypt] " + e.getMessage());
                }

                String rfinalstring = new String(bytesToHex(finalEncrypted));
                //rfinalstring = str_rot(rfinalstring, 14);
                
                return rfinalstring;
        }

        public String decrypt(String code) throws Exception {
                if(code == null || code.length() == 0)
                        throw new Exception("Empty string");

                byte[] decrypted = null;
                byte[] finalDecrypted = null;
                

                try {
                		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec12);
                        decrypted = cipher.doFinal(hexToBytes(code));
                        
                      //Remove trailing zeroes
                        if( decrypted.length > 0)
                        {
                            int trim = 0;
                            for( int i = decrypted.length - 1; i >= 0; i-- ) if( decrypted[i] == 0 ) trim++;

                            if( trim > 0 )
                            {
                                byte[] newArray = new byte[decrypted.length - trim];
                                System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                                decrypted = newArray;
                            }
                        }
                } catch (Exception e)
                {
                      throw new Exception("[decrypt-layer 2] " + e.getMessage());
                }
                
        
                        String decryptedD = new String(decrypted);
                        
                        String[] decryptedDD = decryptedD.split("--");
                        	String decryptedData = decryptedDD[0];
                        	String thisiv = decryptedDD[1];
                        
                try {
                        	ivspec1 = new IvParameterSpec(thisiv.getBytes());
                    
                        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec1);

                        finalDecrypted = cipher.doFinal(hexToBytes(decryptedData));
                        //Remove trailing zeroes
                        if( finalDecrypted.length > 0)
                        {
                            int trim = 0;
                            for( int i = decrypted.length - 1; i >= 0; i-- ) if( decrypted[i] == 0 ) trim++;

                            if( trim > 0 )
                            {
                                byte[] newArray = new byte[decrypted.length - trim];
                                System.arraycopy(decrypted, 0, newArray, 0, decrypted.length - trim);
                                decrypted = newArray;
                            }
                        }
                } catch (Exception e)
                {
                        throw new Exception("[decrypt-layer 1] " + e.getMessage());
                }
                return new String(finalDecrypted);
        }      


        public static String bytesToHex(byte[] buf) {
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
        
        private String appendIV () {
        	return iv;
        }

        private static String str_rot(String s, int n) {
        	String[] letters = {"A","a","B","b","C","c","D","d","E","e","F","f","G","g","H","h","I","i","J","j","K","k","L"
        						,"l","M","m","N","n","O","o","P","p","Q","q","R","r","S","s","T","t","U","u","V","v","W","w",
        						"X","x","Y","y","Z","z"};
        	//n = (int)n %26;

        	for(int x=0; x < letters.length; x++){
        		s = s.replace(letters[x], letters[arraySN(x , n, letters.length)]);
        	}
        	
        	return s;
        	
        }
        
        /*
         * x being the index number/key you are at in the string you are working on
         * n being the jump to index/key number you are trying to jump to in the array of A
         * l being the length of the array A
         */
        private static int arraySN (int x, int n, int l) {
        	// if running index + the jump to index is bigger than the length of A
        		int j;
        		int r;
        	// we are rotating to encrypt
	        if(isPositive(n)){
	        	if((x+n) >= l){
	        		r = (x+n) - l;
	        		j = 0 + r;
	        			if(j == 0) j = 2;
	        	}else{
	        		j = n;
	        	}
	        //we are rotating backwards to decrypt
	        }else{
	        	int pn = Math.abs(n);
	        	if((x-pn) <= 0){
		        	j = l - (mPositive(x-pn));
	        	}
	        	j = x-pn;
	        	
	        }
	        
	        return j;
        }
        
        private static int mPositive (int n){
        	return Math.abs(n);
        }
        
        private static boolean isPositive (int n){
        	boolean isPositive = ((n % (n - 0.5)) * n) / 0.5 == n;
        	return isPositive;
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