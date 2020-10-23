package com.wma.library.utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class HttpUtils {


    public Callback.Cancelable get(String url, Map<String, String> parameters, Callback.CommonCallback callback) {
        RequestParams entity = new RequestParams(url);
        entity.setConnectTimeout(10000);
        for (String key : parameters.keySet()) {
            entity.addBodyParameter(key, parameters.get(key));
        }
        Callback.Cancelable cancelable = x.http().get(entity, callback);
        return cancelable;

    }

    public Callback.Cancelable post(String url, Map<String, String> parameters, Callback.CommonCallback callback) {
        RequestParams entity = new RequestParams(url);
        entity.setConnectTimeout(10000);
        for (String key : parameters.keySet()) {
            entity.addBodyParameter(key, parameters.get(key));
        }
        Callback.Cancelable cancelable = x.http().post(entity, callback);
        return cancelable;
    }
}
