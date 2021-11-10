package com.wma.library.model;

import android.graphics.drawable.Drawable;

import com.wma.library.base.BaseApplication;
import com.wma.library.base.BaseModule;
import com.wma.library.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WMA on 2021/10/25.
 */
public class AppInfo extends BaseModule {
    private String packageName;
    private String appName;
    private String versionName;
    private Drawable appIcon;
    private boolean isSystemApp;
    private String[] totalPermissions;
    private List<String> dangerousPermissions = new ArrayList<>();
    private List<String> normalPermissions = new ArrayList<>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public String[] getTotalPermissions() {
        return totalPermissions;
    }

    public void setTotalPermissions(String[] totalPermissions) {
        if (totalPermissions == null) {
            this.totalPermissions = new String[]{};
            return;
        }
        this.totalPermissions = totalPermissions;
        for (String permission : this.totalPermissions) {
            if (PermissionUtils.isDangerousPermission(permission)) {
                this.dangerousPermissions.add(permission);
            } else {
                this.normalPermissions.add(permission);
            }
        }
    }

    public List<String> getDangerousPermissions() {
        return dangerousPermissions;
    }

    public void setDangerousPermissions(List<String> dangerousPermissions) {
        this.dangerousPermissions = dangerousPermissions;
    }

    public List<String> getNormalPermissions() {
        return normalPermissions;
    }

    public void setNormalPermissions(List<String> normalPermissions) {
        this.normalPermissions = normalPermissions;
    }
}
