package com.srwing.gxylib.livedatabus;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * Description:
 * Created by small small su
 * Date: 2022/2/7
 * Email: surao@foryou56.com
 */
public class LiveDataEventBus {
    private static final String TAG = LiveDataEventBus.class.getSimpleName();

    //发送event
    public static void postEvent(String event, Object o) {
        LiveEventBus.get(event).post(o);
    }

    //发送event
    public static void postEvent(String tag, String event, Object o) {
        LiveEventBus.get(tag).post(new BaseEventEntity(event, o));
    }

    //监听event
    public static void observeEvent(LifecycleOwner context, String event, EventAction eventAction) {
        LiveEventBus.get(event, Object.class)
                .observe(context, o -> {
                    if (o != null)
                        Log.d(TAG, "deal msg:" + o);
                    eventAction.deal(o);
                });
    }

    public static void observeEvent(LifecycleOwner context, String event, BaseEventAction eventAction) {
        LiveEventBus.get(event, BaseEventEntity.class)
                .observe(context, o -> {
                    if (o != null) {
                        Log.d(TAG, "deal msg TAG:" + o.TAG);
                    }
                    eventAction.deal(o.TAG, o);
                });
    }


    public interface EventAction {
        void deal(Object o);
    }

    public interface BaseEventAction {
        void deal(String TAG, BaseEventEntity o);
    }
}
