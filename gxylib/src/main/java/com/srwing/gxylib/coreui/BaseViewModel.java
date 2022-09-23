package com.srwing.gxylib.coreui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.srwing.gxylib.coreui.mvvm.IViewModel;
import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.RxLifecycleAndroid;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public class BaseViewModel extends AndroidViewModel implements IViewModel, LifecycleProvider<ActivityEvent> {

    protected Application application;

    protected MutableLiveData<DataLoadStatus> dataLoadStatus = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public MutableLiveData<DataLoadStatus> getDataLoadStatus() {
        return dataLoadStatus;
    }

    public void postDataLoadStatus(DataLoadStatus status) {
        if (status == null) return;
        dataLoadStatus.postValue(status);
    }


    @Override
    public void onDestroy() {
        Log.e("viewModel", "onDestroy");
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
    }

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

}
