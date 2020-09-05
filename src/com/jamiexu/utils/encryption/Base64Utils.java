package com.jamiexu.utils.encryption;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.file.FileUtils;

import java.io.*;
import java.util.Base64;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:39
 * @blog https://blog.jamiexu.cn
 **/
public class Base64Utils {


    /**
     * 编码成Base64字符串
     * @param data 数据
     * @return String
     */
    public static String encodeStr(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }


    /**
     * 解码Base64字符串
     * @param data 数据
     * @return byte[]
     */
    public static byte[] decodeStr(String data){
        return Base64.getDecoder().decode(data);
    }


    /**
     * 编码成Base64
     * @param data 数据
     * @return byte[]
     */
    public static byte[] encode(byte[] data){
        return Base64.getEncoder().encode(data);
    }

    /**
     * 解码Base64
     * @param data 数据
     * @return byte[]
     */
    public static byte[] decode(byte[] data){
        return Base64.getDecoder().decode(data);
    }

    /**
     * 获取文件Base64,并把编码数据写出
     *
     * @param path   文件路径
     * @param target 写出编码后数据文件路径
     * @return boolean 状态
     */
    public static boolean getFileBase64AndWrite(String path, String target) {
        boolean status = false;
        Base64.Encoder encoder = Base64.getEncoder();
        FileInputStream fileInputStream = null;
        PrintWriter printWriter = null;
        try {
            fileInputStream = new FileInputStream(path);
            printWriter = new PrintWriter(new FileOutputStream(target));
            byte[] byts = new byte[1024 * 8];
            int len;
            while ((len = fileInputStream.read(byts)) != -1) {
                if (len != byts.length) {
                    byte[] copy = ByteUtils.copyBytes(byts, 0, len);
                    printWriter.println(encoder.encodeToString(copy));
                } else {
                    printWriter.println(encoder.encodeToString(byts));
                }
            }
            if (new File(target).exists())
                status = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
        return status;
    }

    /**
     * 获取小文件的Base64并返回结果
     *
     * @param path 待获取文件路径
     * @return String 结果
     */
    public static String getSmallFileBase64(String path) {
        return Base64.getEncoder().encodeToString(FileUtils.readFile(path));
    }

}
