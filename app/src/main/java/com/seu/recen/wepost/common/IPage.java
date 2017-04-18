package com.seu.recen.wepost.common;

import android.content.Context;

import java.util.Map;

/**
 * Created by Recen on 2017/4/16.
 */

public interface IPage {
    /**
     * 关闭当前页面
     */
    void finish();

    /**
     * 设置result后，回退后可回调onActivityResult传递数据
     *
     * @param result
     */
    void finish(Map<String, Object> result);

    /**
     * 页面跳转
     *
     * @param pageName
     */
    void go(String pageName);

//    void go(String pageName, Object params, ICallback callback);

    /**
     * 跳转到Web页面
     *
     * @param url
     */
    void goUrl(String url);

    void replaceFragment(String tag);

//    ICache getCache();
//
//    IChat getChatService();

//    void openSmsObserver(SmsHelper smsHelper);
//
//    void closeSmsObserver(SmsHelper smsHelper);

    Context getApplicationContext();

    BaseActivity getActivity();
}
