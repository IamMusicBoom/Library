package com.wma.library.utils.http;

/**
 * create by wma
 * on 2020/12/8 0008
 */
public interface HttpProgressListener extends HttpCallbackListener {
    public void onStarted();
    public void onWaiting();
    public void onLoading(long total, long current, boolean isDownloading);

}
