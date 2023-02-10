package com.srwing.gxylib.launch;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Description:
 * Created by srwing
 * Date: 2022/8/2
 * Email: 694177407@qq.com
 */
public class StartActivityUtil {
    protected static final String TAG = "StartActivityUtil";
    private IFragmentProxy fragmentProxy;
    private Intent intent;

    /**
     * 无result 有传入参数
     */
//    public static void startActivity(Activity activity, Class<?> clazz,
//                                     Map<String, String> param) {
//        new StartActivityUtil(activity, clazz).startActivity(param);
//    }
    public static <V> void startActivity(Activity activity, Class<?> clazz,
                                         String key, V value) {
        Map<String, V> param = new WeakHashMap<>();
        param.put(key, value);
        new StartActivityUtil(activity, clazz).startActivity(param);
    }

    public static <T> void startActivity(Activity activity, Class<?> clazz,
                                         Map<String, T> param) {
        new StartActivityUtil(activity, clazz).startActivity(param);
    }

    /**
     * 无result 无参数
     *
     * @param activity
     * @param clazz
     */
    public static void startActivity(Activity activity, Class<?> clazz) {
        new StartActivityUtil(activity, clazz).startActivity(null);
    }


    //   -------添加错误回调，为了兼容之前的，所以新加接口和函数

    /**
     * rx方法 有result 有参数
     *
     * @param activity
     * @param clazz
     * @param param
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Class<?> clazz,
                                           Map<String, String> param, final Callback2 callback) {
        new StartActivityUtil(activity, clazz).startForResult(param)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                        callback.onResultError(throwable.getMessage());
                    }
                });
    }


    /**
     * rx方法 有result 无参数
     *
     * @param activity
     * @param clazz
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Class<?> clazz, final Callback2 callback) {
        new StartActivityUtil(activity, clazz).startForResult(null)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                        callback.onResultError(throwable.getMessage());
                    }
                });
    }

    /**
     * rx方法 有result 传入intent
     *
     * @param activity
     * @param clazz
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Intent intent, final Callback2 callback) {
        new StartActivityUtil(activity, intent).startForResult(null)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                        callback.onResultError(throwable.getMessage());
                    }
                });
    }


    //   -------添加错误回调，结束


    /**
     * rx方法 有result 有参数
     *
     * @param activity
     * @param clazz
     * @param param
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Class<?> clazz,
                                           Map<String, String> param, final Callback callback) {
        new StartActivityUtil(activity, clazz).startForResult(param)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * rx方法 有result 无参数
     *
     * @param activity
     * @param clazz
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Class<?> clazz, final Callback callback) {
        new StartActivityUtil(activity, clazz).startForResult(null)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * rx方法 有result 传入intent
     *
     * @param activity
     * @param clazz
     * @param callback
     */
    @SuppressWarnings("ALL")
    public static void startRxActForResult(Activity activity, Intent intent, final Callback callback) {
        new StartActivityUtil(activity, intent).startForResult(null)
                .subscribe(new Consumer<ActivityResultInfo>() {
                    @Override
                    public void accept(ActivityResultInfo activityResultInfo) throws Exception {
                        callback.onActivityResult(activityResultInfo);
                        return;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    /**
     * callback方法 有result 有param
     *
     * @param activity
     * @param clazz
     * @param param
     * @param callback
     */
    public static void startActivityForResult(Activity activity, Class<?> clazz,
                                              Map<String, String> param, final Callback callback) {
        new StartActivityUtil(activity, clazz).startForResult(param, callback);
    }


    /**
     * callback方法 无result 无param
     *
     * @param activity
     * @param clazz
     * @param callback
     */
    public static void startActivityForResult(Activity activity, Class<?> clazz, final Callback callback) {
        new StartActivityUtil(activity, clazz).startForResult(null, callback);
    }

    /**
     * activity 构造方法
     *
     * @param activity
     * @param cls
     */
    private StartActivityUtil(Activity activity, Class<?> cls) {
        intent = new Intent(activity, cls);
        fragmentProxy = getAvoidOnResultFragment(activity);
    }


    /**
     * @param activity
     * @param intent
     */
    private StartActivityUtil(Activity activity, Intent intent) {
        this.intent = intent;
        fragmentProxy = getAvoidOnResultFragment(activity);
    }

    /**
     * fragment 构造方法
     */
    private StartActivityUtil(Fragment fragment, Class<?> cls) {
        this(fragment.getActivity(), cls);
    }

    private IFragmentProxy getAvoidOnResultFragment(Activity activity) {
        IFragmentProxy avoidOnResultFragment;
        if (activity instanceof FragmentActivity) {
            avoidOnResultFragment = (StartOnResultFragmentSurport) ((FragmentActivity) activity).getSupportFragmentManager().findFragmentByTag(TAG);
            if (avoidOnResultFragment == null) {
                avoidOnResultFragment = new StartOnResultFragmentSurport();
                FragmentManager fragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
                try {
                    fragmentManager
                            .beginTransaction()
                            .add((Fragment) avoidOnResultFragment, TAG)
                            .commitAllowingStateLoss();
                    fragmentManager.executePendingTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                    avoidOnResultFragment = null;
                }
            }
        } else {
            avoidOnResultFragment = (StartOnResultFragment) activity.getFragmentManager().findFragmentByTag(TAG);
            if (avoidOnResultFragment == null) {
                avoidOnResultFragment = new StartOnResultFragment();
                android.app.FragmentManager fragmentManager = activity.getFragmentManager();
                try {
                    fragmentManager
                            .beginTransaction()
                            .add((android.app.Fragment) avoidOnResultFragment, TAG)
                            .commitAllowingStateLoss();
                    fragmentManager.executePendingTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                    avoidOnResultFragment = null;
                }
            }
        }
        return avoidOnResultFragment;
    }


    /**
     * rx方法
     *
     * @param params
     * @return
     */
    private Observable<ActivityResultInfo> startForResult(Map<String, String> params) {
        if (null != params && params.size() > 0)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        if (null == fragmentProxy) {
            return Observable.error(new Throwable("参数异常"));
        }
        return fragmentProxy.startForResult(intent);
    }


    /**
     * callback方法
     *
     * @param params
     * @param callback
     */
    private void startForResult(Map<String, String> params, Callback callback) {
        if (null != params && params.size() > 0)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        fragmentProxy.startForResult(intent, callback);
    }

    /**
     * 无callback
     *
     * @param params
     */
    private <T> void startActivity(Map<String, T> params) {
        if (null != params && params.size() > 0) {
            for (Map.Entry<String, T> entry : params.entrySet()) {
                if (entry.getValue() instanceof Integer) {
                    intent.putExtra(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof String) {
                    intent.putExtra(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Serializable) {
                    intent.putExtra(entry.getKey(), (Serializable) entry.getValue());
                }
            }
        }
        fragmentProxy.startActivityNoResult(intent);
    }

    /**
     * 回调接口
     */
    public interface Callback {
        void onActivityResult(ActivityResultInfo activityResultInfo);
    }


    /**
     * 回调接口
     */
    public interface Callback2 {
        void onActivityResult(ActivityResultInfo activityResultInfo);

        void onResultError(String error);
    }

    /**
     * 回调接口
     */
    public interface ImageCallback {
        void onImagePathResult(String imagePath);
    }
}
