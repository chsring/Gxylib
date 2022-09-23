package com.srwing.gxylib.launch;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Description:app包下的Fragment，已被弃用，后续会逐渐淘汰
 * Created by srwing
 * Date: 2022/8/2
 * Email: 694177407@qq.com
 */
public class StartOnResultFragment extends Fragment implements IFragmentProxy {
    private Map<Integer, PublishSubject<ActivityResultInfo>> mSubjects = new HashMap<>();
    private Map<Integer, StartActivityUtil.Callback> mCallbacks = new HashMap<>();

    public StartOnResultFragment() {
    }

    public Observable<ActivityResultInfo> startForResult(final Intent intent) {
        final PublishSubject<ActivityResultInfo> subject = PublishSubject.create();
        return subject.doOnSubscribe(disposable -> {
            mSubjects.put(subject.hashCode(), subject);
            startActivityForResult(intent, subject.hashCode());
        });
    }

    public void startForResult(Intent intent, StartActivityUtil.Callback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        startActivityForResult(intent, callback.hashCode());
    }

    public void startActivityNoResult(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rxjava方式的处理
        PublishSubject<ActivityResultInfo> subject = mSubjects.remove(requestCode);
        if (subject != null) {
            subject.onNext(new ActivityResultInfo(resultCode, data));
            subject.onComplete();
        }

        //callback方式的处理
        StartActivityUtil.Callback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(new ActivityResultInfo(resultCode, data));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i(StartActivityUtil.TAG, "fragment-life-onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(StartActivityUtil.TAG, "fragment-life-onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(StartActivityUtil.TAG, "fragment-life-onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(StartActivityUtil.TAG, "fragment-life-onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(StartActivityUtil.TAG, "fragment-life-onDetach");
        super.onDetach();
    }
}
