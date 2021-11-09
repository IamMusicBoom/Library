package com.wma.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;

import com.wma.library.R;
import com.wma.library.impl.IBaseImpl;
import com.wma.library.utils.LoadingDialog;
import com.wma.library.utils.ScreenUtils;
import com.wma.library.utils.ToastUtils;
import com.wma.library.widget.titlebar.OnBaseTitleBarClickListener;
import com.wma.library.widget.titlebar.TitleBar;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * create by wma
 * on 2020/9/10 0010
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements IBaseImpl, OnBaseTitleBarClickListener {
    public String TAG;
    public B mBinding;
    public TitleBar mTitleBar;
    public Context mContext;
    public LinearLayout mRootView;
    private boolean mImmerse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        mContext = this;
        beforeSetContentView();
        setContentView(generateRootView());
        immerseStatus(false, false, ContextCompat.getColor(mContext, R.color.status_bar_color), 0);
        init(savedInstanceState);

    }


    @Override
    public View generateRootView() {
        mRootView = new LinearLayout(mContext);
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
        return mRootView;
    }

    /**
     * 在setContentView之前调用，需要使用WindowManager的
     */
    public void beforeSetContentView() {

    }


    /**
     * 是否沉浸到状态栏
     * <p>
     * 默认状态不沉浸到状态栏，且状态栏为白色图标
     *
     * @param isImmerse   true 沉浸到状态栏
     *                    false 不沉浸到状态栏
     * @param isLightMode true 背景高亮，字体，图片成黑色
     *                    false 背景低暗，字体，图片成白色
     * @param color       状态栏颜色
     * @param alpha       状态栏透明度
     */
    public void immerseStatus(boolean isImmerse, boolean isLightMode, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        mImmerse = isImmerse;
        if (isImmerse) {
            ScreenUtils.immerseStatusBar(this);
        }
        ScreenUtils.setStatusBarMode(this, isLightMode);
        ScreenUtils.setStatusBarColor(this, color, alpha);
    }


    /**
     * 是否沉浸到导航栏
     * <p>
     * 默认状态不沉浸到导航栏，且状态栏为白色图标，黑色背景
     *
     * @param isImmerse   true 沉浸到状态栏
     *                    false 不沉浸到状态栏
     * @param isLightMode true 背景高亮，字体，图片成黑色
     *                    false 背景低暗，字体，图片成白色
     * @param color       状态栏颜色
     * @param alpha       状态栏透明度
     */
    public void immerseNavigation(boolean isImmerse, boolean isLightMode, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (isImmerse) {
            ScreenUtils.immerseNavigationBar(this);
        }
        ScreenUtils.setNavigationBarMode(this, isLightMode);
        ScreenUtils.setNavigationBarColor(this, color, alpha);
    }

    /**
     * @param drawerLayout
     * @param color
     * @param alpha
     */
    public void immerseStatusWithDrawerLayout(DrawerLayout drawerLayout, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        ScreenUtils.setColorForDrawerLayout(this, drawerLayout, color, alpha);
    }


    @Override
    public void onLeftLlClick(View view) {
        onBackPressed();
    }

    @Override
    public void onRightLlClick(View view) {

    }

    public Type getType() {
        ParameterizedType genType = (ParameterizedType) getClass().getGenericSuperclass();

        Type[] actualTypeArguments = ((ParameterizedType) genType).getActualTypeArguments();

        return actualTypeArguments[0];
    }


    public void showToast(String msg) {
        ToastUtils.showToast(this, msg);
    }

    public void showToast(int msgId) {
        ToastUtils.showToast(this, msgId);
    }

    public void showLoading(String msg) {
        LoadingDialog.getInstance().showLoading(this, msg);
    }

    public void hideLoading() {
        LoadingDialog.getInstance().hideLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
