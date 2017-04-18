package com.seu.recen.wepost.common;

import android.databinding.BaseObservable;

/**
 * Created by Recen on 2017/4/16.
 */

public abstract class PageWrapper extends BaseObservable{
    protected IPage page;

    public PageWrapper(){
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    public void finish(){
        page.finish();
    }

    public void init(IPage page) {
        this.page = page;
        initData();
    }

    public void refresh(){}

//    protected void enableEventBus() {
//        EventBus.getDefault().register(this);
//    }
//
//    protected void disableEventBus() {
//        EventBus.getDefault().unregister(this);
//    }
}
