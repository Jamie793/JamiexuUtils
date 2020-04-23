package com.jamiexu.Utils.FileUtils;

import com.jamiexu.Utils.ConvertUtils.ConvertUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;

public class FileUtils {

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
        return stringBuilder.toString();
    }


    public static void putString(String path, String content) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(path);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
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

            if (bufferedWriter != null) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void writeFile(String path, byte[] bytes) {
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(path));
            dataOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static byte[] readFile(String path) {
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(new FileInputStream(path));
            byte[] bytes = new byte[dataInputStream.available()];
            dataInputStream.read(bytes);
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static void copyFile(String from, String to) {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    }


    public static void removeDirectory(String path) {
        File[] files = new File(path).listFiles();
        for (File f : files) {
            if (f.isFile())
                f.delete();
            else {
                removeDirectory(f.toString());
                f.delete();
            }
        }
        new File(path).delete();
    }


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
            String hex = ConvertUtils.bytesToHex(messageDigest.digest());
            if (upper)
                return hex.toUpperCase();
            return hex;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


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
            String hex = ConvertUtils.bytesToHex(messageDigest.digest());
            if (upper)
                return hex.toUpperCase();
            return hex;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


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
            String hex = ConvertUtils.bytesToHex(messageDigest.digest());
            if (upper)
                return hex.toUpperCase();
            return hex;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Long getFileCrc32(String path) {
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
        return null;
    }


    public static String getFileBase64(String path) {
        return Base64.getEncoder().encodeToString(readFile(path));
    }


    public static long getFileSize(String path) {
        return new File(path).length();
    }

    public static String getFileName(String path) {
        return new File(path).getName();
    }

    public static String getFileParentPath(String path) {
        return new File(path).getParent();
    }

    public static void moveFile(String from, String to) {
        new File(from).renameTo(new File(to));
    }

    public static void renameFile(String path, String name) {
        File file = new File(path);
        file.renameTo(new File(file.getParent() + "/" + name));
    }


}
