package com.jamiexu;

import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileChannelUtils;
import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.file.ZipUtils;
import com.jamiexu.utils.http.HttpResponse;
import com.jamiexu.utils.http.HttpUtils;
import com.jamiexu.utils.http.IHttpListener;

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
        HttpUtils httpUtils = new HttpUtils("https://www.baidu.com");
        httpUtils.setHttpListener(new IHttpListener() {
            @Override
            public void onComplete(HttpResponse httpResponse) {
                System.out.println();
            }

            @Override
            public void onError(Exception error) {

            }
        });
        httpUtils.execute();;
    }
}
