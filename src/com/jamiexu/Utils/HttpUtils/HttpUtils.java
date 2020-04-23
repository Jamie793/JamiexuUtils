package com.jamiexu.Utils.HttpUtils;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    private String url;
    private byte[] data = null;
    private HashMap<String, String> hashMap_headers;
    private IHttpListener iHttpListener;
    private int timeout = 6 * 1000;
    private HttpURLConnection httpURLConnection;
    private String downloadPaht;

    public HttpUtils(String url) {
        this(url, null, null, null);
    }

    public HttpUtils(String url, HashMap<String, String> hashMap_headers) {
        this(url, null, hashMap_headers, null);
    }


    public HttpUtils(String url, HashMap<String, String> hashMap_headers, IHttpListener iHttpListener) {
        this(url, null, hashMap_headers, iHttpListener);
    }


    public HttpUtils(String url, String data) {
        this(url, data, null, null);
    }

    public HttpUtils(String url, String data, HashMap<String, String> hashMap_headers) {
        this(url, data, hashMap_headers, null);
    }


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

    public HttpUtils setCookie(String cookie) {
        this.hashMap_headers.put("Cookie", cookie);
        return this;
    }

    public HttpUtils setHeaders(HashMap<String, String> hashMap_headers) {
        this.hashMap_headers = hashMap_headers;
        return this;
    }

    public HttpUtils setHeader(String key, String value) {
        this.hashMap_headers.put(key, value);
        return this;
    }

    public HttpUtils setEncoding(String value) {
        if (value != null && value != "") {
            hashMap_headers.put("Accept-Charset", value);
        } else {
            hashMap_headers.put("Accept-Charset", "UTF-8");
        }
        return this;
    }

    public HttpUtils setRedirect(Boolean redirects) {
        HttpURLConnection.setFollowRedirects(redirects);
        return this;
    }

    public HttpUtils setUserAgent(String userAgent) {
        this.hashMap_headers.put("User-Agent", userAgent);
        return this;
    }

    public HttpUtils setHttpListener(IHttpListener iHttpListener) {
        this.iHttpListener = iHttpListener;
        return this;
    }

    public HttpUtils setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpUtils setRawData(byte[] data) {
        this.data = data;
        return this;
    }

    public HttpUtils setDownloadPath(String path){
        this.downloadPaht = path;
        return this;
    }


    protected Object doInBackground(Object... strings) {
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
                return null;

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
                if(this.downloadPaht != null){

                    fileOutputStream = new FileOutputStream(this.downloadPaht);
                    int len = -1;
                    byte[] byt = new byte[8 * 1024];
                    while ((len = inputStream.read(byt, 0, byt.length)) != -1) {
                        fileOutputStream.write(byt, 0, len);
                    }
                    return new HttpResponse(null, rHeader, rCookie, httpURLConnection.getResponseCode());

                }else{

                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String s = null;
                    while ((s = bufferedReader.readLine())!=null){
                        stringBuilder.append(s).append("\n");
                    }
                    return new HttpResponse(stringBuilder.toString(), rHeader, rCookie, httpURLConnection.getResponseCode());

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

                if(fileOutputStream!=null){
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

                if(bufferedReader!=null){
                    bufferedReader.close();
                }
            }

        } catch (IOException e) {
            if (this.iHttpListener != null)
                this.iHttpListener.onError(e);
            return null;
        }
        return null;
    }



    protected void onPostExecute(Object s) {
        if (s == null)
            return;

        if (this.iHttpListener != null)
            this.iHttpListener.onComplete((HttpResponse) s);


    }
}
