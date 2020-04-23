package com.jamiexu;

import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class Main {
    public static void main(String[] args) {
        byte[] con = "123".getBytes();
        byte[] pass = "123".getBytes();
        byte[] res = EncryptionUtils.enAes(con,pass,1);
        System.out.println(ConvertUtils.bytesToBase64(res));
    }
}
