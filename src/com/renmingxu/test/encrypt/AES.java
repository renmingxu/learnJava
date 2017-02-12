package com.renmingxu.test.encrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by renmingxu on 2017/2/11.
 */
public class AES {
    private static final byte[] pad = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    public  static String AesCbcMode = "AES/CBC/PKCS5Padding";
    public  static String AesEcbMode = "AES/ECB/PKCS5Padding";
    public static String CBC_Encrypt(String content, String key, String iv) {
        try {
            return new String(
                    CBC_Encrypt(
                            content.getBytes("UTF-8"),
                            key.getBytes("UTF-8"),
                            iv.getBytes("UTF-8")
                            ),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    };
    public static String CBC_Decrypt(String content, String key, String iv) {
        try {
            return new String(
                    CBC_Decrypt(
                            content.getBytes("UTF-8"),
                            key.getBytes("UTF-8"),
                            iv.getBytes("UTF-8")
                    ),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    };
    public static String ECB_Encrypt(String content, String key) {
        try {
            return new String(
                    ECB_Encrypt(
                            content.getBytes("UTF-8"),
                            key.getBytes("UTF-8")
                    ),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String ECB_Decrypt(String content, String key) {
        try {
            return new String(
                    ECB_Decrypt(
                            content.getBytes("UTF-8"),
                            key.getBytes("UTF-8")
                    ),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static byte[] CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try{
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance(AesCbcMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){

        try{
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher=Cipher.getInstance(AesCbcMode);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] ECB_Encrypt(byte[] content, byte[] keyBytes){

        try{
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher=Cipher.getInstance(AesEcbMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] ECB_Decrypt(byte[] content, byte[] keyBytes){

        try{
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher=Cipher.getInstance(AesEcbMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result=cipher.doFinal(content);
            return result;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] getPaddedContent(byte[] content) {
        int l = ((int) content.length / 16 + 1) * 16 - content.length;
        byte[] content_padded = new byte[content.length + l];
        System.arraycopy(content, 0, content_padded, 0, content.length);
        System.arraycopy(pad, 0, content_padded, content.length, l);
        return content_padded;
    }
    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
