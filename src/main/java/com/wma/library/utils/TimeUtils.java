package com.wma.library.utils;

import com.wma.library.log.Logger;

import java.util.Calendar;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class TimeUtils {
    static final String TAG = TimeUtils.class.getSimpleName();




    public static int getCurMonth() {
        return (Calendar.getInstance().get(Calendar.MONTH) + 1);
    }


    public static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}
