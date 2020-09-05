package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ConvertUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:34
 * @blog https://blog.jamiexu.cn
 **/
public class ShaUtils {
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
     * 获取文件Sha1
     *
     * @param path  文件路径
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getFileSha1(String path, boolean upper) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            return ConvertUtils.bytesToHex(messageDigest.digest(), upper);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件Sha1默认结果为小写
     *
     * @param path 文件路径
     * @return String 16进制文本
     */
    public static String getFileSha1(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            return ConvertUtils.bytesToHex(messageDigest.digest(), false);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件Sha256
     *
     * @param path  文件路径
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getFileSha256(String path, boolean upper) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            return ConvertUtils.bytesToHex(messageDigest.digest(), upper);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件Sha256默认结果为小写
     *
     * @param path 文件路径
     * @return String 16进制文本
     */
    public static String getFileSha256(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            return ConvertUtils.bytesToHex(messageDigest.digest(), false);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证文件sha256
     *
     * @param path   文件路径
     * @param sha256 sha256
     * @return boolean 返回布尔值
     */
    public static boolean checkFileSha256(String path, String sha256) {
        return Objects.equals(getFileSha256(path, true), sha256.toUpperCase());
    }

    /**
     * 验证文件sha1
     *
     * @param path 文件路径
     * @param sha1 sha1
     * @return boolean 返回布尔值
     */
    public static boolean checkFileSha1(String path, String sha1) {
        return Objects.equals(getFileSha1(path, true), sha1.toUpperCase());
    }
}
