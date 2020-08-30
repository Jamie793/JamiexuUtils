package com.jamiexu.utils.android.dex;

import com.jamiexu.utils.convert.ByteUtils;
import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DexUtils {
    /**
     * 验证Dex CheckSum值
     *
     * @param src dexBytes
     * @return boolean
     */
    public static boolean verifyCheckSum(byte[] src) {
        byte[] bytes = ConvertUtils.longToBytes(EncryptionUtils.getAlder32(ByteUtils.copyBytes(src, 12, src.length - 12)));
        bytes = ByteUtils.copyBytes(bytes, 4, 4);
        String checksumHex = ConvertUtils.bytesToHex(bytes).toUpperCase();
        byte[] checkSum = new byte[4];
        System.arraycopy(src, 8, checkSum, 0, 4);
        String checksumHex2 = ConvertUtils.bytes2Hex(checkSum).toUpperCase();
        return checksumHex2.equals(checksumHex);
    }


    /**
     * 验证Dex Signature值
     *
     * @param src dexBytes
     * @return boolean
     */
    public static boolean verifySignature(byte[] src) {
        byte[] bytes = ByteUtils.copyBytes(src, 32, src.length - 32);
        bytes = EncryptionUtils.getSha1(bytes);
        String hex = ConvertUtils.bytesToHex(bytes).toUpperCase();
        byte[] signature = new byte[20];
        System.arraycopy(src, 12, signature, 0, 20);
        String hex2 = ConvertUtils.bytesToHex(signature).toUpperCase();
        return hex.equals(hex2);
    }


    /**
     * 重新计算 CheckSum值
     *
     * @param src dexBytes
     */
    public static void calcCheckSum(byte[] src) {
        byte[] bytes = ConvertUtils.longToBytes(EncryptionUtils.getAlder32(ByteUtils.copyBytes(src, 12, src.length - 12)));
        bytes = ByteUtils.copyBytes(bytes, 4, 4);
        byte[] newBytes = new byte[4];
        for (int i = bytes.length - 1; i >= 0; i--) {
            newBytes[4 - i - 1] = bytes[i];
        }
        System.arraycopy(newBytes, 0, src, 8, 4);
    }


    /**
     * 重新计算 Signature值
     *
     * @param src dexBytes
     */
    public static void calcSignature(byte[] src) {
        byte[] bytes = ByteUtils.copyBytes(src, 32, src.length - 32);
        bytes = EncryptionUtils.getSha1(bytes);
        byte[] newBytes = new byte[20];
        for (int i = bytes.length - 1; i >= 0; i--) {
            newBytes[20 - i - 1] = bytes[i];
        }
        System.arraycopy(bytes, 0, src, 12, 20);
    }


    /**
     * 重新计算Checksum和Signature并写出文件
     *
     * @param path 路径
     */
    public static void writeDex(String path, byte[] bytes) {
        calcSignature(bytes);
        calcCheckSum(bytes);
        FileUtils.writeFile(path, bytes);
    }


    /**
     * 获取对应偏移的字符串数据
     *
     * @param bytes  dexBytes
     * @param offset 偏移
     * @return byte[] 字符串字节数组
     */
    public static byte[] getStringBytes(byte[] bytes, int offset) {
        ArrayList<Byte> arrayList = new ArrayList<>();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[offset + i];
            if (b == 0) {
                break;
            }
            arrayList.add(b);
        }
        bytes = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bytes[i] = arrayList.get(i);
        }
        return bytes;
    }




}
