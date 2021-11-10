package com.wma.library.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.wma.library.base.BaseApplication;
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

    public static final int REQUEST_CODE_ALERT_WINDOW = 10011;

    /**
     * 敏感权限
     */
    private static String[] dangerousPermissions = {"READ_CALENDAR", "WRITE_CALENDAR", "CAMERA", "READ_CONTACTS", "WRITE_CONTACTS", "GET_ACCOUNTS", "ACCESS_FINE_LOCATION"
            , "ACCESS_COARSE_LOCATION", "RECORD_AUDIO", "READ_PHONE_STATE", "CALL_PHONE", "READ_CALL_LOG", "WRITE_CALL_LOG", "ADD_VOICEMAIL", "USE_SIP", "PROCESS_OUTGOING_CALLS"
            , "BODY_SENSORS", "SEND_SMS", "RECEIVE_SMS", "READ_SMS", "RECEIVE_WAP_PUSH", "RECEIVE_MMS", "READ_EXTERNAL_STORAGE", "WRITE_EXTERNAL_STORAGE"};



    private static final String TAG = PermissionUtils.class.getSimpleName();

    private PermissionUtils() {
    }

    /**
     * 检查某个权限是否被赋予
     *
     * @param permission
     * @return
     */
    public static boolean havePermission(String permission) {
        return havePermission(permission, "");
    }

    public static boolean havePermission(String permission, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            packageName = BaseApplication.getContext().getPackageName();
        }
        PackageManager pm = BaseApplication.getContext().getPackageManager();
        int i = pm.checkPermission(permission, packageName);
        return PackageManager.PERMISSION_GRANTED == i;
    }

    /**
     * 检查是否所有权限都被赋予
     *
     * @param permissions
     * @return
     */
    public static List<String> checkIsPermissionAllGranted(String... permissions) {
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
    public static boolean isAllPermissionGranted(String... permissions) {
        List<String> list = checkIsPermissionAllGranted(permissions);
        return list.size() == 0;
    }


//    public static PermissionUtils getInstance(Context context) {
//        return new PermissionUtils(context);
//    }

    /**
     * 动态申请权限
     *
     * @param activity
     * @param list
     * @param requestcode
     */
    public static void requestPermissions(Activity activity, List<String> list, int requestcode) {
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
    public static List<String> requestResult(String[] permissions, int[] grantResults) {
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
    public static String getChineseByPermission(String permission) {
        if (Manifest.permission.CAMERA.equalsIgnoreCase(permission)) {
            return "相机";
        } else if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equalsIgnoreCase(permission)) {
            return "SD卡写";
        } else if (Manifest.permission.READ_EXTERNAL_STORAGE.equalsIgnoreCase(permission)) {
            return "SD卡读";
        } else if (Manifest.permission.READ_CALENDAR.equalsIgnoreCase(permission)) {
            return "日历读权限";
        } else if (Manifest.permission.WRITE_CALENDAR.equalsIgnoreCase(permission)) {
            return "新建/修改/删除日历";
        } else if (Manifest.permission.READ_CONTACTS.equalsIgnoreCase(permission)) {
            return "读取联系人";
        } else if (Manifest.permission.WRITE_CONTACTS.equalsIgnoreCase(permission)) {
            return "新建/修改/删除联系人";
        } else if (Manifest.permission.GET_ACCOUNTS.equalsIgnoreCase(permission)) {
            return "通讯录";
        } else if (Manifest.permission.ACCESS_FINE_LOCATION.equalsIgnoreCase(permission)) {
            return "精确位置信息";
        } else if (Manifest.permission.ACCESS_COARSE_LOCATION.equalsIgnoreCase(permission)) {
            return "粗略位置信息";
        } else if (Manifest.permission.RECORD_AUDIO.equalsIgnoreCase(permission)) {
            return "麦克风";
        } else if (Manifest.permission.READ_PHONE_STATE.equalsIgnoreCase(permission)) {
            return "读取通话状态和移动网络信息";
        } else if (Manifest.permission.CALL_PHONE.equalsIgnoreCase(permission)) {
            return "使用呼叫转移和拨打电话";
        } else if (Manifest.permission.READ_CALL_LOG.equalsIgnoreCase(permission)) {
            return "读取通话记录";
        } else if (Manifest.permission.WRITE_CALL_LOG.equalsIgnoreCase(permission)) {
            return "新建/修改/删除通话记录";
        } else if (Manifest.permission.ADD_VOICEMAIL.equalsIgnoreCase(permission)) {
            return "添加语音邮件";
        } else if (Manifest.permission.USE_SIP.equalsIgnoreCase(permission)) {
            return "USE_SIP";
        } else if (Manifest.permission.PROCESS_OUTGOING_CALLS.equalsIgnoreCase(permission)) {
            return "PROCESS_OUTGOING_CALLS";
        } else if (Manifest.permission.BODY_SENSORS.equalsIgnoreCase(permission)) {
            return "身体传感器";
        } else if (Manifest.permission.SEND_SMS.equalsIgnoreCase(permission)) {
            return "发送短信和发送彩信";
        } else if (Manifest.permission.RECEIVE_SMS.equalsIgnoreCase(permission)) {
            return "接受短信";
        } else if (Manifest.permission.READ_SMS.equalsIgnoreCase(permission)) {
            return "读取短信/彩信";
        } else if (Manifest.permission.RECEIVE_WAP_PUSH.equalsIgnoreCase(permission)) {
            return "信息：RECEIVE_WAP_PUSH";
        } else if (Manifest.permission.RECEIVE_MMS.equalsIgnoreCase(permission)) {
            return "信息：信RECEIVE_MMS";
        }
        return "";
    }

    /**
     * 是否为敏感权限
     *
     * @param permission
     * @return
     */
    public static boolean isDangerousPermission(String permission) {
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

    /**
     * 检查是否有悬浮窗权限
     * @return
     */
    public static boolean isAlertWindowGranted(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(BaseApplication.getContext());
        }
        return true;
    }

    /**
     * 申请悬浮窗权限
     * @param activity
     */
    public static void requestAlertWindowPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + BaseApplication.getContext().getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_ALERT_WINDOW);
        }

    }
}
