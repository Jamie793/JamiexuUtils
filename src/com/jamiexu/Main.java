package com.jamiexu;

import com.jamiexu.utils.file.FileChannelUtils;
import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.file.ZipUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class Main {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
//        FileUtils.copyFile("E:\\BaiduNetdiskDownload\\AE cc2018 64位.zip","C:\\Users\\1\\desktop\\1..zip"); 4450
//        FileChannelUtils.copyFile("E:\\BaiduNetdiskDownload\\AE cc2018 64位.zip","C:\\Users\\1\\desktop\\1..zip"); 4406 4893   6412
//        FileChannelUtils.mapFile("E:\\BaiduNetdiskDownload\\AE cc2018 64位.zip","C:\\Users\\1\\desktop\\1..zip"); 2970   3204
//        FileChannelUtils.transferFile("E:\\BaiduNetdiskDownload\\AE cc2018 64位.zip","C:\\Users\\1\\desktop\\1..zip"); 1747 1685     2056
        ZipUtils.extraceFileDir("C:\\Users\\1\\Desktop\\a...zip","C:\\Users\\1\\Desktop\\a","2/新建文件夹/新建文件夹/新建文件夹/神隐桩 - 副本 - 副本 (3)/神隐桩 - 副本/神隐桩 - 副本/神隐桩 - 副本/7地点.jpg");
        System.out.println(System.currentTimeMillis()-t);
    }
}
