package com.jamiexu.utils.http;


import com.jamiexu.utils.file.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * @author Jamiexu or Jamie793
 * @version 3.0
 * blog；blog.jamiexu.cn
 */

public class HttpUtils {

    private final int REQUEST_GET = 0x00000001;

    private final int REQUEST_POST = 0x00000002;

    private final int REQUEST_UPLOAD = 0x00000004;

    private final int REQUEST_DOWNLOAD = 0x00000008;

    private final int REQUEST_RAW = 0x000000010;

    private final String REQUEST_LINE = "----979916206Jamiexu0Jamie7937401316330204";

    private int requestMode = REQUEST_GET;

    private String url;

    private byte[] data = null;

    private HashMap<String, String> hashMap_headers;

    private IHttpListener iHttpListener;

    private int timeout = 6 * 1000;

    private HttpURLConnection httpURLConnection;

    private String downloadPaht;

    private Proxy proxy;

    private String[] uploadFiles;

    private HashMap<String, String> datas;

    private String fileTageName = "file";

    private String stringDatas;


    /**
     * 请求默认为GET方式，没有回调
     *
     * @param url
     */
    public HttpUtils(String url) {
        this(url, null, null, null);
    }

    /**
     * 请求默认为GET方式，有回调
     *
     * @param url           url
     * @param iHttpListener IHttpListener回调接口
     */
    public HttpUtils(String url, IHttpListener iHttpListener) {
        this(url, null, null, iHttpListener);
    }

    /**
     * 请求默认为POST，没有回调
     *
     * @param url  url
     * @param data 数据
     */
    public HttpUtils(String url, String data) {
        this(url, data, null, null);
    }

    /**
     * 请求默认为POST，有回调
     *
     * @param url           url
     * @param data          数据
     * @param iHttpListener IHttpListener回调接口
     */
    public HttpUtils(String url, String data, IHttpListener iHttpListener) {
        this(url, data, null, iHttpListener);
    }


    /**
     * 请求默认为GET需设置协议头HashMap，没有回调
     *
     * @param url     url
     * @param headers HashMap协议头
     */
    public HttpUtils(String url, HashMap<String, String> headers) {
        this(url, null, headers, null);
    }

    /**
     * 请求默认为GET需设置协议头HashMap，有回调
     *
     * @param url           url
     * @param headers       HashMap协议头
     * @param iHttpListener IHttpListener回调接口
     */
    public HttpUtils(String url, HashMap<String, String> headers, IHttpListener iHttpListener) {
        this(url, null, headers, iHttpListener);
    }

    /**
     * 请求默认为POST需设置协议头HashMap，没有回调
     *
     * @param url     url
     * @param data    请求数据
     * @param headers 请求协议头
     */
    public HttpUtils(String url, String data, HashMap<String, String> headers) {
        this(url, data, headers, null);
    }

    /**
     * @param url             url
     * @param data            请求数据
     * @param hashMap_headers 请求协议头
     * @param iHttpListener   请求回调
     */
    public HttpUtils(String url, String data, HashMap<String, String> hashMap_headers, IHttpListener iHttpListener) {
        this.url = url;
        if (data != null) {
            this.data = data.getBytes();
            this.stringDatas = data;
            this.requestMode = REQUEST_POST;
        }

        if (hashMap_headers == null)
            this.hashMap_headers = new HashMap<>();
        else
            this.hashMap_headers = hashMap_headers;
        this.iHttpListener = iHttpListener;
    }

    /**
     * 设置请求Cookie
     *
     * @param cookie cookie
     * @return HtppUtils
     */
    public HttpUtils setCookie(String cookie) {
        this.hashMap_headers.put("Cookie", cookie);
        return this;
    }

    /**
     * 设置请求头集合
     *
     * @param hashMap_headers 协议头集合
     * @return HtppUtils
     */
    public HttpUtils setHeaders(HashMap<String, String> hashMap_headers) {
        this.hashMap_headers = hashMap_headers;
        return this;
    }

    /**
     * 设置请求头
     *
     * @param key   请求头名称
     * @param value 值
     * @return HtppUtils
     */
    public HttpUtils setHeader(String key, String value) {
        this.hashMap_headers.put(key, value);

        return this;
    }

    /**
     * 设置请求编码
     *
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
     * 设置协议头Referer
     *
     * @param referer referer
     * @return HttpUtils
     */
    public HttpUtils setReferer(String referer) {
        if (referer != null && referer != "") {
            hashMap_headers.put("Referer", referer);
        }
        return this;
    }

    /**
     * 设置协议头Origin
     *
     * @param origin origin
     * @return HttpUtils
     */
    public HttpUtils setOrigin(String origin) {
        if (origin != null && origin != "") {
            hashMap_headers.put("Origin", origin);
        }
        return this;
    }

    /**
     * 设置协议头ContentType
     *
     * @param contentType contentType
     * @return HttpUtils
     */
    public HttpUtils setContentType(String contentType) {
        if (contentType != null && contentType != "") {
            hashMap_headers.put("Content-Type", contentType);
        }
        return this;
    }

    /**
     * 设置协议头ContentLength
     *
     * @param contentLength contentLength
     * @return HttpUtils
     */
    public HttpUtils setContentLength(String contentLength) {
        if (contentLength != null && contentLength != "") {
            hashMap_headers.put("Content-Length", contentLength);
        }
        return this;
    }

    /**
     * 设置协议头AcceptEncoding
     *
     * @param acceptEncoding acceptEncoding
     * @return HttpUtils
     */
    public HttpUtils setAcceptEncoding(String acceptEncoding) {
        if (acceptEncoding != null && acceptEncoding != "") {
            hashMap_headers.put("Accept-Encoding", acceptEncoding);
        }
        return this;
    }

    /**
     * 设置协议头Accept
     *
     * @param aceept aceept
     * @return HttpUtils
     */
    public HttpUtils setAceept(String aceept) {
        if (aceept != null && aceept != "") {
            hashMap_headers.put("Accept", aceept);
        }
        return this;
    }


    /**
     * 设置是否重定向
     *
     * @param redirects true为重定向，false禁止重定向
     * @return HtppUtils
     */
    public HttpUtils setRedirect(Boolean redirects) {
        HttpURLConnection.setFollowRedirects(redirects);
        return this;
    }

    /**
     * 设置UserAgent请求头
     *
     * @param userAgent UA
     * @return HtppUtils
     */
    public HttpUtils setUserAgent(String userAgent) {
        this.hashMap_headers.put("User-Agent", userAgent);
        return this;
    }

    /**
     * 设置回调接口
     *
     * @param iHttpListener 回调接口
     * @return HtppUtils
     */
    public HttpUtils setHttpListener(IHttpListener iHttpListener) {
        this.iHttpListener = iHttpListener;
        return this;
    }

    /**
     * 设置超时事件
     *
     * @param timeout 单位ms
     * @return HtppUtils
     */
    public HttpUtils setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 设置请求二进制数据
     *
     * @param data 二进制数据byte
     * @return HtppUtils
     */
    public HttpUtils setRawData(byte[] data) {
        this.requestMode = REQUEST_POST | REQUEST_RAW;
        this.data = data;
        return this;
    }

    /**
     * 设置下载目录并自动转为下载模式
     *
     * @param path          设置了下载目录后将进行下载模式
     * @param requestMethod 设置下载的请求方式GET或POST
     * @return HtppUtils
     */
    public HttpUtils setDownloadPath(String path, String requestMethod) {
        if (this.data != null) {
            this.requestMode = this.requestMode | REQUEST_DOWNLOAD;
        } else if (requestMethod.toUpperCase().equals("GET")) {
            this.requestMode = REQUEST_GET | REQUEST_DOWNLOAD;
        } else if (requestMethod.toUpperCase().equals("POST")) {
            this.requestMode = REQUEST_POST | REQUEST_DOWNLOAD;
        }
        this.downloadPaht = path;
        return this;
    }

    /**
     * 设置下载目录并自动转为下载模式，默认下载请求方式为GET
     *
     * @param path
     * @return HttpUtils
     */
    public HttpUtils setDownloadPath(String path) {
        this.setDownloadPath(path, "GET");
        return this;
    }

    /**
     * 设置要上传的文件路径数组，设置后会自动转换为上传文件模式
     *
     * @param datas 上传时请求的数据
     * @param paths 文件路径数组
     * @return HttpUtils
     */
    public HttpUtils setUploadFiles(HashMap<String, String> datas, String... paths) {
        this.requestMode = REQUEST_POST | REQUEST_UPLOAD;
        this.datas = datas;
        this.uploadFiles = paths;
        return this;
    }


    /**
     * 设置要上传的文件路径数组，设置后会自动转换为上传文件模式
     *
     * @param fileTageName 文件标签名并不是文件名
     * @param paths        文件路径数组
     * @return HttpUtils
     * @
     */
    public HttpUtils setUploadTFiles(String fileTageName, HashMap<String, String> datas, String... paths) {
        this.fileTageName = fileTageName;
        this.requestMode = REQUEST_POST | REQUEST_UPLOAD;
        this.datas = datas;
        this.uploadFiles = paths;
        return this;
    }

    /**
     * 设置要上传的文件路径数组，设置后会自动转换为上传文件模式
     *
     * @param fileTageName 文件标签名并不是文件名
     * @param paths        文件路径数组
     * @return HttpUtils
     */
    public HttpUtils setUploadTFiles(String fileTageName, String... paths) {
        this.fileTageName = fileTageName;
        this.requestMode = this.requestMode | REQUEST_UPLOAD;
        this.uploadFiles = paths;
        return this;
    }


    /**
     * 设置要上传的文件路径数组，设置后会自动转换为上传文件模式
     *
     * @param paths 文件路径数组
     * @return HttpUtils
     */
    public HttpUtils setUploadFiles(String... paths) {
        this.requestMode = this.requestMode | REQUEST_UPLOAD;
        this.uploadFiles = paths;
        return this;
    }

    /**
     * 设置代理访问呢
     *
     * @param proxy 代理
     * @return
     */
    public HttpUtils setProxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }


    private void executeJ() {
        try {
            if (this.proxy == null)
                this.httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
            else
                this.httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection(this.proxy);
            this.httpURLConnection.setConnectTimeout(this.timeout);
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setDoOutput(true);
            if (this.requestMode == REQUEST_POST | this.requestMode == (REQUEST_RAW | REQUEST_POST)) {
                this.httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(this.data);
                outputStream.flush();
                outputStream.close();
            } else if (this.requestMode == (REQUEST_POST | REQUEST_UPLOAD)) {
                this.httpURLConnection.setRequestMethod("POST");
                this.httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + REQUEST_LINE);
                OutputStream outputStream = this.httpURLConnection.getOutputStream();
//                OutputStream outputStream = new FileOutputStream("C:\\Users\\Jamiexu\\Desktop\\12.txt");
                PrintWriter printWriter = new PrintWriter(outputStream, true);

                Matcher matcher = Pattern.compile("([^=])=([^&=]+)").matcher(this.stringDatas);
                while (matcher.find()) {
                    printWriter.println("--" + REQUEST_LINE);
                    printWriter.println("Content-Disposition: form-data; name=\"" + matcher.group(1) + "\"");
                    printWriter.println();
                    printWriter.println(matcher.group(2));
                }

                if (this.datas != null)
                    for (Map.Entry<String, String> m : this.datas.entrySet()) {
                        printWriter.println("--" + REQUEST_LINE);
                        printWriter.println("Content-Disposition: form-data; name=\"" + m.getKey() + "\"");
                        printWriter.println();
                        printWriter.println(m.getValue());
                    }

                for (String p : this.uploadFiles) {
                    printWriter.println("--" + REQUEST_LINE);
                    printWriter.println("Content-Disposition: form-data; name=\"" + this.fileTageName + "\"; filename=\"" + FileUtils.getFileName(p) + "\"");
                    printWriter.println();
                    outputStream.write(Objects.requireNonNull(FileUtils.readFile(p)));
                }
                printWriter.write("\r\n--" + REQUEST_LINE + "--");
                printWriter.flush();
                printWriter.close();
                outputStream.flush();
                outputStream.close();
            } else if (this.requestMode == (REQUEST_POST | REQUEST_DOWNLOAD)) {
                this.httpURLConnection.setRequestMethod("POST");
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(this.data);
                outputStream.flush();
                outputStream.close();
            } else if (this.requestMode == (REQUEST_GET | REQUEST_DOWNLOAD)) {
                this.httpURLConnection.setRequestMethod("GET");
            } else if (this.requestMode == REQUEST_GET) {
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
                int req = this.requestMode & ~REQUEST_POST;
                req = req & ~REQUEST_GET;
                if (req == REQUEST_DOWNLOAD) {
                    fileOutputStream = new FileOutputStream(this.downloadPaht);
                    int len = -1;
                    byte[] byt = new byte[8 * 1024];
                    while ((len = inputStream.read(byt, 0, byt.length)) != -1) {
                        fileOutputStream.write(byt, 0, len);
                    }
                    this.iHttpListener.onComplete(new HttpResponse(null, rHeader, rCookie, httpURLConnection.getResponseCode()));
                } else {
                    if (rHeader.containsKey("Content-Encoding") && rHeader.get("Content-Encoding").contains("gzip")) {
                        inputStream = new GZIPInputStream(inputStream);
                    }
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
    }

    /**
     * 开始执行可以设置是否异步请求
     *
     * @param async
     */
    public void execute(boolean async) {
        if (async) {
            new Thread(this::executeJ).start();
        } else {
            executeJ();
        }
    }

    /**
     * 默认采用异步方式进行请求
     */
    public void execute() {
        this.execute(true);
    }


}
