package com.wma.library.base;

import com.wma.library.utils.HttpUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseModule {

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
