package com.wma.library.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wma.library.R;
import com.wma.library.databinding.LayoutRefreshListBinding;
import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public abstract class BaseListActivity<T extends BaseModule, B extends ViewDataBinding> extends BaseLoadActivity<B,T> {

    public BaseRecyclerAdapter mAdapter;
    public List<T> mList = new ArrayList<>();
    public RecyclerView mRecyclerView;
    public SmartRefreshLayout mSmartRefreshLayout;
    public LayoutRefreshListBinding mRefreshBinding;

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        mAdapter = getAdapter();
        mRefreshBinding = DataBindingUtil.bind(getRefreshView());
        mRecyclerView = mRefreshBinding.recyclerView;
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout = mRefreshBinding.smartRefreshLayout;
        initRefreshLayout();
        loadData();
    }

    private void initRefreshLayout() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setEnableLoadMore(true);
    }

    protected abstract View getRefreshView();

    public void addList(List<T> list) {
        mAdapter.addData(list);
    }

    public abstract BaseRecyclerAdapter getAdapter();

    public abstract void loadData();


}
