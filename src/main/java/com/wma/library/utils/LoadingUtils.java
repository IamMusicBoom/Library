package com.wma.library.utils;

import android.app.Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class LoadingUtils {
    private Activity mActivity;
    private SweetAlertDialog mLoadingDialog;

    public LoadingUtils(Activity activity) {
        this.mActivity = activity;
    }

    public void showLoading(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
        }
        mLoadingDialog.setContentText(message);
        mLoadingDialog.show();
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
