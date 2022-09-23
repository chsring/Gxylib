package com.srwing.gxylib.coreui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/25
 * Email: 694177407@qq.com
 */
public class GxyService extends Service {

    private final String TAG = GxyService.class.getSimpleName();
    private final static int GRAY_SERVICE_ID = 0; //无通知样式的id

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "deal onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//26
            try {
                Log.i(TAG, "deal onCreate - startService begin class =" + this.getClass().getSimpleName());
                startForeground(GRAY_SERVICE_ID, createChannelNotification(this));
                Log.i(TAG, "deal onCreate - startService success class =" + this.getClass().getSimpleName());
            } catch (Exception e) {
                Log.i(TAG, "deal onCreate - startService occur exception" + e.getMessage());
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "deal onStartCommand");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//26
            try {
                Log.i(TAG, "deal onStartCommand - startService begin class =" + this.getClass().getSimpleName());
                startForeground(GRAY_SERVICE_ID, createChannelNotification(this));
                Log.i(TAG, "deal onStartCommand - startService success class =" + this.getClass().getSimpleName());
            } catch (Exception e) {
                Log.i(TAG, "deal onStartCommand - startService occur exception" + e.getMessage());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "deal onBind class =" + this.getClass().getSimpleName());
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "deal onUnbind class =" + this.getClass().getSimpleName());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "deal onDestroy class =" + this.getClass().getSimpleName());
    }

    /**
     * 支持8.0以上系统需要
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification createChannelNotification(Context context) {
        NotificationChannel channel = new NotificationChannel("101", "golaxy", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        return new Notification.Builder(context.getApplicationContext(), "101").build();

    }
}
