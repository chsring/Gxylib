package com.srwing.gxylib.handler;

import com.trello.rxlifecycle4.LifecycleProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @author srwing
 * @fileName RxHandler
 * @time 2022/6/20 4:00 下午
 * @Email 694177407@qq.com (有问题请邮件)
 * @desc 代替Handler 解决内存泄漏，或者弱引用被强杀的问题
 */
public class RxHandler {
    private Disposable subscription;

    private Map<Integer, Disposable> bus = new HashMap<>(32);

    public RxHandler() {
    }

    /**
     * 延时发送消息的方法
     *
     * @param what        发送的消息
     * @param delayMillis 需要的延时，毫秒
     */
    public <T> void sendMsgDelayed(LifecycleProvider<T> rxLifeCycle, int what, long delayMillis, HandRxMsg handRxMsg) {
        //如果 subscription 不为空，说明已经启动一个 timer 无需再次启动timer
        if ((subscription = bus.get(what)) != null) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.timer(delayMillis, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindToLifecycle());
        }
        subscription = tObservable.doOnComplete(() -> {
            handRxMsg.handMsg(what);
            if ((subscription = bus.get(what)) != null) {
                subscription.dispose();
                bus.remove(what);
                subscription = null;
            }
        }).subscribe();
        bus.put(what, subscription);
    }

    /**
     *
     * @param rxLifeCycle rxjava的 LifecycleProvider
     * @param event 生命周期事件
     * @param what 消息号
     * @param delayMillis 延迟多少秒
     * @param handRxMsg 处理事件的回调
     * @param <T> ActivityEvent 或者 FragmentEvent
     */
    public <T> void sendMsgDelayed(LifecycleProvider<T> rxLifeCycle,T event, int what, long delayMillis, HandRxMsg handRxMsg) {
        //如果 subscription 不为空，说明已经启动一个 timer 无需再次启动timer
        if ((subscription = bus.get(what)) != null) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.timer(delayMillis, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread());
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindUntilEvent(event));
        }
        subscription = tObservable.doOnComplete(() -> {
            handRxMsg.handMsg(what);
            if ((subscription = bus.get(what)) != null) {
                subscription.dispose();
                bus.remove(what);
                subscription = null;
            }
        }).subscribe();
        bus.put(what, subscription);
    }

    //循环发送 有执行次数
    public <T> void sendMsgCycle(LifecycleProvider<T> rxLifeCycle, int what, int timeCount, int period, int delay, HandRxMsg handRxMsg) {
        if ((subscription = bus.get(what)) != null && !subscription.isDisposed()) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.interval(delay, period, TimeUnit.MILLISECONDS).take(timeCount); //执行多少次
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindToLifecycle());
        }
        subscription = tObservable //.doOnNext(o -> handRxMsg.handMsg(what))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> handRxMsg.handMsg(what));
        bus.put(what, subscription);
    }

    public <T> void sendMsgCycle(LifecycleProvider<T> rxLifeCycle,T event, int what, int timeCount, int period, int delay, HandRxMsg handRxMsg) {
        if ((subscription = bus.get(what)) != null && !subscription.isDisposed()) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.interval(delay, period, TimeUnit.MILLISECONDS).take(timeCount); //执行多少次
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindUntilEvent(event));
        }
        subscription = tObservable //.doOnNext(o -> handRxMsg.handMsg(what))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> handRxMsg.handMsg(what));
        bus.put(what, subscription);
    }

    //循环发送 无限循环 没有执行次数  注意 delay 小于1500 毫秒可能不会执行
    public <T> void sendMsgCycle(LifecycleProvider<T> rxLifeCycle, int what, long period, long delay, HandRxMsg handRxMsg) {
        if ((subscription = bus.get(what)) != null && !subscription.isDisposed()) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.interval(delay, period, TimeUnit.MILLISECONDS);
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindToLifecycle());
        }
        //循环用onNext，执行一次 用complete
        subscription = tObservable.doOnNext(o -> handRxMsg.handMsg(what)).observeOn(AndroidSchedulers.mainThread()).subscribe();
        bus.put(what, subscription);
    }

    public <T> void sendMsgCycle(LifecycleProvider<T> rxLifeCycle, T event,int what, long period, long delay, HandRxMsg handRxMsg) {
        if ((subscription = bus.get(what)) != null && !subscription.isDisposed()) {
            subscription.dispose();
            bus.remove(what);
        }
        Observable<Long> tObservable = Observable.interval(delay, period, TimeUnit.MILLISECONDS);
        if (null != rxLifeCycle) {
            tObservable = tObservable.compose(rxLifeCycle.bindUntilEvent(event));
        }
        //循环用onNext，执行一次 用complete
        subscription = tObservable.doOnNext(o -> handRxMsg.handMsg(what)).observeOn(AndroidSchedulers.mainThread()).subscribe();
        bus.put(what, subscription);
    }

    /**
     * 解除订阅
     */
    public void removeMessage(int what) {
        if (bus.containsKey(what)) {
            if ((subscription = bus.get(what)) != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            bus.remove(what);
            subscription = null;
        }
    }

    /**
     * 解除订阅
     */
    public void removeAllMessage() {
        Iterator<Map.Entry<Integer, Disposable>> iter = bus.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Disposable> entry = iter.next();
            subscription = entry.getValue();
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            iter.remove();
        }
        subscription = null;
    }

    public interface HandRxMsg {
        void handMsg(int what);
    }
}
