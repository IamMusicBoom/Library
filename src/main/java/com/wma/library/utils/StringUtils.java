package com.wma.library.utils;

/**
 * create by wma
 * on 2020/11/4 0004
 */
public class StringUtils {

    public static String findNumberInStr(String str){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c >= 48 && (int) c <= 57) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
