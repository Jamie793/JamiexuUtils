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
 * @time 13:36
 * @blog https://blog.jamiexu.cn
 **/
public class MD5Utils {

    /**
     * bytes转md5，默认小写
     * @param bytes 待转换数据
     * @param offset 偏移
     * @param len 长度
     * @return String
     */
    public static String md5(byte[] bytes, int offset, int len) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes, offset, len);
            return ConvertUtils.bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * bytes转md5，默认小写
     * @param bytes 数据
     * @return String
     */
    public static String md5(byte[] bytes) {
        return md5(bytes, 0, bytes.length);
    }


    /**
     * 获取文件MD5默认结果为小写
     *
     * @param path 文件路径
     * @return String 16进制文本
     */
    public static String getFileMD5(String path) {
        return getFileMD5(path, false);
    }

    /**
     * 获取文件MD5
     *
     * @param path  文件路径
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getFileMD5(String path, boolean upper) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            return ConvertUtils.bytesToHex(messageDigest.digest(), upper);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 验证文件md5
     *
     * @param path 文件路径
     * @param md5  md5值大小写都行
     * @return boolean 返回布尔值
     */
    public static boolean checkFileMD5(String path, String md5) {
        return Objects.equals(getFileMD5(path, true), md5.toUpperCase());
    }
}
