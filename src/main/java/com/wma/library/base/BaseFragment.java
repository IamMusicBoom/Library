package com.wma.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wma.library.R;
import com.wma.library.impl.IBaseImpl;
import com.wma.library.log.Logger;
import com.wma.library.utils.JsonUtils;
import com.wma.library.utils.LoadingUtils;
import com.wma.library.widget.titlebar.OnBaseTitleBarClickListener;
import com.wma.library.widget.titlebar.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/10/21 0021
 */
public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements IBaseImpl, OnBaseTitleBarClickListener {
    public String TAG;
    public B mBinding;
    public TitleBar mTitleBar;
    public Context mContext;
    public ViewGroup mParent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        mParent = container;
        TAG = this.getClass().getSimpleName();
        return generateRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View generateRootView() {
        LinearLayout rootView = new LinearLayout(mContext);
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
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void onLeftLlClick(View view) {
        getActivity().onBackPressed();
    }

    @Override
    public void onRightLlClick(View view) {

    }

}
