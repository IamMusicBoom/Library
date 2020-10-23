package com.wma.library.impl;

import android.view.View;

/**
 * create by wma
 * on 2020/9/10 0010
 *
 */
public interface IBaseImpl {


    public String getTitleStr();

    public void init();

    public int getLayoutId();

    public View generateRootView();



}
