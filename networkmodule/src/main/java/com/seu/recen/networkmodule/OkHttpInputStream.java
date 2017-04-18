package com.seu.recen.networkmodule;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Recen on 2017/4/16.
 */

public class OkHttpInputStream extends WrapperInputStream {
    private ResponseBody responseBody;

    public OkHttpInputStream(InputStream inputStream, ResponseBody responseBody) {
        super(inputStream);
        this.responseBody = responseBody;
    }

    @Override
    public void close() throws IOException {
        super.close();
        responseBody.close();
    }
}
