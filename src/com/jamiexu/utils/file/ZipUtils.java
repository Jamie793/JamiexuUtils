package com.jamiexu.utils.file;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */
public class ZipUtils {

    /**
     * 压缩文件或文件夹
     *
     * @param path 文件或文件夹路径
     * @param to   导出zip的路径
     * @param is   是否把当前目录一起压缩进压缩包里
     */
    public static void zip(String path, String to, boolean is) {
        ZipOutputStream zipOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(to);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            if (is)
                zip(new File(path), zipOutputStream, new File(path).getName() + "/");
            else
                zip(new File(path), zipOutputStream, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.flush();
                    zipOutputStream.close();
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
        }
    }


    private static void zip(File file, ZipOutputStream zipOutputStream, String basePath) {
        try {
            if (file.isFile()) {
                zipOutputStream.putNextEntry(new ZipEntry(basePath + "/" + file.getName()));
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 8];
                int len;
                while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                    zipOutputStream.write(bytes, 0, bytes.length);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
                return;
            }
            File[] files = file.listFiles();
            if (files == null)
                return;
            for (File f : files) {
                if (f.isFile()) {
                    zipOutputStream.putNextEntry(new ZipEntry(basePath + "/" + f.getName()));
                    FileInputStream fileInputStream = new FileInputStream(f);
                    byte[] bytes = new byte[1024 * 8];
                    int len;
                    while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                        zipOutputStream.write(bytes, 0, bytes.length);
                    }
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                } else {
                    File[] files1 = f.listFiles();
                    if (files1 == null)
                        return;
                    if (files1.length == 0) {
                        zipOutputStream.putNextEntry(new ZipEntry(basePath + f.getName() + "/"));
                    } else {
                        zip(f, zipOutputStream, basePath + f.getName() + "/");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压文件
     *
     * @param zipPath zip路径
     * @param desPath 解压路径
     */
    public static void unZip(String zipPath, String desPath) {
        ZipInputStream zipInputStream = null;
        File f = new File(desPath);
        f.delete();
        f.mkdir();
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    new File(desPath, zipEntry.getName()).mkdir();
                } else {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(desPath, zipEntry.getName()));
                    byte[] bytes = new byte[1024 * 8];
                    int len;
                    while ((len = zipInputStream.read(bytes, 0, bytes.length)) != -1) {
                        fileOutputStream.write(bytes, 0, bytes.length);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提取压缩包的某个文件
     *
     * @param zipPath     zip路径
     * @param desPath     解压路径
     * @param zipFilePath zip文件中的路径 目录和文件之间用/
     */
    public static void extraceFile(String zipPath, String desPath, String zipFilePath) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().replace("//", "/").equals(zipFilePath)) {
                    fileOutputStream = new FileOutputStream(desPath);
                    byte[] bytes = new byte[1024 * 8];
                    int len;
                    while ((len = zipInputStream.read(bytes, 0, bytes.length)) != -1) {
                        fileOutputStream.write(bytes, 0, bytes.length);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (zipInputStream != null) {
                try {
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 提取压缩包的某个文文件夹或文件
     *
     * @param zipPath    zip路径
     * @param desPath    解压路径
     * @param zipDirPath zip文件中的目录路径 目录和文件之间用/
     */
    public static void extraceFileDir(String zipPath, String desPath, String zipDirPath) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = new File(desPath);
        if (!file.exists()) file.mkdirs();
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().replace("//", "/").contains(zipDirPath)) {
                    File file1 = new File(desPath, zipEntry.getName());
                    if (zipEntry.isDirectory()) {
                        file1.mkdirs();
                    } else {
                        if (!file1.getParentFile().exists()) file1.getParentFile().mkdirs();
                        fileOutputStream = new FileOutputStream(file1);
                        byte[] bytes = new byte[1024 * 8];
                        int len;
                        while ((len = zipInputStream.read(bytes, 0, bytes.length)) != -1) {
                            fileOutputStream.write(bytes, 0, len);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (zipInputStream != null) {
                try {
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
