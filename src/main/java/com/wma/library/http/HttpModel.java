package com.wma.library.http;

import com.wma.library.base.BaseModule;
import com.wma.library.utils.http.HttpUtils;

import org.xutils.common.Callback;

/**
 * Created by WMA on 2021/10/25.
 */
public class HttpModel extends BaseModule {

    public static final String HOST = "http://192.168.0.62:8080";

    private int error_code;
    private String reason;
    public HttpUtils mHttpUtils;
    public Callback.Cancelable mCancelable;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HttpModel() {
        mHttpUtils = new HttpUtils();
    }

    public void cancelLoad() {
        if (mCancelable != null) {
            mCancelable.cancel();
        }
    }
}
