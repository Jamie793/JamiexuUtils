package com.jamiexu;

import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileChannelUtils;
import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.file.ZipUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class Main {
    public static void main(String[] args) {
        byte[] bytes = "12546546456353".getBytes();
        byte[] pass = "1234567811111111".getBytes();
        byte[] en = EncryptionUtils.enAes(bytes,pass,1);
        System.out.println(ConvertUtils.bytesToHex(en,false));
        System.out.println(ConvertUtils.bytesToString(EncryptionUtils.enAes(en,pass,2)));
    }
}
