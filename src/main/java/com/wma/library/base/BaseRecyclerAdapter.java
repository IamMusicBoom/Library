package com.wma.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public abstract class BaseRecyclerAdapter<MODEL extends BaseModule, ITEM extends ViewDataBinding> extends RecyclerView.Adapter<BaseRecyclerHolder<ITEM>> {
    final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    public List<MODEL> mList;
    public Context mContext;

    public BaseRecyclerAdapter(List<MODEL> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder<ITEM> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new BaseRecyclerHolder<ITEM>(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseRecyclerHolder<ITEM> holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClickListener(holder, holder.getAdapterPosition(), mList.get(holder.getAdapterPosition()));
                }
            }
        });
        bindData(holder.getBinding(), mList.get(position), holder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public abstract void bindData(ITEM binding, MODEL data, BaseRecyclerHolder<ITEM> holder, int position);

    public abstract int getLayoutId();


    public void setOnItemClickListener(OnItemClickListener<MODEL, ITEM> listener) {
        this.listener = listener;
    }

    OnItemClickListener<MODEL, ITEM> listener;

    public interface OnItemClickListener<MODEL extends BaseModule, ITEM extends ViewDataBinding> {

        void onItemClickListener(BaseRecyclerHolder<ITEM> holder, int adapterPosition, MODEL t);
    }

    /**
     * 添加一堆数据
     *
     * @param list
     */
    public void addData(List<MODEL> list) {
        int start = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(start, list.size());
    }


    /**
     * 添加一条数据
     */
    public void addData(MODEL item) {
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
    public void addData(int position, List<MODEL> list) {
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
    public void addData(int position, MODEL item) {
        int start = position;
        mList.add(position, item);
        notifyItemRangeInserted(start, 1);

    }


    /**
     * 删除一条数据
     *
     * @param position
     * @param item
     */
    public void removeData(int position, MODEL item) {
        mList.remove(position);
        notifyItemRangeRemoved(position, 1);
    }

    /**
     * 删除一堆数据
     *
     * @param position
     * @param list
     */
    public void removeData(int position, List<MODEL> list) {
        mList.removeAll(list);
        notifyItemRangeRemoved(position, list.size());
    }

    /**
     * 清空数据
     */
    public void removeAllData() {
        notifyItemRangeRemoved(0, mList.size());
        mList.clear();
    }

}
