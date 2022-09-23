package com.srwing.gxylib.livedatabus;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

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
        LiveDataBus.get().with(event).postValue(o);
    }

    //发送event
    public static void postEvent(String tag, String event, Object o) {
        LiveDataBus.get().with(tag).postValue(new BaseEventEntity(event, o));
    }

    //监听event
    public static void observeEnent(LifecycleOwner context, String event, EventAction eventAction) {
        LiveDataBus.get().with(event, Object.class)
                .observe(context, o -> {
                    if (o != null)
                        Log.d(TAG, "deal msg:" + o);
                    eventAction.deal(o);
                });
    }

    public static void observeEnent(LifecycleOwner context, String event, BaseEventActivon eventAction) {
        LiveDataBus.get().with(event, BaseEventEntity.class)
                .observe(context, o -> {
                    if (o != null) {
                        Log.d(TAG, "deal msg TAG:" + o.TAG);
                    }
                    eventAction.deal(o.TAG, o);
                });
    }

//
//    public static <O> void observeEnent(LifecycleOwner context, String event,BaseEventEntity<O> type, EventAction eventAction) {
//        LiveDataBus.get().with(event, type)
//                .observe(context, new Observer<T>() {
//                    @Override
//                    public void onChanged(T t) {
//
//                    }
//                });
//    }


    public interface EventAction {
        void deal(Object o);
    }

    public interface BaseEventActivon {
        void deal(String TAG, BaseEventEntity o);
    }
}
