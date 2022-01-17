package com.wma.library.utils;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.wma.library.base.BaseApplication;
import com.wma.library.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WMA on 2021/10/25.
 */
public class AppUtils {
    public static final int TYPE_SYSTEM_APP = 0;
    public static final int TYPE_USER_APP = 1;
    public static final int TYPE_ALL_APP = 2;

    public static List<AppInfo> getInstallApps(PackageManager pm, int type) {
        List<AppInfo> appInfos = new ArrayList<>();
        List<PackageInfo> installedPackages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);// 获取带权限的 list PackageManager.GET_PERMISSIONS
        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            //排除
            if (BaseApplication.getContext().getPackageName().equals(packageInfo.packageName)) {
                continue;
            }
            // 只读取Launch中的app
            Intent launchIntent = pm.getLaunchIntentForPackage(packageInfo.packageName);
            if (launchIntent == null) {
                continue;
            }
            AppInfo appInfo = new AppInfo();
            boolean isSystemApp = false;
            appInfo.setPackageName(packageInfo.applicationInfo.packageName);
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfo.setVersionName(packageInfo.versionName);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                isSystemApp = true;
            }
            appInfo.setSystemApp(isSystemApp);
            appInfo.setTotalPermissions(packageInfo.requestedPermissions);
            if (type == TYPE_SYSTEM_APP) {
                if (appInfo.isSystemApp()) {
                    appInfos.add(appInfo);
                }
            } else if (type == TYPE_USER_APP) {
                if (!appInfo.isSystemApp()) {
                    appInfos.add(appInfo);
                }
            } else {
                appInfos.add(appInfo);
            }
        }
        return appInfos;
    }


    public static AppInfo getAppInfoByPackageName(PackageManager pm, String packageName) {
        AppInfo appInfo = new AppInfo();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            boolean isSystemApp = false;
            appInfo.setPackageName(packageInfo.applicationInfo.packageName);
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfo.setVersionName(packageInfo.versionName);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                isSystemApp = true;
            }
            appInfo.setSystemApp(isSystemApp);
            appInfo.setTotalPermissions(packageInfo.requestedPermissions);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return appInfo;
        }
        return appInfo;
    }

    /**
     * 跳转至 app 管理界面
     *
     * @param packageName
     */
    public static void goManageActivity(String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getContext().startActivity(intent);
    }

    /**
     * 打开应用
     *
     * @param packageName
     */
    public static void goApp(String packageName) {
        Intent intent = null;
        intent = BaseApplication.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            ToastUtils.showToast(BaseApplication.getContext(), "未安装该应用");
        } else {
            BaseApplication.getContext().startActivity(intent);
        }
    }

    /**
     * 判断是否可以打开应用
     *
     * @param packageName
     * @return
     */
    public static boolean canGoApp(String packageName) {
        return BaseApplication.getContext().getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    /**
     * 卸载应用，需要权限
     * <uses-permission android:name="android.permission.REQUEST_DELETE_PACKAGES" />
     *
     * @param packageName
     */
    public static void unInstallApp(String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getContext().startActivity(intent);
    }

    public static ComponentName getMoveToFgComponent(Context context) {
        UsageStatsManager usageManager = (UsageStatsManager) context.getApplicationContext().getSystemService(Context.USAGE_STATS_SERVICE);;
        long now = System.currentTimeMillis();
        UsageEvents events = usageManager.queryEvents(now - 1000, now);
        String pkgName = null;
        String clsName = null;
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);
            if (e.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                pkgName = e.getPackageName();
                clsName = e.getClassName();
            }
        }

        ComponentName comp = null;
        if (pkgName != null && clsName != null) {
            comp = new ComponentName(pkgName, clsName);
        }
        return comp;
    }
}
