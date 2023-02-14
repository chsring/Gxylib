package com.example.gxylib.main.m;

import com.srwing.t_network.GxyNet;
import com.srwing.t_network.base.BaseDataSource;
import com.srwing.t_network.callback.ISuccess;
import com.srwing.t_network.http.IMethod;
import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

public class MainRemoteDataSource implements MainDataSource {

    @Override
    public void getMainActivity(LifecycleProvider<ActivityEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback) {
        GxyNet.builder()
                .service(MainService.class)
                .bindLifeCycle(provider.bindToLifecycle())
                .method((IMethod<MainService>) MainService::getMain)
                .success((ISuccess<MainEntity>) callback::onTaskLoaded)
                .failure((callback::onDataNotAvailable))
                .build()
                .excute();
    }

    @Override
    public void getMainFragment(LifecycleProvider<FragmentEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback) {
        GxyNet.builder()
                .service(MainService.class)
                .bindLifeCycle(provider.bindToLifecycle())
                .method((IMethod<MainService>) MainService::getMain)
                .success((ISuccess<MainEntity>) callback::onTaskLoaded)
                .failure((callback::onDataNotAvailable))
                .build()
                .excute();
    }
}
