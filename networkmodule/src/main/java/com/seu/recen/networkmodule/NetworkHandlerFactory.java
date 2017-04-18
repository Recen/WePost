package com.seu.recen.networkmodule;

/**
 * Created by Recen on 2017/4/16.
 */

public class NetworkHandlerFactory {
    public static INetworkHandler createHandler(){
        return new OkHttpManager();
    }
}

