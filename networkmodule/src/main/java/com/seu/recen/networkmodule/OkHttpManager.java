package com.seu.recen.networkmodule;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

/**
 * Created by Recen on 2017/4/16.
 */

public class OkHttpManager implements INetworkHandler{
    private OkHttpClient client;

    public OkHttpManager() {
        this.client = OkHttpClientInstance.getInstance();
    }

    @Override
    public Response synRequest(Request request) {
        try {
            com.squareup.okhttp.Request.Builder builder = createOkHttpBuilder(request);

            com.squareup.okhttp.Request okHttpRequest = builder.build();
            com.squareup.okhttp.Response response = client.newCall(okHttpRequest).execute();
            boolean fromCache = response.cacheResponse() != null;

            ResponseBody responseBody = response.body();
            Response result = new Response(response.code(),  new OkHttpInputStream(responseBody.byteStream(), responseBody),responseBody.contentLength());
            result.setCached(fromCache);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void asyncRequest(final Request request, final ICallback callback) {
        com.squareup.okhttp.Request.Builder builder = createOkHttpBuilder(request);
        com.squareup.okhttp.Request okHttpRequest = builder.build();
        client.newCall(okHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(com.squareup.okhttp.Request r, IOException e) {
                callback.onFailure(request,e);
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                ResponseBody responseBody = response.body();
                Response result = new Response(response.code(),new OkHttpInputStream(responseBody.byteStream(),responseBody),responseBody.contentLength());
            }
        });
    }

    @Override
    public void cancel(Object tag) {
        client.cancel(tag);
    }

    private com.squareup.okhttp.Request.Builder createOkHttpBuilder(Request request){
        com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder().url(request.getUrl());
        if(request.getTag() != null){
            builder.tag(request.getTag());
        }

        return builder;
    }
}
