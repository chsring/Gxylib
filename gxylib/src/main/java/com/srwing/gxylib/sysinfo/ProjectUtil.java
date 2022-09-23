package com.srwing.gxylib.sysinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author zxl
 * @data 2020/7/30
 * @function 获取项目本身信息的工具类
 */
public class ProjectUtil {

    public static String getUserAgent(Context context) {
        return "Golaxy Android Client/" + ProjectUtil.getVersionName(context) +
                " OkHttp/3.8.1 " + PhoneInfoUtil.getDeviceBrand() +
                " Android" + PhoneInfoUtil.getSystemVersion() +
                " " + PhoneInfoUtil.getSystemLanguage() +
                " " + PhoneInfoUtil.getSystemModel();
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        }  catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取图标 bitmap
     *
     * @param context
     */
    public static synchronized Bitmap getBitmap(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext()
                    .getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    public static boolean isNewVersion(String newVersion, String localVersion) {
        boolean isNewVersion = false;
        String[] newVersionList = newVersion.split("\\.");
        String[] localVersionList = localVersion.split("\\.");

        if (newVersionList.length == localVersionList.length) {
            for (int i = 0; i < newVersionList.length; i++) {
                if (Integer.parseInt(newVersionList[i]) > Integer.parseInt(localVersionList[i])) {
                    isNewVersion = true;
                    break;
                } else if (Integer.parseInt(newVersionList[i]) < Integer.parseInt(localVersionList[i])) {
                    break;
                }
            }
        }
        return isNewVersion;
    }

}
