package com.jamiexu.utils.convert;

import java.util.Base64;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class ConvertUtils {
    /**
     * 16进制文本转byte数组
     *
     * @param hex 16进制文本
     * @return byte[] byte数组
     */
    public static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i = i + 2) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(i, i + 1), 16);
        }
        return bytes;
    }

    /**
     * bytes数组转16进制文本
     *
     * @param bytes bytes数组
     * @param upper 是否大小写
     * @return String 16进制文本
     */
    public static String bytesToHex(byte[] bytes, boolean upper) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b & 0xFF));
        }
        if (upper)
            return stringBuilder.toString().toUpperCase();
        return stringBuilder.toString();
    }

    /**
     * bytes数组转Base64
     *
     * @param bytes bytes数组
     * @return String Base64文本
     */
    public static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64文本转bytes数组
     *
     * @param base64 Base64文本
     * @return byte[] bytes驻足
     */
    public static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * bytes数组转String
     *
     * @param bytes bytes数组
     * @return 文本
     */
    public static String bytesToString(byte[] bytes) {
        return new String(bytes, 0, bytes.length);
    }



}
