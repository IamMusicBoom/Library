package com.wma.library.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;

import com.wma.library.utils.ScreenUtils;

/**
 * Created by WMA on 2021/11/12.
 */
public abstract class BaseDialog<BINDING extends ViewDataBinding> extends DialogFragment {
    public BINDING mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(attributes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    protected abstract void init(Bundle savedInstanceState);

    public abstract int getLayoutId();
}
