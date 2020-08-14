package com.jamiexu;

//import com.jamiexu.utils.convert.ConvertUtils;

import com.jamiexu.utils.encryption.EncryptionUtils;
import com.jamiexu.utils.file.FileChannelUtils;
import com.jamiexu.utils.file.FileUtils;
import com.jamiexu.utils.file.ZipUtils;
import com.jamiexu.utils.http.HttpResponse;
import com.jamiexu.utils.http.HttpUtils;
import com.jamiexu.utils.http.IHttpListener;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
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

    public static int post = 0x00000001;
    public static int download = 0x00000002;
    public static int get = 0x00000004;
    public static int upload = 0x00000008;

    public static void main(String[] args) {
        new HttpUtils("http://127.0.0.1/test.php", "n=123", new IHttpListener() {
            @Override
            public void onComplete(HttpResponse httpResponse) {
                System.out.println(httpResponse.getBody());
            }

            @Override
            public void onError(Exception error) {
                System.out.println(error);
            }
        }).setTimeout(1000).setDownloadPath("C:\\Users\\Jamiexu\\Desktop\\2.png")
                .execute();


    }

    public static void deFile(String path) {
        File[] file = new File(path).listFiles();
        for (File f : file) {
            if (f.isDirectory()) {
                deFile(f.toString());
            } else if (f.getName().endsWith("aar")) {
                de(f.toString());
            }
        }
    }


    public static void de(String path) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            byte[] key = new byte[32];
            System.arraycopy(bytes, bytes.length - 32, key, 0, 32);
            byte[] data = new byte[bytes.length - 8 - 32];
            System.arraycopy(bytes, 8, data, 0, bytes.length - 8 - 32);
//            System.out.println(new String(data));
            data = en(data, key);
            System.out.println(new String(data, 0, data.length));
//            FileUtils.writeFile(path.replace("aar",""),data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static byte[] en(byte[] arrby, byte[] arrby2) {
        for (int i = 0; i < arrby.length; i++) {
            arrby[i] = (byte) (arrby[i] ^ 0xFF ^ arrby2[i % arrby2.length]);
        }
        return arrby;
    }
}
