package com.wma.library.log;

import android.util.Log;

/**
 * create by wma
 * on 2020/4/21 0021
 */
public class Logger {
    private static final String GLOBAL_TAG = "WMA-WMA";
    private static String mLineNumber, mClassName, mFileName, mMethodName;

    public static void d(String tag, String msg) {
        Log.d(buildTag(), tag + " : " + msg);
    }

    public static void d(String msg) {
        Log.d(buildTag(), msg);
    }

    public static void v(String tag, String msg) {
        Log.v(buildTag(), tag + " : " + msg);
    }

    public static void w(String tag, String msg) {
        Log.w(buildTag(), tag + " : " + msg);
    }

    public static void i(String tag, String msg) {
        Log.i(buildTag(), tag + " : " + msg);
    }

    public static void e(String tag, String msg) {
        Log.e(buildTag(), tag + " : " + msg);
    }

    public static void line(String tag, boolean isTop) {
        if (isTop) {
            d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    private static String buildTag() {
        // StackTraceElement[] sElements = new Throwable().getStackTrace();
        StackTraceElement[] sElements = Thread.currentThread().getStackTrace();
        if (sElements.length >= 4) {
            // 5 is the external function level
            mLineNumber = String.valueOf(sElements[4].getLineNumber());
            mClassName = sElements[4].getClassName();
            mFileName = sElements[4].getFileName();
            mMethodName = sElements[4].getMethodName();
            return GLOBAL_TAG + " [" + mClassName + ":" + mMethodName + ":" + mLineNumber + "]";
        } else {
            return GLOBAL_TAG;
        }

    }
}
