package com.seu.recen.networkmodule;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonSyntaxException;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.UUID;

/**
 * Created by Recen on 2017/4/16.
 */

public class HttpUtils {
    public HttpUtils() {
    }

    private static final String TAG = "HttpUtils";
    private static INetworkHandler mNetworkHandler;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void cancel(String tag) {
        if(mNetworkHandler != null){
            mNetworkHandler.cancel(tag);
        }
    }

    public static void get(final Object httpTag, final String url, final StringCallback callback){
        Log.d(TAG, "Do GET --> " + url);

        //检查请求url策略匹配,添加至策略任务
        final String urlKey = url;

        mNetworkHandler = NetworkHandlerFactory.createHandler();
        mNetworkHandler.asyncRequest(new Request(httpTag.hashCode(), url), new ICallback() {
            @Override
            public void onResponse(Request request, Response response) {
                try {
                    final String responseString = convertUtf8Byte(response.getStream(), (int) response.getContentLength());
                    Log.v(TAG, "[" + url + "] response:" + responseString);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(responseString);
                        }
                    });
                } catch (final Throwable e) {
                    e.printStackTrace();
                    handleErrorWithCache(urlKey, handler, request, e, callback, response.getCode());
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                handleErrorWithCache(urlKey, handler, request, e, callback);
            }
        });
    }

    public static String get(final String url, final StringCallback callback) {
        //cancel tag with same url
//        cancel(url);  // Deleted by waynezhang on 5/9/16: 删除取消相同url的request的代码, 转由业务层控制
        UUID tag = UUID.randomUUID();
        Log.d(TAG, "Do GET --> " + url);

        //检查请求url策略匹配,添加至策略任务
        final String urlKey = url;

            mNetworkHandler = NetworkHandlerFactory.createHandler();
            mNetworkHandler.asyncRequest(new Request(tag.toString(), url), new ICallback() {
                @Override
                public void onResponse(Request request, Response response) {
                    try {
                        final String responseString = convertUtf8Byte(response.getStream(), (int)response.getContentLength());
                        Log.v(TAG, "[" + url + "] response:" + responseString);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResponse(responseString);
                            }
                        });
                    } catch (final Throwable e) {
                        handleErrorWithCache(urlKey, handler, request, e, callback, response.getCode());
                    }
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    handleErrorWithCache(urlKey, handler, request, e, callback);
                }
            });

        return tag.toString();
    }

    private static String convertUtf8Byte(InputStream inputStream, int length){
        ByteArrayOutputStream outputStream;
        if(length > 0){
            outputStream = new ByteArrayOutputStream(length);
        } else {
            outputStream = new ByteArrayOutputStream();
        }

        try {
            int read;
            byte[] buffer = new byte[4*1024];

            while((read = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0 , read);
            }
            return new String(outputStream.toByteArray(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(null != inputStream){
                    inputStream.close();
                }
                outputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    private static void handleErrorWithCache(final String url, final Handler handler, final Request request, final Throwable th, final OnErrorCallback callback) {
        handleErrorWithCache(url, handler, request, th, callback, 0);
    }

    private static void handleErrorWithCache(final String url, final Handler handler, final Request request, final Throwable th, final OnErrorCallback callback, int httpResponseCode) {
        final ResultCode resultCode = getErrorCode(th);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(request, resultCode, (Exception) th);
                }
            });

    }

    private static ResultCode getErrorCode(final Throwable th) {
        assert (th != null);

        if (th instanceof ConnectTimeoutException) {
            return ResultCode.RequestTimeout;
        }

        if (th instanceof SocketTimeoutException) {
            return ResultCode.ServerResponseTimeout;
        }

        if (th instanceof IOException) {
            return ResultCode.NetworkException;
        }

        if (th instanceof JsonSyntaxException) {
            return ResultCode.ServerException;
        }

        return ResultCode.DefaultException;
    }

    public static interface OnErrorCallback {
        public void onFailure(Request request, ResultCode code, Exception e);
    }

    public static interface StringCallback extends OnErrorCallback {
        public void onResponse(String result);
    }
}
