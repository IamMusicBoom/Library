package com.wma.library.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.ViewDataBinding;


import com.wma.library.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wma
 * on 2020/9/1 0001
 */
public abstract class BasePermissionActivity<B extends ViewDataBinding> extends BaseActivity<B> {

    private final int REQUEST_CODE_PERMISSION = 100;//权限申请
    private final int REQUEST_CODE_STORAGE = 102;//跳转setting开启存储权限的


    String permissions[] = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissions = getPermissions();
    }

    public abstract String[] getPermissions();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            String[] notGrantedPermissions = PermissionUtils.requestResultA(permissions, grantResults);
            if (notGrantedPermissions.length == 0) {
                onPermissionGranted();
            } else {
                boolean isNeverTips = false;
                for (int i = 0; i < notGrantedPermissions.length; i++) {
                    String notGrantedPermission = notGrantedPermissions[i];
                    isNeverTips = !ActivityCompat.shouldShowRequestPermissionRationale(this, notGrantedPermission);
                    if (isNeverTips) {
                        break;
                    }
                }
                onPermissionDenied(notGrantedPermissions, isNeverTips);
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (PermissionUtils.isStoragePermissionGranted()) {
                if (isPermissionAllGranted()) {
                    onPermissionGranted();
                } else {
                    onPermissionDenied(permissions, false);
                }
            } else {
                onPermissionDenied(permissions, false);
            }
        }
    }

    public void requestPermissions() {
        if (PermissionUtils.isContainsStorage(permissions)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_STORAGE);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, REQUEST_CODE_PERMISSION);
                }
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_CODE_PERMISSION);
            }
        }
    }

    /**
     * 所有权限都被允许了吗？
     *
     * @return
     */
    public boolean isPermissionAllGranted() {
        if (PermissionUtils.isContainsStorage(permissions)) {// 如果有存储权限，需要判断 Android 版本
            if (PermissionUtils.isStoragePermissionGranted()) {
                List<String> tempPermissionList = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    tempPermissionList.add(permissions[i]);
                }
                if (tempPermissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    tempPermissionList.remove(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                if (tempPermissionList.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    tempPermissionList.remove(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                permissions = tempPermissionList.toArray(new String[0]);
                return PermissionUtils.isAllPermissionGranted(permissions);
            } else {
                return false;
            }
        } else {
            return PermissionUtils.isAllPermissionGranted(permissions);
        }
    }


    public abstract void onPermissionGranted();

    public abstract void onPermissionDenied(String[] permissions, boolean isNeverTips);


}
