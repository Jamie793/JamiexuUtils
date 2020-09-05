package com.jamiexu.utils.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:16
 * @blog https://blog.jamiexu.cn
 **/
public class DesUtils {

    private static String padding = "DES/ECB/PKCS5Padding";

    /**
     * 设置加密填充默认DES/ECB/NoPadding
     *
     * @param padding 填充模式
     */
    public static void setPadding(String padding) {
        if (padding != null)
            DesUtils.padding = padding;
        else
            DesUtils.padding = "DES/ECB/PKCS5Padding";
    }


    /**
     * Des加密
     *
     * @param bytes 待加密数据
     * @param key   加密key
     * @return byte[] 加密数据
     */
    public static byte[] encrypt(byte[] bytes, byte[] key) {
        return doFianl(bytes, key, 1);
    }


    /**
     * Des解密
     *
     * @param bytes 待解密数据
     * @param key   加密key
     * @return byte[] 解密数据
     */
    public static byte[] decrypt(byte[] bytes, byte[] key) {
        return doFianl(bytes, key, 2);
    }


    /**
     * 加密文件按
     *
     * @param path  待加密文件路径
     * @param path2 解密后文件路径
     * @param key   密钥
     * @return boolean 状态
     */
    public static boolean encryptFile(String path, String path2, byte[] key) {
        return doFianl(path, path2, key, Cipher.ENCRYPT_MODE);
    }


    /**
     * 解密文件按
     *
     * @param path  待解密文件路径
     * @param path2 加密后文件路径
     * @param key   密钥
     * @return boolean 状态
     */
    public static boolean decryptFile(String path, String path2, byte[] key) {
        return doFianl(path, path2, key, Cipher.DECRYPT_MODE);
    }


    /**
     * Des加解密
     *
     * @param bytes 需要加解密的数据
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    private static byte[] doFianl(byte[] bytes, byte[] key, int mode) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(mode, securekey, random);
            return cipher.doFinal(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密文件
     *
     * @param path  待处理文件路径
     * @param path2 处理后文件路径
     * @param key   密钥
     * @param mode  模式
     * @return boolean 状态
     */
    private static boolean doFianl(String path, String path2, byte[] key, int mode) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(mode, securekey, random);
            fileInputStream = new FileInputStream(path);
            fileOutputStream = new FileOutputStream(path2);
            byte[] byt = new byte[1024 * 8];
            int len;
            while ((len = fileInputStream.read(byt)) != -1) {
                fileOutputStream.write(cipher.doFinal(byt, 0, len));
            }
            status = new File(path2).exists();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }


}
