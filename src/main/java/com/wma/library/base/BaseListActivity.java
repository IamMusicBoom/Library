package com.wma.library.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
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
public abstract class BaseListActivity< T extends BaseModule,B extends ViewDataBinding> extends BaseLoadActivity<T, B> {

    public BaseRecyclerAdapter mAdapter;
    public List<T> mList = new ArrayList<>();
    public RecyclerView mRecyclerView;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mAdapter = getAdapter();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 0;
                outRect.top = 15;
                outRect.right = 0;
                outRect.bottom = 15;

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }


    public void addList(List<T> list) {
        mAdapter.addData(list);
    }

    public abstract BaseRecyclerAdapter getAdapter();

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return findViewById(R.id.smart_refresh_layout);
    }
}
