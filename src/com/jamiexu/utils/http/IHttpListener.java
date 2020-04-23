package com.jamiexu.utils.http;

public interface IHttpListener {
    public void onComplete(HttpResponse httpResponse);
    public void onError(Exception error);
}
