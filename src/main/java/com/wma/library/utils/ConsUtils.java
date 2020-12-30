package com.wma.library.utils;

import android.icu.util.Calendar;

import com.wma.library.log.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * create by wma
 * on 2020/10/29 0029
 */
public class ConsUtils {

    public static final String M_J_Z = "摩羯座";
    public static final String S_P_Z = "水瓶座";
    public static final String S_Y_Z = "双鱼座";
    public static final String B_Y_Z = "白羊座";
    public static final String J_N_Z = "金牛座";
    public static final String SH_Z_Z = "双子座";
    public static final String J_X_Z = "巨蟹座";
    public static final String S_Z_Z = "狮子座";
    public static final String C_N_Z = "处女座";
    public static final String T_C_Z = "天秤座";
    public static final String T_X_Z = "天蝎座";
    public static final String S_S_Z = "射手座";

    public static String getCurConstellation() {
        return getConstellationByDate(-1, -1);
    }

    /**
     * @param time
     * @return
     */
    public static String getConstellation(long time) {
        if (time <= 0) {
            return getCurConstellation();
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(time);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return getConstellationByDate(month + 1, day);

    }


    public static String getConstellationByDate(int month, int day) {
        int curMonth;
        int curDay;
        if (month == -1 || day == -1) {
            curMonth = TimeUtils.getCurMonth();
            curDay = TimeUtils.getCurDay();
        } else {
            curMonth = month;
            curDay = day;
        }
        if (curMonth == 1) {
            if (curDay >= 1 && curDay <= 19) {
                return M_J_Z;
            } else {
                return S_P_Z;
            }
        } else if (curMonth == 2) {
            if (curDay >= 1 && curDay <= 18) {
                return S_P_Z;
            } else {
                return S_Y_Z;
            }
        } else if (curMonth == 3) {
            if (curDay >= 1 && curDay <= 20) {
                return S_Y_Z;
            } else {
                return B_Y_Z;
            }
        } else if (curMonth == 4) {
            if (curDay >= 1 && curDay <= 19) {
                return B_Y_Z;
            } else {
                return J_N_Z;
            }
        } else if (curMonth == 5) {
            if (curDay >= 1 && curDay <= 20) {
                return J_N_Z;
            } else {
                return SH_Z_Z;
            }
        } else if (curMonth == 6) {
            if (curDay >= 1 && curDay <= 21) {
                return SH_Z_Z;
            } else {
                return J_X_Z;
            }
        } else if (curMonth == 7) {
            if (curDay >= 1 && curDay <= 22) {
                return J_X_Z;
            } else {
                return S_Z_Z;
            }
        } else if (curMonth == 8) {
            if (curDay >= 1 && curDay <= 22) {
                return S_Z_Z;
            } else {
                return C_N_Z;
            }
        } else if (curMonth == 9) {
            if (curDay >= 1 && curDay <= 22) {
                return C_N_Z;
            } else {
                return T_C_Z;
            }
        } else if (curMonth == 10) {
            if (curDay >= 1 && curDay <= 23) {
                return T_C_Z;
            } else {
                return T_X_Z;
            }
        } else if (curMonth == 11) {
            if (curDay >= 1 && curDay <= 22) {
                return T_X_Z;
            } else {
                return S_S_Z;
            }
        } else if (curMonth == 12) {
            if (curDay >= 1 && curDay <= 21) {
                return S_S_Z;
            } else {
                return M_J_Z;
            }
        }
        return "";
    }
}
