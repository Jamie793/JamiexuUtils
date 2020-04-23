package com.jamiexu.utils.http;

public interface IHttpListener {
    /**
     * Http成功回调接口
     *
     * @param httpResponse httpResponse
     */
    public void onComplete(HttpResponse httpResponse);

    /**
     * Http失败回调接口
     *
     * @param error 返回错误
     */
    public void onError(Exception error);
}
