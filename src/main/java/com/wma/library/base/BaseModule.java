package com.wma.library.base;

import androidx.databinding.BaseObservable;

import com.wma.library.utils.http.HttpUtils;

import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseModule extends BaseObservable {
    public static final String HOST = "http://192.168.0.62:8080";

    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }


    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HttpUtils mHttpUtils;

    public Callback.Cancelable mCancelable;

    public BaseModule() {
        mHttpUtils = new HttpUtils();
    }

    public void cancelLoad() {
        if (mCancelable != null) {
            mCancelable.cancel();
        }
    }
}
