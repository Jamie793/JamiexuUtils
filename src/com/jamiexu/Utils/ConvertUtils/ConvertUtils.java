package com.jamiexu.Utils.ConvertUtils;

public class ConvertUtils {
    public static byte[] hexToBytes(String hex){
        byte[] bytes = new byte[hex.length()/2];
        for(int i=0;i<bytes.length;i=i+2){
            bytes[i] = (byte) Integer.parseInt(hex.substring(i,i+1),16);
        }
        return bytes;
    }

    public static String bytesToHex(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : bytes){
            stringBuilder.append(String.format("%02x", b & 0xFF));
        }
        return stringBuilder.toString();
    }

    public static String bytesToString(byte[] bytes){
        return new String(bytes,0,bytes.length);
    }

}
