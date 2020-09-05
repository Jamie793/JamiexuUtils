package com.jamiexu.utils.encryption;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Jamiexu/Jamie793
 * @version 1.0
 * @date 2020/9/5
 * @time 13:46
 * @blog https://blog.jamiexu.cn
 **/
public class URLUtils {

    /**
     * URL编码
     *
     * @param content 需要编码的内容
     * @return String
     */
    public static String encode(String content) {
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URL解码
     *
     * @param content 需要解码的内容
     * @return String
     */
    public static String decode(String content) {
        try {
            return URLDecoder.decode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
