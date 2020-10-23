package com.wma.library.base;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
