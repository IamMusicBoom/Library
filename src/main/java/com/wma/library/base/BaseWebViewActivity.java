package com.wma.library.base;

import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;

import com.wma.library.R;
import com.wma.library.databinding.ActivityBaseWebViewBinding;
import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public abstract class BaseWebViewActivity extends BaseActivity<ActivityBaseWebViewBinding> {
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";
    @Override
    public String getTitleStr() {
        String title = getIntent().getStringExtra("title");
        return title;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        WebSettings webSettings = mBinding.webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        String url = getIntent().getStringExtra(KEY_URL);
        Logger.d(TAG, "init: url = " + url);
        mBinding.webView.loadUrl(url);

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return true;
            }

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_base_web_view;
    }
}
