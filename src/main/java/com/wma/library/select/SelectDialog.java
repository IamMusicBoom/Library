package com.wma.library.select;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.wma.library.R;
import com.wma.library.databinding.DialogChooseFileTypeBinding;
import com.wma.library.log.Logger;

import java.util.ArrayList;


/**
 * create by wma
 * on 2020/12/8 0008
 */
public class SelectDialog extends DialogFragment implements View.OnClickListener {
    final String TAG = SelectDialog.class.getSimpleName();
    public static final int REQUEST_CODE = 200;// 请求码

    public static final String KEY_TYPE = "KEY_TYPE";
    public static final String KEY_LIMIT = "KEY_LIMIT";
    public static final String KEY_SELECT_LIST = "KEY_SELECT_LIST";


    DialogChooseFileTypeBinding mBinding;

    private SelectDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_choose_file_type, container, false);
        mBinding.tvCancel.setOnClickListener(this);
        mBinding.tvChooseFromLocal.setOnClickListener(this);
        mBinding.tvTakePicture.setOnClickListener(this);

        return mBinding.getRoot();
    }


    @Override
    public void onResume() {
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.windowAnimations = R.style.BottomDialogAnimation;
        getDialog().getWindow().setAttributes(params);
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClick(View v) {
        if (v == mBinding.tvChooseFromLocal) {
            SelectActivity.chooseFile(getActivity(), getArguments());
        } else if (v == mBinding.tvTakePicture) {
            Logger.d(TAG, "onClick: 照相");
        }
        dismiss();
    }


    public static class Builder {
        /**
         * 选择种类
         * {@link FileType#AUDIO}
         * {@link FileType#IMAGE}
         * {@link FileType#VIDEO}
         * {@link FileType#FILE}
         */
        private String type;

        /**
         * 选择数量：0 ~ MAX
         */
        private Integer limit;


        /**
         * 请求码
         */
        private Integer requestCode;



        public String getType() {
            return type;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Integer getLimit() {
            return limit;
        }

        public Builder setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Integer getRequestCode() {
            return requestCode;
        }

        public Builder setRequestCode(Integer requestCode) {
            this.requestCode = requestCode;
            return this;
        }


        public SelectDialog create() {
            SelectDialog dialog = new SelectDialog();
            dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TYPE, type);
            bundle.putInt(KEY_LIMIT, limit);
            dialog.setArguments(bundle);
            return dialog;
        }
    }

}
