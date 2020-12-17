package com.wma.library.utils;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.wma.library.log.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static String getStringDateToDay(long time){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
    }
    public static String getStringDateToMin(long time){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(time);
    }
    public static String getStringDateToSec(long time){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(time);
    }
}
