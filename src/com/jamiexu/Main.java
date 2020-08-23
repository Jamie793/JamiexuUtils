package com.jamiexu;

//import com.jamiexu.utils.convert.ConvertUtils;

import com.jamiexu.utils.convert.ConvertUtils;
import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileChannelUtils;
import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.file.ZipUtils;
import com.jamiexu.utils.http.HttpResponse;
import com.jamiexu.utils.http.HttpUtils;
import com.jamiexu.utils.http.IHttpListener;
import com.jamiexu.utils.reflect.ReflectUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class Main {

    public static int post = 0x00000001;
    public static int download = 0x00000002;
    public static int get = 0x00000004;
    public static int upload = 0x00000008;

    public static void main(String[] args) {

    }


}
