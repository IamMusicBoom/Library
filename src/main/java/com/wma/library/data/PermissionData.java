package com.wma.library.data;

import android.Manifest;

import com.wma.library.R;
import com.wma.library.base.BaseApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WMA on 2022/1/10.
 *
 */
public class PermissionData {
    public static Map<String, String> permissionMap = new HashMap<>();

    static {
        permissionMap.put(Manifest.permission.CAMERA, BaseApplication.getContext().getString(R.string.txt_permission_CAMERA));
        permissionMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, BaseApplication.getContext().getString(R.string.txt_permission_WRITE_EXTERNAL_STORAGE));
        permissionMap.put(Manifest.permission.READ_EXTERNAL_STORAGE, BaseApplication.getContext().getString(R.string.txt_permission_READ_EXTERNAL_STORAGE));
        permissionMap.put(Manifest.permission.READ_CALENDAR, BaseApplication.getContext().getString(R.string.txt_permission_READ_CALENDAR));
        permissionMap.put(Manifest.permission.WRITE_CALENDAR, BaseApplication.getContext().getString(R.string.txt_permission_WRITE_CALENDAR));
        permissionMap.put(Manifest.permission.READ_CONTACTS, BaseApplication.getContext().getString(R.string.txt_permission_READ_CONTACTS));
        permissionMap.put(Manifest.permission.WRITE_CONTACTS, BaseApplication.getContext().getString(R.string.txt_permission_WRITE_CONTACTS));
        permissionMap.put(Manifest.permission.GET_ACCOUNTS, BaseApplication.getContext().getString(R.string.txt_permission_GET_ACCOUNTS));
        permissionMap.put(Manifest.permission.ACCESS_FINE_LOCATION, BaseApplication.getContext().getString(R.string.txt_permission_ACCESS_FINE_LOCATION));
        permissionMap.put(Manifest.permission.ACCESS_COARSE_LOCATION, BaseApplication.getContext().getString(R.string.txt_permission_ACCESS_COARSE_LOCATION));
        permissionMap.put(Manifest.permission.RECORD_AUDIO, BaseApplication.getContext().getString(R.string.txt_permission_RECORD_AUDIO));
        permissionMap.put(Manifest.permission.READ_PHONE_STATE, BaseApplication.getContext().getString(R.string.txt_permission_READ_PHONE_STATE));
        permissionMap.put(Manifest.permission.CALL_PHONE, BaseApplication.getContext().getString(R.string.txt_permission_CALL_PHONE));
        permissionMap.put(Manifest.permission.READ_CALL_LOG, BaseApplication.getContext().getString(R.string.txt_permission_READ_CALL_LOG));
        permissionMap.put(Manifest.permission.WRITE_CALL_LOG, BaseApplication.getContext().getString(R.string.txt_permission_WRITE_CALL_LOG));
        permissionMap.put(Manifest.permission.ADD_VOICEMAIL, BaseApplication.getContext().getString(R.string.txt_permission_ADD_VOICEMAIL));
        permissionMap.put(Manifest.permission.USE_SIP, BaseApplication.getContext().getString(R.string.txt_permission_USE_SIP));
        permissionMap.put(Manifest.permission.PROCESS_OUTGOING_CALLS, BaseApplication.getContext().getString(R.string.txt_permission_PROCESS_OUTGOING_CALLS));
        permissionMap.put(Manifest.permission.BODY_SENSORS, BaseApplication.getContext().getString(R.string.txt_permission_BODY_SENSORS));
        permissionMap.put(Manifest.permission.SEND_SMS, BaseApplication.getContext().getString(R.string.txt_permission_SEND_SMS));
        permissionMap.put(Manifest.permission.RECEIVE_SMS, BaseApplication.getContext().getString(R.string.txt_permission_RECEIVE_SMS));
        permissionMap.put(Manifest.permission.READ_SMS, BaseApplication.getContext().getString(R.string.txt_permission_READ_SMS));
        permissionMap.put(Manifest.permission.RECEIVE_WAP_PUSH, BaseApplication.getContext().getString(R.string.txt_permission_RECEIVE_WAP_PUSH));
        permissionMap.put(Manifest.permission.RECEIVE_MMS, BaseApplication.getContext().getString(R.string.txt_permission_RECEIVE_MMS));
        permissionMap.put(Manifest.permission.SYSTEM_ALERT_WINDOW, BaseApplication.getContext().getString(R.string.txt_permission_SYSTEM_ALERT_WINDOW));
    }

    public static String getPermissionName(String permission) {
        return permissionMap.get(permission);
    }
}
