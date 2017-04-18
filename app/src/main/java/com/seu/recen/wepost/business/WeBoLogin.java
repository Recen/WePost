package com.seu.recen.wepost.business;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.seu.recen.wepost.common.PageWrapper;
import com.seu.recen.wepost.common.constants.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * Created by Recen on 2017/4/16.
 */

public class WeBoLogin extends PageWrapper{
    private AuthInfo mAuthInfo;
    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    private void WeBoAuth(){

    }

    @Override
    protected void initData() {
        mAuthInfo = new AuthInfo(page.getActivity(), Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(page.getActivity(), mAuthInfo);
    }

}
