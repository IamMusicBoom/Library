package com.wma.library.base;

import android.app.Application;

import org.xutils.x;

/**
 * create by wma
 * on 2020/10/14 0014
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
