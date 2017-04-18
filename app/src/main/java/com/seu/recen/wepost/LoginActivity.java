package com.seu.recen.wepost;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.seu.recen.wepost.business.WeBoLogin;
import com.seu.recen.wepost.common.BaseActivity;
import com.seu.recen.wepost.common.constants.Constants;
import com.seu.recen.wepost.databinding.ActivityLoginBinding;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;

/**
 * Created by Recen on 2017/4/16.
 */

public class LoginActivity extends BaseActivity{
    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);

        // SSO 授权, 仅客户端
        findViewById(R.id.login_via_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSsoHandler.authorizeClientSso(new AuthListener());
            }
        });
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(final Bundle values) {
            // 从 Bundle 中解析 Token
            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAccessToken = Oauth2AccessToken.parseAccessToken(values);
                    //从这里获取用户输入的 电话号码信息
                    String  phoneNum =  mAccessToken.getPhoneNum();
                    if (mAccessToken.isSessionValid()) {
                        // 显示 Token
                        updateTokenView(false);

                        // 保存 Token 到 SharedPreferences
                        AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                        Toast.makeText(LoginActivity.this,
                                R.string.auth_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // 以下几种情况，您会收到 Code：
                        // 1. 当您未在平台上注册的应用程序的包名与签名时；
                        // 2. 当您注册的应用程序包名与签名不正确时；
                        // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                        String code = values.getString("code");
                        String message = getString(R.string.auth_fail);
                        if (!TextUtils.isEmpty(code)) {
                            message = message + "\nObtained the code: " + code;
                        }
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    R.string.auth_cancel, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                new java.util.Date(mAccessToken.getExpiresTime()));
//        String format = getString(R.string.token_to_string_format_1);
//        mTokenText.setText(String.format(format, mAccessToken.getToken(), date));
//
//        String message = String.format(format, mAccessToken.getToken(), date);
//        if (hasExisted) {
//            message = getString(R.string.weibosdk_demo_token_has_existed) + "\n" + message;
//        }
//        mTokenText.setText(message);
    }
}
