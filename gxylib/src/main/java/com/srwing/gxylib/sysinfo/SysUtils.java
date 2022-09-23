package com.srwing.gxylib.sysinfo;

import static android.content.Context.TELEPHONY_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.orhanobut.logger.Logger;

import java.util.UUID;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/17
 * Email: 694177407@qq.com
 */
public class SysUtils {
    public static final String TAG = SysUtils.class.getSimpleName();

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetwork(Context context) {
        String strNetworkType = "23";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "4";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "0";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "1";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    case 19:   // Current network is LTE_CA {@hide} NETWORK_TYPE_LTE_CA  这个版本sdk已经隐藏 或许下个版本就会放出，所以用19代替
                        strNetworkType = "2";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "1";
                        } else {
                            strNetworkType = "23";
                        }

                        break;
                }

                Logger.t(TAG).e("Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }

        Logger.t(TAG).e("Network Type : " + strNetworkType);

        return strNetworkType;
    }

    /**
     * 返回运营商 需要加入权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     *
     * @return 0，代表中国联通， 1, 代表中国移动,  2，代表中国电信，  -1，代表未知
     * @author srwing
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getOperators(Context context) {
        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）（也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        String OperatorsName = "23";
        String IMSI = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        if (TextUtils.isEmpty(IMSI))
            return OperatorsName;
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = "1";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = "0";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = "2";
        }
        return OperatorsName;
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE://api<11 : replace by 13
                    case 19:   // Current network is LTE_CA {@hide} NETWORK_TYPE_LTE_CA  这个版本sdk已经隐藏 或许下个版本就会放出，所以用19代替
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = "23";
                        }

                        break;
                }

                Logger.t(TAG).e("Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }

        Logger.t(TAG).e("Network Type : " + strNetworkType);

        return strNetworkType;
    }

    @SuppressLint("HardwareIds")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getNotNullDeviceId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences("device_id.xml", 0);
        final String id = prefs.getString("device_id", null);
        String imei = "";
        if (id != null) {
            // Use the ids previously computed and stored in the prefs file
            imei = id;
        } else {

            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                if (tm != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        imei = tm.getImei() != null ? tm.getImei() : (tm.getMeid() != null ? tm.getMeid() :
                                UUID.randomUUID().toString());
                    } else {
                        imei = tm.getDeviceId() != null ? tm.getDeviceId() : UUID.randomUUID().toString();
                    }
                } else {
                    imei = UUID.randomUUID().toString();
                }
            } catch (Exception e) {
                imei = UUID.randomUUID().toString();
//                throw new RuntimeException(e);
            }

            // Write the value out to the prefs file
            prefs.edit().putString("device_id", imei).commit();
        }

        return imei;

    }
}
