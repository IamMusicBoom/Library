package com.wma.library.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.wma.library.R;
import com.wma.library.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public abstract class BaseListActivity<MODEL extends BaseModule, ACT extends ViewDataBinding, ITEM extends ViewDataBinding> extends BaseLoadActivity<MODEL, ACT> {

    public BaseRecyclerAdapter<MODEL, ITEM> mAdapter;
    public List<MODEL> mList = new ArrayList<>();
    public RecyclerView mRecyclerView;

    public int mCurPage = 1;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter = getAdapter();
        mRecyclerView = initRecyclerView();
        if (mRecyclerView == null) {
            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(),LinearLayoutManager.VERTICAL));
        }
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mAdapter.removeAllData();
        mCurPage = 1;
        super.onRefresh(refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        mCurPage++;
        loadData();
    }

    public abstract BaseRecyclerAdapter<MODEL, ITEM> getAdapter();

    public RecyclerView initRecyclerView() {
        return null;

    }


    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return findViewById(R.id.smart_refresh_layout);
    }
}
