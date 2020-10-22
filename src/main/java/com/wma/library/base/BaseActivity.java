package com.wma.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.wma.library.R;
import com.wma.library.impl.IBaseImpl;
import com.wma.library.widget.titlebar.OnBaseTitleBarClickListener;
import com.wma.library.widget.titlebar.TitleBar;

/**
 * create by wma
 * on 2020/9/10 0010
 */
public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements IBaseImpl, OnBaseTitleBarClickListener {
    public String TAG;
    public T mBinding;
    public TitleBar mTitleBar;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mContext = this;
        beforeSetContentView();
        setContentView(generateRootView());
        init();
    }


    @Override
    public View generateRootView() {
        LinearLayout rootView = new LinearLayout(mContext);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (null != getTitleStr()) {// 生成title
            Toolbar title = (Toolbar) getLayoutInflater().inflate(R.layout.title_bar_view, rootView,false);
            mTitleBar = new TitleBar(mContext, title);
            mTitleBar.setTitleText(getTitleStr());
            mTitleBar.setOnTitleBarClickListener(this);
            rootView.addView(title);
            setSupportActionBar(title);
        }
        if(getLayoutId() != 0){// 生成内容区域
            mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), rootView, false);
            if(mBinding != null){
                rootView.addView(mBinding.getRoot());
            }
        }
        return rootView;
    }

    /**
     * 在setContentView之前调用，需要使用WindowManager的
     */
    public void beforeSetContentView() {

    }

    @Override
    public void onLeftLlClick(View view) {
        onBackPressed();
    }

    @Override
    public void onRightLlClick(View view) {

    }
}
