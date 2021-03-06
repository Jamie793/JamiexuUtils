package com.jamiexu.utils.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 8:29
 * @blog https://blog.jamiexu.cn
 **/
public class AESUtils {

    private static String padding = "AES/ECB/PKCS5Padding";

    /**
     * 设置加密填充默认AES/ECB/PKCS5Padding
     *
     * @param padding 填充模式
     */
    public static void setPadding(String padding) {
        if (padding != null)
            AESUtils.padding = padding;
        else
            AESUtils.padding = "AES/ECB/PKCS5Padding";
    }


    /**
     * Aes加密
     *
     * @param bytes 待加密数据
     * @param key   加密key
     * @return byte[] 加密数据
     */
    public static byte[] encrypt(byte[] bytes, byte[] key) {
        return doFianl(bytes, key, 1);
    }


    /**
     * Aes解密
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
     * Aes加解密
     *
     * @param bytes 需要加解密的数据
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    private static byte[] doFianl(byte[] bytes, byte[] key, int mode) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(mode, secretKeySpec);
            return cipher.doFinal(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加秘密文件
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
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(padding);
            cipher.init(mode, secretKeySpec);
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
