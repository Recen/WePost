package com.seu.recen.networkmodule;

import java.io.InputStream;

/**
 * Created by Recen on 2017/4/16.
 */

public class Response {
    private int code;
    private InputStream stream;
    private long contentLength;
    private boolean cached;

    public Response(int code, InputStream stream, long contentLength) {
        this.code = code;
        this.stream = stream;
        this.contentLength = contentLength;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }
}
