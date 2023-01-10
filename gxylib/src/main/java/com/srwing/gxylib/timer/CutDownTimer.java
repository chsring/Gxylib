package com.srwing.gxylib.timer;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:可暂停的倒计时
 * Created srwing
 * Date: 2023/1/10
 * Email: 694177407@qq.com
 */
public class CutDownTimer implements ITimerSupport, LifecycleObserver {

    private String TAG = CutDownTimer.class.getSimpleName();
    private Timer mTimer;

    private Handler mHandler;

    /**
     * 倒计时时间
     */
    private final long fromMillis;

    private final long toMillis;

    /**
     * 间隔时间
     */
    private final long interval;
    /**
     * 倒计时剩余时间
     */
    private long mMillisUntilFinished;

    private OnCountDownTimerListener mOnCountDownTimerListener;

    private TimerState mTimerState = TimerState.FINISH;

    /**
     * 倒计时范围区间：[fromMillis, toMillis]
     */
    public CutDownTimer(LifecycleOwner owner, long fromMillis, long toMillis, long interval) {
        this.fromMillis = fromMillis;
        this.toMillis = toMillis;
        this.mMillisUntilFinished = fromMillis;
        this.interval = interval;
        this.mHandler = new Handler(Looper.myLooper());
        if (owner != null) {
            owner.getLifecycle().addObserver(this);
        }
    }

    //start 对应 stop
    @Override
    public void start() {
        //防止重复启动 重新启动要先reset再start
        if (mTimer == null && mTimerState != TimerState.START) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(createTimerTask(), 0, interval);
            mTimerState = TimerState.START;
        }
    }

    //暂停 pause 对应 resume
    @Override
    public void pause() {
        if (mTimer != null && mTimerState == TimerState.START) {
            cancelTimer();
            mTimerState = TimerState.PAUSE;
        }
    }

    @Override
    public void resume() {
        if (mTimerState == TimerState.PAUSE) {
            start();
        }
    }

    //停止
    @Override
    public void stop() {
        stopTimer(true);
    }

    @Override
    public void reset() {
        if (mTimer != null) {
            cancelTimer();
        }
        mMillisUntilFinished = fromMillis;
        mTimerState = TimerState.FINISH;
    }

    private void stopTimer(final boolean cancel) {
        if (mTimer != null) {
            cancelTimer();
            mMillisUntilFinished = fromMillis;
            mTimerState = TimerState.FINISH;

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mOnCountDownTimerListener != null) {
                        if (cancel) {
                            mOnCountDownTimerListener.onCancel();
                        } else {
                            mOnCountDownTimerListener.onFinish();
                        }
                    }
                }
            });
        }
    }

    private void cancelTimer() {
        mTimer.cancel();
        mTimer.purge();
        mTimer = null;
    }

    public boolean isStart() {
        return mTimerState == TimerState.START;
    }

    public boolean isFinish() {
        return mTimerState == TimerState.FINISH;
    }


    public void setOnCountDownTimerListener(OnCountDownTimerListener listener) {
        this.mOnCountDownTimerListener = listener;
    }

    public long getMillisUntilFinished() {
        return mMillisUntilFinished;
    }

    public TimerState getTimerState() {
        return mTimerState;
    }

    protected TimerTask createTimerTask() {
        return new TimerTask() {
            private long startTime = -1;

            @Override
            public void run() {
                if (startTime < 0) {
                    //第一次回调 记录开始时间

                    startTime = scheduledExecutionTime() - (fromMillis - mMillisUntilFinished);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnCountDownTimerListener != null) {
                                Log.d(TAG, "run onTick startTime： " + mMillisUntilFinished);
                                mOnCountDownTimerListener.onTick(mMillisUntilFinished);
                            }
                        }
                    });
                } else {
                    //剩余时间
                    mMillisUntilFinished = fromMillis - (scheduledExecutionTime() - startTime);
                    Log.d(TAG, "run mMillisUntilFinished: " + mMillisUntilFinished);
                    if (mMillisUntilFinished < toMillis) {
                        //如果没有剩余时间 就停止
                        stopTimer(false);
                        return;
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnCountDownTimerListener != null) {
                                Log.d(TAG, "run onTick mMillisUntilFinished： " + mMillisUntilFinished);
                                mOnCountDownTimerListener.onTick(mMillisUntilFinished);
                            }
                        }
                    });

                }
            }
        };
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        stopTimer(true);
    }
}

