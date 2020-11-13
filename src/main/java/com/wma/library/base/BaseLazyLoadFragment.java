package com.wma.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/3/23 0023
 * 懒加载Fragment
 */
public abstract class BaseLazyLoadFragment<T extends BaseModule, B extends ViewDataBinding> extends BaseLoadFragment<T, B> {
    /**
     * 当前Fragment对用户是否可见
     */
    private boolean isVisible = false;

    /**
     * 当前的Fragment是否已经创建视图
     */
    private boolean isViewCreated = false;

    /**
     * 当前的Fragment是否已经加载数据
     */
    protected boolean isAlreadyLoadData = false;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        isViewCreated = true;
        isAlreadyLoadData = false;
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isVisible && !isAlreadyLoadData) {
            autoRefresh();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isViewCreated && !isAlreadyLoadData) {
            autoRefresh();
        }
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadLazyData();
    }

    /**
     * 懒加载数据
     */
    protected void loadLazyData() {
        isAlreadyLoadData = true;
    }

}
