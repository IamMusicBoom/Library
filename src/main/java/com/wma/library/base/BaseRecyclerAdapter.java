package com.wma.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.wma.library.log.Logger;

import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public abstract class BaseRecyclerAdapter<T extends BaseModule, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    private List<T> mList;
    private Context mContext;

    public BaseRecyclerAdapter(List<T> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        BaseRecyclerHolder holder = new BaseRecyclerHolder<B>(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        Logger.d(TAG, "onBindViewHolder: holder.getBinding() = " + holder.getBinding());
        bindData((B) holder.getBinding(), mList.get(position), holder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract void bindData(B binding, T data, BaseRecyclerHolder holder, int position);

    public abstract int getLayoutId();

    /**
     * 添加一堆数据
     *
     * @param list
     */
    public void addData(List<T> list) {
        int start = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }


    /**
     * 添加一条数据
     */
    public void addData(T item) {
        int start = mList.size();
        mList.add(item);
        notifyItemRangeInserted(start, 1);
    }

    /**
     * 在某个位置添加一串数据
     *
     * @param position
     * @param list
     */
    public void addData(int position, List<T> list) {
        int start = position;
        mList.addAll(position, list);
        notifyItemRangeInserted(start, list.size());

    }

    /**
     * 在某个位置添加一条数据
     *
     * @param position
     * @param item
     */
    public void addData(int position, T item) {
        int start = position;
        mList.add(position, item);
        notifyItemRangeInserted(start, 1);

    }
}
