package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.CRC32;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class EncryptionUtils {
    /**
     * Aes加解密
     *
     * @param bytes 需要加解密的数据
     * @param salt  加解密密钥
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    public static byte[] enAes(byte[] bytes, byte[] salt, int mode) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(salt));// 利用用户密码作为随机数初始化出
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(mode, key);// 初始化为加密模式的密码器
            return cipher.doFinal(bytes);// 加密
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Des加解密
     *
     * @param bytes 需要加解密的数据
     * @param salt  加解密密钥
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    public static byte[] enDes(byte[] bytes, byte[] salt, int mode) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(salt);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            return cipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * URL编码
     *
     * @param content 需要转换的内容
     * @param mode    编码解码 1编码，2解码
     * @return
     */
    public static String enUrl(String content, int mode) {
        if (mode == 1)
            return URLEncoder.encode(content, StandardCharsets.UTF_8);
        return URLDecoder.decode(content, StandardCharsets.UTF_8);
    }

    /**
     * 获取MD5
     *
     * @param bytes 需要获取md5的bytes数组
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getMd5(byte[] bytes, boolean upper) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return ConvertUtils.bytesToHex(messageDigest.digest(bytes), upper);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Sha1
     *
     * @param bytes 需要获取sha1的bytes数组
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getSha1(byte[] bytes, boolean upper) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            return ConvertUtils.bytesToHex(messageDigest.digest(bytes), upper);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Sha256
     *
     * @param bytes 需要获取sha256的bytes数组
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getFileSha256(byte[] bytes, boolean upper) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return ConvertUtils.bytesToHex(messageDigest.digest(bytes), upper);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CRC32
     *
     * @param bytes 需要获取crc32的bytes数组
     * @return Long
     */
    public static Long getFileCrc32(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return crc32.getValue();
    }

    /**
     * 编码成unicode
     *
     * @param content 需要编码的内容
     * @return String 解码后的内容
     */
    public static String enUnicode(String content) {
        char[] chars = content.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            stringBuilder.append("\\u").append(Integer.toString(chars[i], 16));
        }
        return stringBuilder.toString();
    }


    /**
     * 解码unicode
     * @param unicode 需要解码的内容
     * @return String 解码后的内容
     */
    public static String deUnicode(String unicode) {
        String[] strs = unicode.split("\\\\u");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < strs.length; i++) {
            stringBuilder.append((char)Integer.valueOf(strs[i], 16).intValue());
        }
        return stringBuilder.toString();
    }

}
