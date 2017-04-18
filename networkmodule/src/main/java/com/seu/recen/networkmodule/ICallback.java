package com.seu.recen.networkmodule;

/**
 * Created by Recen on 2017/4/16.
 */

public interface ICallback {
    void onResponse(Request request, Response response);
    void onFailure(Request request, Exception e);
}
