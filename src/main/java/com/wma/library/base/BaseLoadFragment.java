package com.wma.library.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wma.library.R;
import com.wma.library.log.Logger;
import com.wma.library.widget.titlebar.TitleBar;

import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public abstract class BaseLoadFragment<T extends ViewDataBinding> extends BaseFragment<T> implements Callback.CommonCallback<String>{

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void init(Bundle savedInstanceState) {
        Logger.d(TAG, "init: ");
        mSwipeRefreshLayout.setRefreshing(true);
        loadData();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    protected abstract void loadData();

    @Override
    public View generateRootView() {
        mSwipeRefreshLayout = new SwipeRefreshLayout(mContext);
        LinearLayout rootView = new LinearLayout(mContext);
        mSwipeRefreshLayout.addView(rootView);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (null != getTitleStr()) {// 生成title
            View title = getLayoutInflater().inflate(R.layout.title_bar_view, rootView, false);
            mTitleBar = new TitleBar(mContext, title);
            mTitleBar.setTitleText(getTitleStr());
            mTitleBar.setOnTitleBarClickListener(this);
            rootView.addView(title);
        }
        if (getLayoutId() != 0) {// 生成内容区域
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), mParent, false);
            rootView.addView(mBinding.getRoot());
        }
        return mSwipeRefreshLayout;
    }


    @Override
    public void onSuccess(String result) {
        Logger.d(TAG, "onSuccess: " + result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Logger.d(TAG, "onError: " + ex.getMessage());
    }

    @Override
    public void onCancelled(Callback.CancelledException cex) {
        Logger.d(TAG, "onCancelled: ");
    }

    @Override
    public void onFinished() {
        Logger.d(TAG, "onFinished: ");
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
