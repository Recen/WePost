package com.seu.recen.networkmodule;

/**
 * Created by Recen on 2017/4/16.
 */

public class Request {
    private Object tag;
    private String url;

    public Request(Object tag, String url) {
        this.tag = tag;
        this.url = url;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
