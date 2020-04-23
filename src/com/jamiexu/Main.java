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
        System.out.println(EncryptionUtils.deUnicode(EncryptionUtils.enUnicode("我是靓仔")));
    }
}
