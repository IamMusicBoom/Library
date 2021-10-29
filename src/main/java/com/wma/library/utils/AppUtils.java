package com.wma.library.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.wma.library.log.Logger;
import com.wma.library.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WMA on 2021/10/25.
 */
public class AppUtils {
    public static List<AppInfo> getInstallApps(PackageManager pm) {
        List<AppInfo> appInfos = new ArrayList<>();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);// 获取带权限的 list PackageManager.GET_PERMISSIONS
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            AppInfo appInfo = new AppInfo();
            boolean isSystemApp = false;
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                isSystemApp = true;
            }
            appInfo.setSystemApp(isSystemApp);
            appInfo.setPermissions(packageInfo.requestedPermissions);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

}
