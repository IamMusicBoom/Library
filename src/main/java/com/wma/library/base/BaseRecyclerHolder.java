package com.wma.library.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.wma.library.log.Logger;

/**
 * create by wma
 * on 2020/11/5 0005
 */
public class BaseRecyclerHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    final String TAG = BaseRecyclerHolder.class.getSimpleName();
    B mBinding;
    public BaseRecyclerHolder(@NonNull View itemView) {
        super(itemView);
    }
    public B getBinding(){
        mBinding = DataBindingUtil.bind(itemView);
        return  mBinding;
    }
}
