package com.wma.library.log;

import android.util.Log;

/**
 * create by wma
 * on 2020/4/21 0021
 */
public class Logger {
    private static final String TAG = "WMA-WMA";

    public static void d(String tag, String msg) {
        Log.d(TAG, tag + " : " + msg);
    }

    public static void v(String tag, String msg) {
        Log.v(TAG, tag + " : " + msg);
    }

    public static void w(String tag, String msg) {
        line(tag,true);
        Log.w(TAG, tag + " : " + msg);
        line(tag,false);
    }

    public static void i(String tag, String msg) {
        Log.i(TAG, tag + " : " + msg);
    }

    public static void e(String tag, String msg) {
        line(tag,true);
        Log.e(TAG, tag + " : " + msg);
        line(tag,false);
    }

    public static void line(String tag, boolean isTop) {
        if (isTop) {
            d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
