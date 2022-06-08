package com.wma.library.utils;

import android.os.Handler;
import android.os.Looper;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by WMA on 2021/10/29.
 */
public class ThreadUtils {
    private static final ThreadPoolExecutor mThreadPoolExecutor;
    private static final Handler sUIHandler;
    private static final Looper sLooper = Looper.getMainLooper();

    static {
        mThreadPoolExecutor = new ThreadPoolExecutor(5, 20, 5, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100));
        sUIHandler = new Handler();
    }


    public static void doInBackground(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() == sLooper.getThread()) {
            runnable.run();
        } else {
            sUIHandler.post(runnable);
        }
    }

    public static void runOnUiThread(Runnable runnable,long delay) {
        sUIHandler.postDelayed(runnable,delay);
    }

    public static void doInBackground(Runnable runnable, long delay) {
        sUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doInBackground(runnable);
            }
        },delay);
    }

    /**
     * 主线程空闲时执行
     */
    public static void postIdle(Runnable runnable) {
        Looper.myQueue().addIdleHandler(() -> {
            runnable.run();
            return false;
        });
    }


}
