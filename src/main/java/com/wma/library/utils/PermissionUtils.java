package com.wma.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by wma
 * on 2020/12/11 0011
 */
public class PermissionUtils {
    private Context mContext;

    private final String TAG = PermissionUtils.class.getSimpleName();
    private PermissionUtils(Context context) {
        mContext = context;
    }

    /**
     * 检查某个权限是否被赋予
     *
     * @param permission
     * @return
     */
    public boolean havePermission(String permission) {
        PackageManager pm = mContext.getPackageManager();
        int i = pm.checkPermission(permission, mContext.getPackageName());
        if (PackageManager.PERMISSION_GRANTED == i) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否所有权限都被赋予
     *
     * @param permissions
     * @return
     */
    public List<String> checkIsPermissionAllGranted(String... permissions) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (havePermission(permission)) {
                continue;
            }
            notGrantedPermissions.add(permission);
        }
        return notGrantedPermissions;
    }


    /**
     * 检查是否所有权限都被赋予
     *
     * @param permissions
     * @return
     */
    public boolean isAllPermissionGranted(String... permissions) {
        List<String> list = checkIsPermissionAllGranted(permissions);
        return list.size() == 0;
    }


    public static PermissionUtils getInstance(Context context) {
        return new PermissionUtils(context);
    }

    /**
     * 动态申请全向
     *
     * @param activity
     * @param list
     * @param requestcode
     */
    public void requestPermissions(Activity activity, List<String> list, int requestcode) {
        String[] permissions = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            permissions[i] = list.get(i);
        }
        ActivityCompat.requestPermissions(activity, permissions, requestcode);
    }

    /**
     * 权限申请后的处理
     * @param permissions
     * @param grantResults
     */
    public List<String> requestResult(String[] permissions, int[] grantResults) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Logger.d(TAG, "requestResult: permission = " + permissions[i] + " grant = " + grantResults[i]);
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                notGrantedPermissions.add(permissions[i]);
            }
        }
        return notGrantedPermissions;
    }

    public String getChineseByPermission(String permission) {
        if(Manifest.permission.CAMERA.equalsIgnoreCase(permission)){
            return "相机权限";
        }
        if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equalsIgnoreCase(permission) || Manifest.permission.READ_EXTERNAL_STORAGE.equalsIgnoreCase(permission)){
            return "SD卡读写权限";
        }
        return "";
    }
}
