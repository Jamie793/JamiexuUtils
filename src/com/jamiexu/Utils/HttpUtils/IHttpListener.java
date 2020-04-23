package com.jamiexu.Utils.HttpUtils;

public interface IHttpListener {
    public void onComplete(HttpResponse httpResponse);
    public void onError(Exception error);
}
