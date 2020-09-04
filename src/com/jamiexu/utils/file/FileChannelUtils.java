package com.jamiexu.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

/**
 @author Jamiexu/Jamie793
 @version 3.0
 @date 2020/9/4
 @time 19:11
 @blog https://blog.jamiexu.cn
 **/


public class FileChannelUtils {

    /**
     * 通过通道复制文件（非直接缓冲区）
     *
     * @param from 源文件
     * @param to   目标文件
     * @return boolean 状态
     */
    public static boolean copyFile(String from, String to) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel fileChannel = null;
        FileChannel fileChannel1 = null;
        try {
            fileInputStream = new FileInputStream(from);
            fileOutputStream = new FileOutputStream(to);
            fileChannel = fileInputStream.getChannel();
            fileChannel1 = fileOutputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 8);
            while (fileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel1.write(byteBuffer);
                byteBuffer.clear();
            }
            if (new File(to).exists())
                if (Objects.equals(FileUtils.getFileMd5(from, false), FileUtils.getFileMd5(to, false)))
                    status = true;

        } catch (Throwable e) {
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

            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileChannel1 != null) {
                try {
                    fileChannel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    /**
     * 通过通道复制文件（直接缓冲区）
     *
     * @param from 源文件
     * @param to   目标文件
     * @return boolean 状态
     */
    public static boolean transferFile(String from, String to) {
        boolean status = false;
        FileChannel fileChannel = null;
        FileChannel fileChannel1 = null;
        try {
            fileChannel = FileChannel.open(Paths.get(from), StandardOpenOption.READ);
            fileChannel1 = FileChannel.open(Paths.get(to), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            fileChannel1.transferFrom(fileChannel, 0, fileChannel.size());
            if (new File(to).exists())
                if (Objects.equals(FileUtils.getFileMd5(from, false), FileUtils.getFileMd5(to, false)))
                    status = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileChannel1 != null) {
                try {
                    fileChannel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;

    }


    /**
     * 通过通道内存映射复制文件
     *
     * @param from 源文件
     * @param to   目标文件
     * @return boolean 状态
     */
    public static boolean mapFile(String from, String to) {
        boolean status = false;
        FileChannel fileChannel = null;
        FileChannel fileChannel1 = null;
        try {
            fileChannel = FileChannel.open(Paths.get(from), StandardOpenOption.READ);
            fileChannel1 = FileChannel.open(Paths.get(to), StandardOpenOption.READ, StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE);
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
                    fileChannel.size());
            MappedByteBuffer mappedByteBuffer1 = fileChannel1.map(FileChannel.MapMode.READ_WRITE, 0,
                    fileChannel.size());
            byte[] bytes = new byte[mappedByteBuffer.limit()];
            mappedByteBuffer.get(bytes);
            mappedByteBuffer1.put(bytes);

            if (new File(to).exists())
                if (Objects.equals(FileUtils.getFileMd5(from, false), FileUtils.getFileMd5(to, false)))
                    status = true;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileChannel1 != null) {
                try {
                    fileChannel1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return status;
    }

}
