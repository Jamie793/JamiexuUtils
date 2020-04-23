package com.jamiexu.utils.http;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jamiexu or Jamie793
 * @version 1.0
 * 博客 blog.jamiexu.cn
 */

public class HttpUtils {
    private String url;
    private byte[] data = null;
    private HashMap<String, String> hashMap_headers;
    private IHttpListener iHttpListener;
    private int timeout = 6 * 1000;
    private HttpURLConnection httpURLConnection;
    private String downloadPaht;

    /**
     *
     * @param url 请求的URL
     */
    public HttpUtils(String url) {
        this(url, null, null, null);
    }

    /**
     *
     * @param url 请求的URl
     * @param hashMap_headers 协议头
     */
    public HttpUtils(String url, HashMap<String, String> hashMap_headers) {
        this(url, null, hashMap_headers, null);
    }

    /**
     *
     * @param url 请求的URl
     * @param hashMap_headers 协议头
     * @param iHttpListener 回调接口
     */
    public HttpUtils(String url, HashMap<String, String> hashMap_headers, IHttpListener iHttpListener) {
        this(url, null, hashMap_headers, iHttpListener);
    }

    /**
     *
     * @param url 请求的URl
     * @param data post数据
     */
    public HttpUtils(String url, String data) {
        this(url, data, null, null);
    }

    /**
     *
     * @param url 请求的URl
     * @param data post数据
     * @param hashMap_headers 协议头
     */
    public HttpUtils(String url, String data, HashMap<String, String> hashMap_headers) {
        this(url, data, hashMap_headers, null);
    }

    /**
     *
     * @param url 请求的URl
     * @param data post数据
     * @param hashMap_headers 协议头
     * @param iHttpListener 回调接口
     */
    public HttpUtils(String url, String data, HashMap<String, String> hashMap_headers, IHttpListener iHttpListener) {
        this.url = url;
        if (data != null)
            this.data = data.getBytes();
        if (hashMap_headers == null)
            this.hashMap_headers = new HashMap<>();
        else
            this.hashMap_headers = hashMap_headers;
        this.iHttpListener = iHttpListener;
    }

    /**
     * 设置请求Cookie
     * @param cookie cookie
     * @return HtppUtils
     */
    public HttpUtils setCookie(String cookie) {
        this.hashMap_headers.put("Cookie", cookie);
        return this;
    }

    /**
     * 设置请求头集合
     * @param hashMap_headers 协议头集合
     * @return HtppUtils
     */
    public HttpUtils setHeaders(HashMap<String, String> hashMap_headers) {
        this.hashMap_headers = hashMap_headers;
        return this;
    }

    /**
     * 设置请求头
     * @param key 请求头名称
     * @param value 值
     * @return HtppUtils
     */
    public HttpUtils setHeader(String key, String value) {
        this.hashMap_headers.put(key, value);
        return this;
    }

    /**
     * 设置请求编码
     * @param value 编码，如utf-8，gbk
     * @return HtppUtils
     */
    public HttpUtils setEncoding(String value) {
        if (value != null && value != "") {
            hashMap_headers.put("Accept-Charset", value);
        } else {
            hashMap_headers.put("Accept-Charset", "UTF-8");
        }
        return this;
    }

    /**
     * 设置是否重定向
     * @param redirects true为重定向，false禁止重定向
     * @return HtppUtils
     */
    public HttpUtils setRedirect(Boolean redirects) {
        HttpURLConnection.setFollowRedirects(redirects);
        return this;
    }

    /**
     * 设置UserAgent请求头
     * @param userAgent UA
     * @return HtppUtils
     */
    public HttpUtils setUserAgent(String userAgent) {
        this.hashMap_headers.put("User-Agent", userAgent);
        return this;
    }

    /**
     * 设置回调接口
     * @param iHttpListener 回调接口
     * @return HtppUtils
     */
    public HttpUtils setHttpListener(IHttpListener iHttpListener) {
        this.iHttpListener = iHttpListener;
        return this;
    }

    /**
     * 设置超时事件
     * @param timeout 单位ms
     * @return HtppUtils
     */
    public HttpUtils setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 设置请求二进制数据
     * @param data 二进制数据byte
     * @return HtppUtils
     */
    public HttpUtils setRawData(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * 设置下载目录
     * @param path 设置了下载目录后将进行下载模式
     * @return HtppUtils
     */
    public HttpUtils setDownloadPath(String path) {
        this.downloadPaht = path;
        return this;
    }

    /**
     * 开始执行
     * 执行完毕后会调用回调接口
     */
    public void execute() {
        new Thread(() -> {
            try {
                this.httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
                this.httpURLConnection.setConnectTimeout(this.timeout);
                this.httpURLConnection.setDoInput(true);

                if (this.data != null) {
                    this.httpURLConnection.setDoOutput(true);
                    this.httpURLConnection.setRequestMethod("POST");
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(this.data);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    this.httpURLConnection.setRequestMethod("GET");
                }

                for (Map.Entry<String, String> m : this.hashMap_headers.entrySet()) {
                    this.httpURLConnection.setRequestProperty(m.getKey(), m.getValue());
                }

                this.httpURLConnection.connect();


                if (this.iHttpListener == null)
                    return;

                InputStream inputStream = null;
                String rCookie = "";
                FileOutputStream fileOutputStream = null;
                BufferedReader bufferedReader = null;
                HashMap<String, String> rHeader = new HashMap<>();
                try {
                    Map<String, List<String>> header = this.httpURLConnection.getHeaderFields();
                    if (header != null && !header.isEmpty()) {
                        List<String> cookie = header.get("Set-Cookie");
                        if (cookie != null && !cookie.isEmpty())
                            for (String string : cookie)
                                rCookie += string + ";";

                        for (Map.Entry<String, List<String>> mEntry : header.entrySet()) {
                            for (String ss : mEntry.getValue())
                                if (mEntry.getKey() != null)
                                    rHeader.put(mEntry.getKey(), ss + ";");
                                else
                                    rHeader.put("Status", ss + ";");

                        }
                    }


                    inputStream = this.httpURLConnection.getInputStream();
                    if (this.downloadPaht != null) {

                        fileOutputStream = new FileOutputStream(this.downloadPaht);
                        int len = -1;
                        byte[] byt = new byte[8 * 1024];
                        while ((len = inputStream.read(byt, 0, byt.length)) != -1) {
                            fileOutputStream.write(byt, 0, len);
                        }
                        this.iHttpListener.onComplete(new HttpResponse(null, rHeader, rCookie, httpURLConnection.getResponseCode()));

                    } else {

                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder stringBuilder = new StringBuilder();
                        String s = null;
                        while ((s = bufferedReader.readLine()) != null) {
                            stringBuilder.append(s).append("\n");
                        }
                        this.iHttpListener.onComplete(new HttpResponse(stringBuilder.toString(), rHeader, rCookie, httpURLConnection.getResponseCode()));

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    this.iHttpListener.onError(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }

                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }

            } catch (IOException e) {
                if (this.iHttpListener != null)
                    this.iHttpListener.onError(e);
            }
        }).start();

    }


}
