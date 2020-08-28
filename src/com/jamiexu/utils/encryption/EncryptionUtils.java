package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

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
     * @param pass  加解密密钥
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    public static byte[] enAes(byte[] bytes, byte[] pass, int mode) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKeySpec key = new SecretKeySpec(pass, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(mode, key);
            return cipher.doFinal(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Des加解密
     *
     * @param bytes 需要加解密的数据
     * @param pass  加解密密钥 必须要的倍数
     * @param mode  1加密，2解密
     * @return byte[] 加密后的数据
     */
    public static byte[] enDes(byte[] bytes, byte[] pass, int mode) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(pass);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, securekey, random);
            return cipher.doFinal(bytes);// 加密
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    public static long getAlder32(byte[] bytes){
        Checksum checksumEngine = new Adler32();
        checksumEngine.update(bytes,0,bytes.length);
        return checksumEngine.getValue();
    }


    /**
     * URL编码
     *
     * @param content 需要转换的内容
     * @param mode    编码解码 1编码，2解码
     * @return
     */
    public static String enUrl(String content, int mode) throws UnsupportedEncodingException {
        if (mode == 1)
            return URLEncoder.encode(content, "utf-8");
        return URLDecoder.decode(content, "utf-8");
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
     * @return byte[]
     */
    public static byte[] getSha1(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            return messageDigest.digest(bytes);
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
     *
     * @param unicode 需要解码的内容
     * @return String 解码后的内容
     */
    public static String deUnicode(String unicode) {
        String[] strs = unicode.split("\\\\u");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < strs.length; i++) {
            stringBuilder.append((char) Integer.valueOf(strs[i], 16).intValue());
        }
        return stringBuilder.toString();
    }

}
