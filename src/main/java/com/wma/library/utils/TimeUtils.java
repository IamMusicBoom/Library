package com.wma.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class TimeUtils {
    public static final String TAG = TimeUtils.class.getSimpleName();
    public static final int TIME_UTIL_TYPE_TO_DAY = 0;// 显示到天
    public static final int TIME_UTIL_TYPE_TO_MINUTE = 1;// 显示到分钟
    public static final int TIME_UTIL_TYPE_TO_SECONDS = 2;// 显示到秒





    public static int getCurMonth() {
        return (Calendar.getInstance().get(Calendar.MONTH) + 1);
    }


    public static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    /**
     * 日期转时间戳
     * @param s 格式为 yyyy-MM-dd HH:mm:ss 的字符串
     * @return
     */
    public static long dateToStamp(String s) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            Date date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return 0L;
    }


    /**
     * 根据时间戳获取时间字符串
     * @param time
     * @param type
     * @return
     */
    public static String getStringByMills(long time, int type) {
        if (type == TIME_UTIL_TYPE_TO_DAY) {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
        } else if (type == TIME_UTIL_TYPE_TO_MINUTE) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(time);
        } else if (type == TIME_UTIL_TYPE_TO_SECONDS) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(time);
        }
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(time);
    }



}
