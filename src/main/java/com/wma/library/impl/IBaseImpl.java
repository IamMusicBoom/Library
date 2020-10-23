package com.wma.library.impl;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * create by wma
 * on 2020/9/10 0010
 *
 */
public interface IBaseImpl {


    public String getTitleStr();

    public void init(@Nullable Bundle savedInstanceState);

    public int getLayoutId();

    public View generateRootView();




}
