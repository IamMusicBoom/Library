package com.wma.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by WMA on 2021/11/10.
 */
public class SystemUtils {

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context,String phoneNum){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }
}
