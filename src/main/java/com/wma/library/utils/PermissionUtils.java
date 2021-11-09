package com.wma.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.wma.library.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create by wma
 * on 2020/12/11 0011
 * <p>
 * 敏感权限
 * CALENDAR（日历）
 * READ_CALENDAR
 * WRITE_CALENDAR
 * CAMERA（相机）
 * CAMERA
 * CONTACTS（联系人）
 * READ_CONTACTS
 * WRITE_CONTACTS
 * GET_ACCOUNTS
 * LOCATION（位置）
 * ACCESS_FINE_LOCATION
 * ACCESS_COARSE_LOCATION
 * MICROPHONE（麦克风）
 * RECORD_AUDIO
 * PHONE（手机）
 * READ_PHONE_STATE
 * CALL_PHONE
 * READ_CALL_LOG
 * WRITE_CALL_LOG
 * ADD_VOICEMAIL
 * USE_SIP
 * PROCESS_OUTGOING_CALLS
 * SENSORS（传感器）
 * BODY_SENSORS
 * SMS（短信）
 * SEND_SMS
 * RECEIVE_SMS
 * READ_SMS
 * RECEIVE_WAP_PUSH
 * RECEIVE_MMS
 * STORAGE（存储卡）
 * READ_EXTERNAL_STORAGE
 * WRITE_EXTERNAL_STORAGE
 */
public class PermissionUtils {

    /**
     * 敏感权限
     */
    private String[] dangerousPermissions = {"READ_CALENDAR", "WRITE_CALENDAR", "CAMERA", "READ_CONTACTS", "WRITE_CONTACTS", "GET_ACCOUNTS", "ACCESS_FINE_LOCATION"
            , "ACCESS_COARSE_LOCATION", "RECORD_AUDIO", "READ_PHONE_STATE", "CALL_PHONE", "READ_CALL_LOG", "WRITE_CALL_LOG", "ADD_VOICEMAIL", "USE_SIP", "PROCESS_OUTGOING_CALLS"
            , "BODY_SENSORS", "SEND_SMS", "RECEIVE_SMS", "READ_SMS", "RECEIVE_WAP_PUSH", "RECEIVE_MMS", "READ_EXTERNAL_STORAGE", "WRITE_EXTERNAL_STORAGE"};

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
     * 动态申请权限
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
     *
     * @param permissions
     * @param grantResults
     */
    public List<String> requestResult(String[] permissions, int[] grantResults) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            Logger.d(TAG, "requestResult: permission = " + permissions[i] + " grant = " + grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermissions.add(permissions[i]);
            }
        }
        return notGrantedPermissions;
    }

    /**
     * 需要继续补充，获取权限的中文名称
     *
     * @param permission
     * @return
     */
    public String getChineseByPermission(String permission) {
        if (Manifest.permission.CAMERA.equalsIgnoreCase(permission)) {
            return "相机权限";
        }
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equalsIgnoreCase(permission) || Manifest.permission.READ_EXTERNAL_STORAGE.equalsIgnoreCase(permission)) {
            return "SD卡读写权限";
        }
        return "";
    }

    /**
     * 是否为敏感权限
     * @param permission
     * @return
     */
    public boolean isDangerousPermission(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return false;
        }
        for (String dangerousPermission : dangerousPermissions) {
            if (permission.contains(dangerousPermission)) {
                return true;
            }
        }
        return false;
    }
}
