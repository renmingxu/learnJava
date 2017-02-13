package com.renmingxu.test.encrypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by renmingxu on 2017/2/12.
 */
public class AesCbcPKCS5PaddingEncrypter {
    private byte[] key;
    private byte[] iv;
    public AesCbcPKCS5PaddingEncrypter(byte[] key, byte[] iv)
            throws InvalidAlgorithmParameterException, InvalidKeyException {
        try {
            this.key = key;
            this.iv = iv;
            SecretKey secretKey = new SecretKeySpec(this.key, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(this.iv));
        } catch (InvalidAlgorithmParameterException e) {
            throw e;
        } catch (InvalidKeyException e) {
            throw e;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public AesCbcPKCS5PaddingEncrypter(String key, String iv)
            throws UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException {
        this(key.getBytes("UTF-8"), iv.getBytes("UTF-8"));
    }
    public static AesCbcPKCS5PaddingEncrypter getInstance(String key, String iv) {
        try {
            return new AesCbcPKCS5PaddingEncrypter(key, iv);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static AesCbcPKCS5PaddingEncrypter getInstance(byte[] key, byte[] iv) {
        try {
            return new AesCbcPKCS5PaddingEncrypter(key, iv);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public byte[] encrypt(byte[] content) {
        return AES.CBC_Encrypt(content, key, iv);
    }
    public byte[] encrypt(String contentstr) {
        try {
            return AES.CBC_Encrypt(contentstr.getBytes("UTF-8"), key, iv);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public byte[] decrypt(byte[] contentencrypt) {
        return AES.CBC_Decrypt(contentencrypt, key, iv);
    }
    public byte[] decrypt(byte[] contentencrypt, int indexbegin, int length) {
        byte[] contentfinal = new byte[length];
        System.arraycopy(contentencrypt, indexbegin, contentfinal, 0, length);
        return AES.CBC_Decrypt(contentfinal, key, iv);
    }
    public byte[] decryptwithzero(byte[] contentencrypt) {
        int i = contentencrypt.length;
        while(contentencrypt[--i] == 0);
        byte[] contentencryptwithoutzero = new byte[++i];
        System.arraycopy(contentencrypt, 0, contentencryptwithoutzero, 0, i);
        return AES.CBC_Decrypt(contentencryptwithoutzero, key, iv);
    }
    public String decryptToStr(byte[] contentencrypt) {
        try {
            return new String(AES.CBC_Decrypt(contentencrypt, key, iv), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
