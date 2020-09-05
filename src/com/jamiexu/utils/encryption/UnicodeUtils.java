package com.jamiexu.utils.encryption;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:32
 * @blog https://blog.jamiexu.cn
 **/
public class UnicodeUtils {


    /**
     * 编码成unicode
     *
     * @param content 需要编码的内容
     * @return String 解码后的内容
     */
    public static String enUnicode(String content) {
        char[] chars = content.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            stringBuilder.append("\\u").append(Integer.toString(chars[i], 16));
        }
        return stringBuilder.toString();
    }


    /**
     * 解码unicode
     *
     * @param unicode 需要解码的内容
     * @return String 解码后的内容
     */
    public static String deUnicode(String unicode) {
        String[] strs = unicode.split("\\\\u");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < strs.length; i++) {
            stringBuilder.append((char) Integer.valueOf(strs[i], 16).intValue());
        }
        return stringBuilder.toString();
    }

}
