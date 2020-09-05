package com.jamiexu;


import com.jamiexu.utils.android.dex.DexParser;
import com.jamiexu.utils.android.dex.throwable.DexStringParseException;
import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.AesUtils;
import com.jamiexu.utils.encryption.DesUtils;
import com.jamiexu.utils.encryption.RC4Utils;
import com.jamiexu.utils.encryption.RsaUtils;
import com.jamiexu.utils.file.FileUtils;
import sun.reflect.misc.FieldUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


/**
 * @author Jamiexu/Jamie793
 * @version 3.0
 * @date 2020/9/1
 * @time 16:30
 * @blog https://blog.jamiexu.cn
 **/


public class Main {

    public static int post = 0x00000001;
    public static int download = 0x00000002;
    public static int get = 0x00000004;
    public static int upload = 0x00000008;


    public static void a(String a, int b, char c, boolean d, Boolean e, CharSequence f) {


    }


    public static void main(String[] args) {
//        try {
//            DexParser dexParser = new DexParser("c:\\users\\jamiexu\\desktop\\classes.dex");
//            dexParser.parse();
//        } catch (DexStringParseException e) {
//            e.printStackTrace();
//        }
        byte[] key = "11111122".getBytes();
        DesUtils.encryptFile("c:\\users\\jamiexu\\desktop\\a.txt","c:\\users\\jamiexu\\desktop\\b.txt",key);
        DesUtils.decryptFile("c:\\users\\jamiexu\\desktop\\b.txt","c:\\users\\jamiexu\\desktop\\bb.txt",key);

    }

}
