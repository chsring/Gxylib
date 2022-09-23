package com.srwing.gxylib;

import android.Manifest;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/17
 * Email: 694177407@qq.com
 */
public class PermissionUtils {

    private PermissionUtils() {

    }

    private static class Holder {
        private static final PermissionUtils it = new PermissionUtils();
    }

    public static PermissionUtils get() {
        return Holder.it;
    }

    // 判断是否授予 Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE  权限
    public boolean isGrantedNetPhone() {
        String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
        return com.blankj.utilcode.util.PermissionUtils.isGranted(permissions);
    }
}
