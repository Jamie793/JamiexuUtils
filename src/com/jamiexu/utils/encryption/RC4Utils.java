package com.jamiexu.utils.encryption;

import javax.crypto.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 8:43
 * @blog https://blog.jamiexu.cn
 **/
public class RC4Utils {


    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  加密密钥
     * @return byte[] 加密后数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        return deFinal(data, key, Cipher.ENCRYPT_MODE);
    }


    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  解密密钥
     * @return byte[] 解密后数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        return deFinal(data, key, Cipher.DECRYPT_MODE);
    }


    /**
     * RC4加解密
     *
     * @param data 待加密数据
     * @param key  加密密钥
     * @param mode 加密模式
     * @return byte[] 加密后数据
     */
    private static byte[] deFinal(byte[] data, byte[] key, int mode) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("RC4");
            keyGenerator.init(secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(mode, secretKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * RC4文件加解密
     *
     * @param inputPath  待加密文件路径
     * @param outputPath 加密后输出路径
     * @param key        密钥
     * @return boolean status
     */
    public static boolean encryptFile(String inputPath, String outputPath, byte[] key) {
        return doFinal(inputPath, outputPath, key, Cipher.ENCRYPT_MODE);
    }


    /**
     * RC4文件加解密
     *
     * @param inputPath  待解密文件路径
     * @param outputPath 解密后输出路径
     * @param key        密钥
     * @return boolean status
     */
    public static boolean decryptFile(String inputPath, String outputPath, byte[] key) {
        return doFinal(inputPath, outputPath, key, Cipher.DECRYPT_MODE);
    }


    /**
     * RC4文件加解密
     *
     * @param inputPath  待加密文件路径
     * @param outputPath 加密后输出路径
     * @param key        密钥
     * @param mode       模式
     * @return boolean status
     */
    private static boolean doFinal(String inputPath, String outputPath, byte[] key, int mode) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("RC4");
            keyGenerator.init(secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(mode, secretKey);

            fileInputStream = new FileInputStream(inputPath);
            fileOutputStream = new FileOutputStream(outputPath);

            byte[] bytes = new byte[1024 * 8];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                fileOutputStream.write(cipher.doFinal(bytes, 0, len));
            }
            status = new File(outputPath).exists();
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IOException
                | IllegalBlockSizeException | BadPaddingException e) {
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
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

}
