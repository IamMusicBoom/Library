package com.wma.library.widget.titlebar;

import android.view.View;

/**
 * create by wma
 * on 2020/6/3 0003
 */
public interface OnTitleBarAllClickListener extends OnTitleBarMidViewClickListener {
    void onLeftImgClick(View view);
    void onLeftTvClick(View view);
    void onRightImgClick(View view);
    void onRightTvClick(View view);

}
