package com.srwing.gxylib.coreui.mvvm;

import android.app.Application;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.android.RxLifecycleAndroid;

import java.lang.reflect.GenericSignatureFormatError;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Description: 此BaseLifeViewModel 与Activity 的生命周期一致
 * 在调用网络请求的时候 直接传this就能绑定网络请求框架的生命周期
 * T 可以传ActivityEvent，可以传FragmentEvent
 * Created srwing
 * Date: 2023/2/13
 * Email: 694177407@qq.com
 */
public class BaseLifeViewModel<T> extends BaseViewModel implements LifecycleProvider<T> {

    protected Application application;
    private final Class<?> modelClass;


    public BaseLifeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.modelClass = getClassFromType();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (modelClass != null) {
            if (modelClass.isAssignableFrom(ActivityEvent.class)) {
                lifecycleSubject.onNext((T) ActivityEvent.DESTROY);
            } else if (modelClass.isAssignableFrom(FragmentEvent.class)) {
                lifecycleSubject.onNext((T) FragmentEvent.DESTROY);
            }
        }
    }

    private Class<?> getClassFromType() {
        try {
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                if (types.length == 1) {
                    return (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                }
            }
        } catch (GenericSignatureFormatError | TypeNotPresentException |
                 MalformedParameterizedTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final BehaviorSubject<T> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<T> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <O> LifecycleTransformer<O> bindUntilEvent(@NonNull T event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <O> LifecycleTransformer<O> bindToLifecycle() {
        if (modelClass != null) {
            if (modelClass.isAssignableFrom(ActivityEvent.class)) {
                return RxLifecycleAndroid.bindActivity((Observable<ActivityEvent>) lifecycleSubject);
            } else if (modelClass.isAssignableFrom(FragmentEvent.class)) {
                return RxLifecycleAndroid.bindFragment((Observable<FragmentEvent>) lifecycleSubject);
            }
        }
        return RxLifecycleAndroid.bindActivity((Observable<ActivityEvent>) lifecycleSubject);
    }
}
