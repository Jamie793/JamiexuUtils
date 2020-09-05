package com.jamiexu.utils.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 8:29
 * @blog https://blog.jamiexu.cn
 **/
public class AesUtils {

    private static String padding = "AES/ECB/PKCS5Padding";


    /**
     * 设置加密填充默认AES/ECB/PKCS5Padding
     * @param padding 填充模式
     */
    public static void setPadding(String padding) {
        AesUtils.padding = padding;
    }

    /**
     * Aes加密
     * @param bytes 待加密数据
     * @param key 加密key
     * @return byte[] 加密数据
     */
    public static byte[] encrypt(byte[] bytes,byte[] key){
        return doFianl(bytes,key,1);
    }



    /**
     * Aes解密
     * @param bytes 待解密数据
     * @param key 加密key
     * @return byte[] 解密数据
     */
    public static byte[] decrypt(byte[] bytes,byte[] key){
        return doFianl(bytes,key,2);
    }


    /**
     * Aes加解密
     *
     * @param bytes 需要加解密的数据
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    private static byte[] doFianl(byte[] bytes, byte[] key, int mode) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(mode, secretKeySpec);
            return cipher.doFinal(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }



}
