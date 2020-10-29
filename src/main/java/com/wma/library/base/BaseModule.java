package com.wma.library.base;

import com.wma.library.utils.HttpUtils;

import org.json.JSONObject;
import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseModule {



    public HttpUtils mHttpUtils;

    public Callback.Cancelable mCancelable;

    public BaseModule() {
        mHttpUtils = new HttpUtils();
    }

    public void cancelLoad(){
        if(mCancelable != null){
            mCancelable.cancel();
        }
    }
}
