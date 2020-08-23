package com.jamiexu.utils.file;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

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
     * @return boolean 是否压缩成功
     */
    public static boolean zip(String path, String to, boolean is) {
        ZipOutputStream zipOutputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(to);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            if (is)
                zip(new File(path), zipOutputStream, new File(path).getName() + "/");
            else
                zip(new File(path), zipOutputStream, "");
            return true;
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
        return false;
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
     * @return boolean 是否解压成功
     */
    public static boolean unZip(String zipPath, String desPath) {
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
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 提取压缩包的某个文件
     *
     * @param zipPath     zip路径
     * @param desPath     解压文件路径
     * @param zipFilePath zip文件中的路径 目录和文件之间用/
     * @return boolean 是否提取成功
     */
    public static boolean extraceFile(String zipPath, String desPath, String zipFilePath) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;
        if (zipFilePath.startsWith("/") || zipFilePath.startsWith("\\"))
            zipFilePath = zipFilePath.substring(1);
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
            return true;
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
        return false;
    }


    /**
     * 提取压缩包的某个文件返回byte[]
     *
     * @param zipPath     zip路径
     * @param zipFilePath zip文件中的路径 目录和文件之间用/
     * @return byte[] 文件byte[]数组
     */
    public static boolean extraceFileBytes(String zipPath, String zipFilePath) {
        ZipInputStream zipInputStream = null;
        ByteOutputStream byteOutputStream = null;
        if (zipFilePath.startsWith("/") || zipFilePath.startsWith("\\"))
            zipFilePath = zipFilePath.substring(1);
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().replace("//", "/").equals(zipFilePath)) {
                    byteOutputStream = new ByteOutputStream();
                    byte[] bytes = new byte[1024 * 8];
                    int len;
                    while ((len = zipInputStream.read(bytes, 0, bytes.length)) != -1) {
                        byteOutputStream.write(bytes, 0, bytes.length);
                    }
                    break;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteOutputStream != null) {
                try {
                    byteOutputStream.flush();
                    byteOutputStream.close();
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
        return false;
    }


    /**
     * 提取压缩包的某个文文件夹，不会创建目录
     *
     * @param zipPath    zip路径
     * @param desPath    解压路径
     * @param zipDirPath zip文件中的目录路径 目录和文件之间用/
     * @return boolean 是否提取成功
     */
    public static boolean extraceDir(String zipPath, String desPath, String zipDirPath) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;

        if (zipDirPath.startsWith("/") || zipDirPath.startsWith("\\"))
            zipDirPath = zipDirPath.substring(1);

        File file = new File(desPath);
        if (!file.exists()) file.mkdirs();
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.getName().replace("//", "/").contains(zipDirPath)) {
//                    System.out.println(zipEntry.getName());
                    String name = zipEntry.getName();
                    name = name.substring(name.lastIndexOf("/"));
//                    System.out.println(name);
                    File file1 = new File(desPath, name);
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
            return true;
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
        return false;
    }


    /**
     * 提取压缩包的某个文文件夹，会自动创建目录
     *
     * @param zipPath    zip路径
     * @param desPath    解压路径
     * @param zipDirPath zip文件中的目录路径 目录和文件之间用/
     * @return boolean 是否提取成功
     */
    public static boolean extraceDirs(String zipPath, String desPath, String zipDirPath) {
        ZipInputStream zipInputStream = null;
        FileOutputStream fileOutputStream = null;

        if (zipDirPath.startsWith("/") || zipDirPath.startsWith("\\"))
            zipDirPath = zipDirPath.substring(1);

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
            return true;
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
        return false;
    }


}
