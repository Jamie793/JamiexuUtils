package com.jamiexu.utils.convert;

public class ByteUtils {
    public static byte[] copyBytes(byte[] bytes,int offset,int len){
        byte[] byt = new byte[len];
        System.arraycopy(bytes,offset,byt,0,len);
        return byt;
    }
}
