package com.wma.library.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public class BaseRecyclerHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    final String TAG = BaseRecyclerHolder.class.getSimpleName();
    B mBinding;
    public BaseRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }
    public B getBinding(){
        return  mBinding;
    }
}
