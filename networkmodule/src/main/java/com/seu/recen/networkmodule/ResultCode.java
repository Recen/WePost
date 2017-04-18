package com.seu.recen.networkmodule;

/**
 * Created by Recen on 2017/4/16.
 */

public enum ResultCode {
    Success(0, "成功"),//成功
    NoNetwork(1, "无网络"),//无网络
    NetworkException(2, "网络异常"),//网络请求失败
    RequestTimeout(3, "请求超时"),//请求超时
    ServerException(4, "服务器异常"),//服务器异常,返回不是接口规定的数据
    ServerResponseTimeout(5, "服务器响应超时"),//服务器响应超时
    DefaultException(6, "未知异常");//除上述异常以外的异常

    ResultCode(int ni, String msg){
        nativeInt = ni;
        this.msg = msg;
    }

    public final int nativeInt;
    public final String msg;

}
