package com.wma.library.model;

import android.graphics.drawable.Drawable;

import com.wma.library.base.BaseModule;

/**
 * Created by WMA on 2021/10/25.
 */
public class AppInfo extends BaseModule {
    private String appName;
    private String versionName;
    private Drawable appIcon;
    private boolean isSystemApp;
    private String[] permissions;

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

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
}
