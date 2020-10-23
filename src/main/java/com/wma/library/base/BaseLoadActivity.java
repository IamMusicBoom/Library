package com.wma.library.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.wma.library.R;
import com.wma.library.widget.titlebar.TitleBar;

import org.xutils.common.Callback;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public abstract class BaseLoadActivity<T extends ViewDataBinding> extends BaseActivity<T> implements Callback.CommonCallback<String> {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void init(Bundle savedInstanceState) {
        mSwipeRefreshLayout.setRefreshing(true);
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
        mRootView = new LinearLayout(mContext);
        mSwipeRefreshLayout.addView(mRootView);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (null != getTitleStr()) {// 生成title
            Toolbar title = (Toolbar) getLayoutInflater().inflate(R.layout.title_bar_view, mRootView, false);
            mTitleBar = new TitleBar(mContext, title);
            mTitleBar.setTitleText(getTitleStr());
            mTitleBar.setOnTitleBarClickListener(this);
            mRootView.addView(title);
            setSupportActionBar(title);
        }
        if (getLayoutId() != 0) {// 生成内容区域
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), mRootView, false);
            if (mBinding != null) {
                mRootView.addView(mBinding.getRoot());
            }
        }
        return mSwipeRefreshLayout;
    }


    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
