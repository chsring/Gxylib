package com.example.gxylib.main.m;

import com.srwing.gxylib.coreui.mvvm.LifeDataSource;
import com.srwing.t_network.GxyNet;
import com.srwing.t_network.base.BaseDataSource;
import com.srwing.t_network.callback.ISuccess;
import com.srwing.t_network.http.IMethod;
import com.trello.rxlifecycle4.LifecycleProvider;

public class MainRemoteDataSource extends LifeDataSource implements MainDataSource {

        public MainRemoteDataSource(LifecycleProvider lifecycleProvider) {
            super(lifecycleProvider);
        }

    @Override
    public void getMain(BaseDataSource.GetTaskCallback<MainEntity> callback) {
        GxyNet.builder()
                .service(MainService.class)
                .bindLifeCycle(lifecycleProvider)
                .method((IMethod<MainService>) MainService::getMain)
                .success((ISuccess<MainEntity>) callback::onTaskLoaded)
                .failure((callback::onDataNotAvailable))
                .build()
                .excute();
    }
}
