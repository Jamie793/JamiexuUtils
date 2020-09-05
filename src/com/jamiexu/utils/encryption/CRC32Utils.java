package com.jamiexu.utils.encryption;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:38
 * @blog https://blog.jamiexu.cn
 **/
public class CRC32Utils {

    /**
     * 获取文件CRC32
     *
     * @param path 文件路径
     * @return Long
     */
    public static long getFileCrc32(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            CRC32 crc32 = new CRC32();
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                crc32.update(bytes, 0, len);
            }
            return crc32.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 验证文件crc32
     *
     * @param path  文件路径
     * @param crc32 crc32
     * @return boolean 返回布尔值
     */
    public static boolean checkFileCrc32(String path, long crc32) {
        return getFileCrc32(path) == crc32;
    }

}
