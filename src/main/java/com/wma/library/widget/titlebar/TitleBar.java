package com.wma.library.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

import com.wma.library.R;
import com.wma.library.utils.DPUtils;


/**
 * create by wma
 * on 2020/6/2 0002
 * 通用 title
 */
public class TitleBar implements View.OnClickListener {
    private final String TAG = TitleBar.class.getSimpleName();
    private Context mContext;
    private ImageView mLeftImg, mMidImg, mRightImg;
    private TextView mLeftTv, mMidTv, mRightTv;
    /**
     * showMode 由 6位数字组成，数字由01组成，0代表GONE，1代表VISIBLE，2代表INVISIBLE
     * 111111
     * 第一位 mLeftImg
     * 第二位 mLeftTv
     * 第三位 mMidTv
     * 第四位 mMidImg
     * 第五位 mRightTv
     * 第六位 mRightImg
     */
    private String mShowMode;
    private View mLeftLl, mRightLl;
    private View mRootView;
    private OnBaseTitleBarClickListener onTitleBarClickListener;


    public void setOnTitleBarClickListener(OnBaseTitleBarClickListener onBaseTitleBarClickListener) {
        this.onTitleBarClickListener = onBaseTitleBarClickListener;
        if (onTitleBarClickListener != null) {
            if (onTitleBarClickListener instanceof OnTitleBarAllClickListener) {
                mMidImg.setOnClickListener(this);
                mMidTv.setOnClickListener(this);
                mLeftImg.setOnClickListener(this);
                mLeftTv.setOnClickListener(this);
                mRightTv.setOnClickListener(this);
                mRightImg.setOnClickListener(this);
            } else if (onTitleBarClickListener instanceof OnTitleBarMidViewClickListener) {
                mMidImg.setOnClickListener(this);
                mMidTv.setOnClickListener(this);
            }
            mLeftLl.setOnClickListener(this);
            mRightLl.setOnClickListener(this);

        }
    }

    public View getLeftLl() {
        return mLeftLl;
    }

    public View getRightLl() {
        return mRightLl;
    }


    public View getRootView() {
        return mRootView;
    }


    public ImageView getLeftImg() {
        return mLeftImg;
    }


    public ImageView getMidImg() {
        return mMidImg;
    }


    public ImageView getRightImg() {
        return mRightImg;
    }


    public TextView getLeftTv() {
        return mLeftTv;
    }


    public TextView getMidTv() {
        return mMidTv;
    }


    public TextView getRightTv() {
        return mRightTv;
    }

    public TitleBar(Context context, View view) {
        mRootView = view;
        mRootView.setBackgroundResource(R.drawable.title_bar_bg);
        mContext = context;
        initViews();
        goSetPadding();
    }


    /**
     * @param showMode showMode 由 6位数字组成，数字由01组成，0代表GONE，1代表VISIBLE，2代表INVISIBLE
     *                 111111
     *                 第一位 mLeftImg
     *                 第二位 mLeftTv
     *                 第三位 mMidTv
     *                 第四位 mMidImg
     *                 第五位 mRightTv
     *                 第六位 mRightImg
     */
    public void setShowMode(String showMode) {
        mShowMode = showMode;
        if (TextUtils.isEmpty(mShowMode)) {
            mShowMode = "100000";
        }
        if (!TextUtils.isEmpty(mShowMode) && mShowMode.length() == 6) {
            mLeftImg.setVisibility(isVisibility(mShowMode.charAt(0)));
            mLeftTv.setVisibility(isVisibility(mShowMode.charAt(1)));
            mMidTv.setVisibility(isVisibility(mShowMode.charAt(2)));
            mMidImg.setVisibility(isVisibility(mShowMode.charAt(3)));
            mRightTv.setVisibility(isVisibility(mShowMode.charAt(4)));
            mRightImg.setVisibility(isVisibility(mShowMode.charAt(5)));
            mLeftLl.setVisibility((mLeftImg.getVisibility() == View.GONE && mLeftTv.getVisibility() == View.GONE) ? View.GONE : View.VISIBLE);
            mRightLl.setVisibility((mRightImg.getVisibility() == View.GONE && mRightTv.getVisibility() == View.GONE) ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 设置每个控件的padding
     */
    private void goSetPadding() {
        if (isVisibility(mLeftImg) && isVisibility(mLeftTv)) {//两个都显示
            mLeftImg.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 5), 0);
            mLeftTv.setPadding(DPUtils.dip2px(mContext, 5), 0, DPUtils.dip2px(mContext, 10), 0);
        }
        if (isVisibility(mLeftImg) && !isVisibility(mLeftTv)) {
            mLeftImg.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 10), 0);
        }
        if (!isVisibility(mLeftImg) && isVisibility(mLeftTv)) {
            mLeftTv.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 10), 0);
        }

        if (isVisibility(mRightImg) && isVisibility(mRightTv)) {//两个都显示
            mRightImg.setPadding(DPUtils.dip2px(mContext, 5), 0, DPUtils.dip2px(mContext, 10), 0);
            mRightTv.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 5), 0);
        }
        if (isVisibility(mRightImg) && !isVisibility(mRightTv)) {
            mRightImg.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 10), 0);
        }
        if (!isVisibility(mRightImg) && isVisibility(mRightTv)) {
            mRightTv.setPadding(DPUtils.dip2px(mContext, 10), 0, DPUtils.dip2px(mContext, 10), 0);
        }
    }


    private int isVisibility(char charAt) {
        if ("0".equals(String.valueOf(charAt))) {
            return View.GONE;
        } else if ("1".equals(String.valueOf(charAt))) {
            return View.VISIBLE;
        } else if ("2".equals(String.valueOf(charAt))) {
            return View.INVISIBLE;
        }
        return View.GONE;
    }


    private boolean isVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
    }

    private void initViews() {
        mLeftImg = mRootView.findViewById(R.id.title_left_img);
        mMidImg = mRootView.findViewById(R.id.title_mid_img);
        mRightImg = mRootView.findViewById(R.id.title_right_img);
        mLeftTv = mRootView.findViewById(R.id.title_left_tv);
        mMidTv = mRootView.findViewById(R.id.title_mid_tv);

        mRightTv = mRootView.findViewById(R.id.title_right_tv);
        mLeftLl = mRootView.findViewById(R.id.title_left_ll);
        mRightLl = mRootView.findViewById(R.id.title_right_ll);
    }


    public void setBackgroundColor(@ColorInt int color) {
        mRootView.setBackgroundColor(color);
    }

    @Override
    public void onClick(View v) {
        if (onTitleBarClickListener == null) {
            return;
        }
        if (v == mLeftLl) {
            onTitleBarClickListener.onLeftLlClick(v);
        } else if (v == mRightLl) {
            onTitleBarClickListener.onRightLlClick(v);
        } else if (v == mMidImg) {
            if (onTitleBarClickListener instanceof OnTitleBarMidViewClickListener) {
                ((OnTitleBarMidViewClickListener) onTitleBarClickListener).onMidImgClick(v);
            }
        } else if (v == mMidTv) {
            if (onTitleBarClickListener instanceof OnTitleBarMidViewClickListener) {
                ((OnTitleBarMidViewClickListener) onTitleBarClickListener).onMidTvClick(v);
            }
        } else if (v == mLeftImg) {
            if (onTitleBarClickListener instanceof OnTitleBarAllClickListener) {
                ((OnTitleBarAllClickListener) onTitleBarClickListener).onLeftImgClick(v);
            }
        } else if (v == mLeftTv) {
            if (onTitleBarClickListener instanceof OnTitleBarAllClickListener) {
                ((OnTitleBarAllClickListener) onTitleBarClickListener).onLeftTvClick(v);
            }
        } else if (v == mRightImg) {
            if (onTitleBarClickListener instanceof OnTitleBarAllClickListener) {
                ((OnTitleBarAllClickListener) onTitleBarClickListener).onRightImgClick(v);
            }
        } else if (v == mRightTv) {
            if (onTitleBarClickListener instanceof OnTitleBarAllClickListener) {
                ((OnTitleBarAllClickListener) onTitleBarClickListener).onRightTvClick(v);
            }
        }
    }

    public void setTitleText(int resource) {
        if (resource <= 0) {
            mMidTv.setVisibility(View.INVISIBLE);
        } else {
            mMidTv.setVisibility(View.VISIBLE);
            mMidTv.setText(resource);
        }
    }

    public void setTitleText(String text) {
        if (TextUtils.isEmpty(text)) {
            mMidTv.setVisibility(View.INVISIBLE);
        } else {
            mMidTv.setVisibility(View.VISIBLE);
            mMidTv.setText(text);
        }
    }

    public void setLeftText(int resource) {
        if (resource <= 0) {
            mLeftTv.setVisibility(View.INVISIBLE);
        } else {
            mLeftTv.setVisibility(View.VISIBLE);
            mLeftTv.setText(resource);
        }
    }

    public void setLeftText(String text) {
        if (TextUtils.isEmpty(text)) {
            mLeftTv.setVisibility(View.INVISIBLE);
        } else {
            mLeftTv.setVisibility(View.VISIBLE);
            mLeftTv.setText(text);
        }
    }

    public void setRightText(int resource) {
        if (resource <= 0) {
            mRightTv.setVisibility(View.INVISIBLE);
        } else {
            mRightTv.setVisibility(View.VISIBLE);
            mRightTv.setText(resource);
        }
    }

    public void setRightText(String text) {
        if (TextUtils.isEmpty(text)) {
            mRightTv.setVisibility(View.INVISIBLE);
        } else {
            mRightTv.setVisibility(View.VISIBLE);
            mRightTv.setText(text);
        }
    }

    public void setLeftImg(int resource) {
        if (resource <= 0) {
            mLeftImg.setVisibility(View.INVISIBLE);
        } else {
            mLeftImg.setVisibility(View.VISIBLE);
            mLeftImg.setImageResource(resource);
        }
    }

    public void setRightImg(int resource) {
        if (resource <= 0) {
            mRightImg.setVisibility(View.INVISIBLE);
        } else {
            mRightImg.setVisibility(View.VISIBLE);
            mRightImg.setImageResource(resource);
        }
    }
}
