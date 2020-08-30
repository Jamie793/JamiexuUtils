package com.jamiexu.utils.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
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


    public static byte[] longToBytes(long l) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(l);
        return byteBuffer.array();
    }


    /**
     * bytes数组转16进制文本,默认小写
     *
     * @param bytes bytes数组
     * @return String 16进制文本
     */
    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, false);
    }

    /**
     * bytes数组反转16进制文本,默认小写
     *
     * @param bytes bytes数组
     * @return String 16进制文本
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; i--) {
            stringBuilder.append(String.format("%02x", bytes[i] & 0xFF));
        }
        return stringBuilder.toString();
    }


    /**
     * int抓leb128可变长字节数组
     * @param value int
     * @return byte[]
     */
    public static byte[] intToULeb128(int value) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while ((value & 0xffffffffL) > 0x7f) {
            byteArrayOutputStream.write((value & 0x7f) | 0x80);
            value >>>= 7;
        }
        byteArrayOutputStream.write(value);
        return byteArrayOutputStream.toByteArray();
    }



    /**
     * bytes数组反转16进制文本,默认小写
     *
     * @param bytes bytes数组
     * @param c     分隔符
     * @return String 16进制文本
     */
    public static String bytes2Hex(byte[] bytes, char c) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; i--) {
            stringBuilder.append(String.format("%02x", bytes[i] & 0xFF)).append(c);
        }
        return stringBuilder.toString();
    }


    public static byte[] int2Bytes(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(n);
        byte[] bytes = byteBuffer.array();
        byte[] bytes1 = new byte[bytes.length];
        for (int i = bytes.length - 1; i >= 0; i--) {
            bytes1[bytes.length - i - 1] = bytes[i];
        }
        return bytes1;
    }

    /**
     * bytes数组转16进制文本,自定义分隔符
     *
     * @param bytes bytes数组
     * @return String 16进制文本
     */
    public static String bytesToHex(byte[] bytes, char c) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b & 0xFF)).append(c);
        }
        return stringBuilder.toString();
    }


    /**
     * bytes转short
     *
     * @param bytes bytes数组
     * @return short值
     */
    public static short bytesToShort(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getShort();
    }


    /**
     * bytes数组反转后转int
     *
     * @param res bytes数组
     * @return int
     */
    public static int bytes2Int(byte[] res) {
        int targets = (res[0] & 0xff)
                | ((res[1] << 8) & 0xff00)
                | ((res[2] << 24) >>> 8)
                | (res[3] << 24);
        return targets;
    }

    /**
     * bytes转int
     *
     * @param bytes bytes数组
     * @return short
     */
    public static int bytesToInt(byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            return dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
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

    /**
     * 16进制转Base64
     *
     * @param hex 十六进制的数据
     * @return String base64文本
     */
    public static String hexToBase64(String hex) {
        return bytesToBase64(hexToBytes(hex));
    }

    /**
     * Base64转16进制
     *
     * @param base64 Base64文本
     * @param upper  返回结果是否大写
     * @return String 16进制
     */
    public static String base64ToHex(String base64, boolean upper) {
        return bytesToHex(base64ToBytes(base64), upper);
    }


}
