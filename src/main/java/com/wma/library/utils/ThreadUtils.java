package com.wma.library.utils;

import android.os.Handler;
import android.os.Looper;


import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by WMA on 2021/10/29.
 */
public class ThreadUtils {
    private static final ThreadPoolExecutor mThreadPoolExecutor;
    private static final Handler mUIHandler;
    static {
        mThreadPoolExecutor = new ThreadPoolExecutor(5, 20, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100));
        mUIHandler = new Handler(Looper.getMainLooper());
    }


    public static void doInBackground(Runnable runnable){
            mThreadPoolExecutor.execute(runnable);
    }

    public static void runOnUiThread(Runnable runnable){
        mUIHandler.post(runnable);
    }

    public static void doInBackground(){

    }



}
