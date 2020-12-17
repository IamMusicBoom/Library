package com.wma.library.utils;

import android.content.Context;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.Toast;

/**
 * create by wma
 * on 2020/11/2 0002
 */
public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context c, int resId) {
        if (c == null) {
            return;
        }

        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c, resId, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context c, String str) {
        if (c == null) {
            return;
        }
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(c, str, Toast.LENGTH_SHORT);

//		toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * @param c
     * @param fromHtml
     */
    public static void showToast(Context c, Spanned fromHtml) {
        if (c == null) {
            return;
        }

        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(c, fromHtml, Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
