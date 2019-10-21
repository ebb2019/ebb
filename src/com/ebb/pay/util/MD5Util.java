package com.ebb.pay.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;



public class MD5Util{

    public static String encode(String origin, String charsetname){
        char[] resultString = null;
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("MD5");
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        if(charsetname == null || "".equals(charsetname)){
            resultString = Hex.encodeHex(md.digest(origin.getBytes()));
        }else{
            try{
                resultString = Hex.encodeHex(md.digest(origin.getBytes(charsetname)));
            }catch(UnsupportedEncodingException e){
                throw new RuntimeException(e);
            }
        }
        return new String(resultString);
    }
    
    
    public static String MD5Encode(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("UTF8"));
 
            String result = "";
            byte[] temp;
            temp = md5.digest(key.getBytes("UTF8"));
             
            for (int i = 0; i < temp.length; i++) {
                result += Integer.toHexString(
                        (0x000000ff & temp[i]) | 0xffffff00).substring(6);
            }
            return result;
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String mdecode(String str) throws Exception {
    	
    	MessageDigest  md = MessageDigest.getInstance("md5");
    	md.update(str.getBytes());
    	byte[] b  = md.digest();
    	StringBuffer sb = new StringBuffer() ;
    	for (byte c : b){
    		sb.append(Character.forDigit((c>>4)&0xf,16));
    		sb.append(Character.forDigit(c & 0xf, 16)); 
    	}
    	return sb.toString();
    }
}