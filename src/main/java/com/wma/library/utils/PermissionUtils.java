package com.wma.library.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.wma.library.base.BaseApplication;
import com.wma.library.data.PermissionData;
import com.wma.library.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    private static final int REQUEST_CODE_USAGE_STATS = 10012;

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
        return PermissionData.getPermissionName(permission);
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
     *
     * @return
     */
    public static boolean isAlertWindowGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(BaseApplication.getContext());
        }
        return true;
    }

    /**
     * 申请悬浮窗权限
     *
     * @param activity
     */
    public static void requestAlertWindowPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + BaseApplication.getContext().getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_ALERT_WINDOW);
        }

    }

    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

    /**
     * 检查是否有通知栏管理权限
     *
     * @return
     */
    public static boolean isNotificationListenerEnabled() {
        //NotificationManagerCompat.getEnabledListenerPackages国外某些手机系统会报错
        try {
            Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(BaseApplication.getContext());
            if (packageNames.contains(BaseApplication.getContext().getPackageName())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 跳转申请通知栏管理权限
     *
     * @param context
     */
    public static void openNotificationListenSettings(Context context) {
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * 应用查看权限是否开启
     *
     * @param context
     * @return
     */
    public static boolean isUsageStatsPermissionGranted(Context context) {
        if (!isSupportUsageStats(context)) {
            return true;
        }
        if (isUsageStatsGrant(context)) {
            return true;
        }
        return false;
    }

    public static boolean isSupportUsageStats(Context context) {
        if (null == context || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list != null && list.size() > 0;
    }

    /**
     * 查看使用权限
     *
     * @return
     */
    private static boolean isUsageStatsGrant(Context context) {
        AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        final int mode = opsManager.checkOp(AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            return context.checkCallingPermission("android.permission.PACKAGE_USAGE_STATS")
                    == PackageManager.PERMISSION_GRANTED;
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static void requestUsageStatsPermission(Activity activity) {

        if (null == activity) {
            return;
        }
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        activity.startActivityForResult(intent, REQUEST_CODE_USAGE_STATS);

    }
}
