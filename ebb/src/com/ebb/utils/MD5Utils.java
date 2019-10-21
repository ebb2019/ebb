package com.ebb.utils;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    public static String toHexString(byte[] b) { //String to byte

        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
            return sb.toString();
    }
    
    public static String ToMd5(String s){
        try { // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();
	        return toHexString(messageDigest);
	    }catch (NoSuchAlgorithmException e){
	        e.printStackTrace();
	    }
        return "";
    }

    
    /**
     * MD5加密
     * @param credentials 加密字符串
     * @param count  加密次数
     * @return
     */
//    public static String  encryptPwd(String password) {
//    	return ToMd5(ToMd5(password+"@alston.com"));
//    }



}
