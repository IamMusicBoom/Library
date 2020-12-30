package com.wma.library.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.wma.library.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class LoadingDialog {
    private SweetAlertDialog mLoadingDialog;
    private static LoadingDialog mUtils;

    public static LoadingDialog getInstance() {
        if (mUtils == null) {
            mUtils = new LoadingDialog();
        }
        return mUtils;
    }


    public void showLoading(Activity mActivity, String message) {
        if (mLoadingDialog != null) {
            if(mLoadingDialog.isShowing()){
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;
        }
        mLoadingDialog = new SweetAlertDialog(mActivity, SweetAlertDialog.PROGRESS_TYPE);
        mLoadingDialog.setTitleText("");
        if(!TextUtils.isEmpty(message)){
            mLoadingDialog.setContentText(message);
        }
        mLoadingDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
        mLoadingDialog.show();
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
