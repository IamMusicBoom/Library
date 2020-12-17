package com.wma.library.utils.http;

import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/12/7 0007
 */
public interface HttpCallbackListener {

    public void onSuccess(String result, Request request);

    public void onError(Throwable ex, boolean isOnCallback);

    public void onCancelled(Callback.CancelledException cex);

    public void onFinished();
}
