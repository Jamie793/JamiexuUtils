package com.jamiexu.utils.file;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.CRC32;

/**
 * @author Jamiexu/Jamie793
 * @version 3.0
 * @date 2020/9/4
 * @time 19:11
 * @blog https://blog.jamiexu.cn
 **/


public class FileUtils {

    /**
     * 读取文本文件
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static String getString(String path) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
    }


    /**
     * 读取指定行间的文本
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static String getLinesString(String path, int line, int line2) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String str = null;
            int lines = 1;
            while ((str = bufferedReader.readLine()) != null) {
                if (lines >= line && lines <= line2)
                    stringBuilder.append(str).append("\n");
                lines++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
    }


    /**
     * 获取指定行的文本内容
     *
     * @param path 文件路径
     * @param line 行号
     * @return
     */
    public static String getLineString(String path, int line) {
        return getLinesString(path, line, line);
    }


    /**
     * 写出文本文件
     *
     * @param path    需要写出的文件路径
     * @param content 需要写出的文件内容
     * @return boolean 状态
     */
    public static boolean putString(String path, String content) {
        boolean status = false;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write(content);
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return status;
    }

    /**
     * 写出二进制文件
     *
     * @param path  需要写出文件的路径
     * @param bytes 需要写出的内容
     * @return boolean 状态
     */
    public static boolean writeFile(String path, byte[] bytes) {
        boolean status = false;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(bytes);
            if (new File(path).exists())
                status = true;
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
        }
        return status;
    }


    /**
     * 读取二进制文件
     *
     * @param path 文件地址
     * @return byte[] 文件内容
     */
    public static byte[] readFile(String path) {
        FileInputStream fileInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] byt = new byte[1024 * 8];
            int len;
            while ((len = fileInputStream.read(byt)) != -1) {
                byteArrayOutputStream.write(byt, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
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

            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 复制文件
     *
     * @param from 源文件路径
     * @param to   目标文件路径
     * @return boolean 状态
     */
    public static boolean copyFile(String from, String to) {
        boolean status = false;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(from);
            fileOutputStream = new FileOutputStream(to);
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            if (new File(to).exists())
                if (Objects.equals(FileUtils.getFileMd5(from, false), FileUtils.getFileMd5(to, false)))
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

            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    /**
     * 删除文件目录
     *
     * @param path 需要删除的文件目录路径
     * @return boolean
     */
    public static boolean removeDirectory(String path) {
        File file = new File(path);
        if (!file.exists() || file.isFile())
            return false;
        File[] files = file.listFiles();
        for (File f : Objects.requireNonNull(files)) {
            if (f.isFile()) {
                if (!f.delete())
                    return false;
            } else {
                removeDirectory(f.toString());
            }
        }
        return new File(path).delete();
    }

    /**
     * 获取文件MD5
     *
     * @param path  文件路径
     * @param upper 返回结果是否转换成大写
     * @return String 16进制文本
     */
    public static String getFileMd5(String path, boolean upper) {
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
        }
        return null;
    }


    /**
     * 获取文件MD5默认结果为小写
     *
     * @param path 文件路径
     * @return String 16进制文本
     */
    public static String getFileMd5(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
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
        return Base64.getEncoder().encodeToString(readFile(path));
    }

    /**
     * 获取文件大小
     *
     * @param path 文件路径
     * @return Long 文件大小以byte为单位
     */
    public static long getFileSize(String path) {
        return new File(path).length();
    }

    /**
     * 获取文件名称
     *
     * @param path 文件路径
     * @return String 文件名称
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }

    /**
     * 获取文件父目录
     *
     * @param path 文件路径
     * @return String 文件父目录路径
     */
    public static String getFileParentPath(String path) {
        return new File(path).getParent();
    }

    /**
     * 移动文件
     *
     * @param from 源文件
     * @param to   目标文件
     * @return boolean status
     */
    public static boolean moveFile(String from, String to) {
        return new File(from).renameTo(new File(to));
    }

    /**
     * 重命名文件
     *
     * @param path 文件路径
     * @param name 新文件名
     * @return boolean 状态
     */
    public static boolean renameFile(String path, String name) {
        File file = new File(path);
        return file.renameTo(new File(file.getParent() + "/" + name));
    }

    /**
     * 验证文件md5
     *
     * @param path 文件路径
     * @param md5  md5值大小写都行
     * @return boolean 返回布尔值
     */
    public static boolean checkFileMd5(String path, String md5) {
        return Objects.equals(getFileMd5(path, true), md5.toUpperCase());
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


}
