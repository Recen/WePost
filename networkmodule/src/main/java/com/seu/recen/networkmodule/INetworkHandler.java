package com.seu.recen.networkmodule;

/**
 * Created by Recen on 2017/4/16.
 */

public interface INetworkHandler {
    Response synRequest(Request request);
    void asyncRequest(Request request, ICallback callback);
    void cancel(Object tag);
}
