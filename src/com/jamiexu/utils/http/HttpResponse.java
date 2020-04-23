package com.jamiexu.utils.http;

import java.util.HashMap;

public class HttpResponse {
    private String body;
    private HashMap<String,String> hashMap_headers;
    private String cookie;
    private int code;

    public HttpResponse(String body, HashMap<String, String> hashMap_headers, String cookie, int code) {
        this.body = body;
        this.hashMap_headers = hashMap_headers;
        this.cookie = cookie;
        this.code = code;
    }

    public String getBody() {
        return body;
    }


    public HashMap<String, String> getHeaders() {
        return hashMap_headers;
    }

    public String getHeader(String key) {
        return this.hashMap_headers.get(key);
    }

    public String getCookie() {
        return cookie;
    }

    public int getCode() {
        return code;
    }
}
