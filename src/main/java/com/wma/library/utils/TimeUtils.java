package com.wma.library.utils;

import com.wma.library.log.Logger;

import java.util.Calendar;

/**
 * create by wma
 * on 2020/10/23 0023
 */
public class TimeUtils {
    static final String TAG = TimeUtils.class.getSimpleName();

    public static String getCurConstellation() {
        int curMonth = getCurMonth();
        int curDay = getCurDay();
        if(curMonth == 1){
            if(curDay >=1 && curDay<=19){
                return "摩羯座";
            }else{
                return "水瓶座";
            }
        }else if(curMonth == 2){
            if(curDay >=1 && curDay<=18){
                return "水瓶座";
            }else{
                return "双鱼座";
            }
        }else if(curMonth == 3){
            if(curDay >=1 && curDay<=20){
                return "双鱼座";
            }else{
                return "白羊座";
            }
        }else if(curMonth == 4){
            if(curDay >=1 && curDay<=19){
                return "白羊座";
            }else{
                return "金牛座";
            }
        }else if(curMonth == 5){
            if(curDay >=1 && curDay<=20){
                return "金牛座";
            }else{
                return "双子座";
            }
        }else if(curMonth == 6){
            if(curDay >=1 && curDay<=21){
                return "双子座";
            }else{
                return "巨蟹座";
            }
        }else if(curMonth == 7){
            if(curDay >=1 && curDay<=22){
                return "巨蟹座";
            }else{
                return "狮子座";
            }
        }else if(curMonth == 8){
            if(curDay >=1 && curDay<=22){
                return "狮子座";
            }else{
                return "处女座";
            }
        }else if(curMonth == 9){
            if(curDay >=1 && curDay<=22){
                return "处女座";
            }else{
                return "天秤座";
            }
        }else if(curMonth == 10){
            if(curDay >=1 && curDay<=23){
                return "天秤座";
            }else{
                return "天蝎座";
            }
        }else if(curMonth == 11){
            if(curDay >=1 && curDay<=22){
                return "天蝎座";
            }else{
                return "射手座";
            }
        }else if(curMonth == 12){
            if(curDay >=1 && curDay<=21){
                return "射手座";
            }else{
                return "摩羯座";
            }
        }
        return "";
    }


    private static int getCurMonth() {
        return (Calendar.getInstance().get(Calendar.MONTH) + 1);
    }


    private static int getCurDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
}
