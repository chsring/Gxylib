package com.example.gxylib.main.m;

import com.example.gxylib.main.m.MainEntity;
import com.example.gxylib.main.m.MainDataSource;
import com.srwing.t_network.base.BaseDataSource;
import com.trello.rxlifecycle4.LifecycleProvider;

public class MainRepository implements MainDataSource {

    private MainDataSource remoteDataSource;

    public MainRepository(LifecycleProvider provider) {
        this.remoteDataSource = new MainRemoteDataSource(provider);
    }

    public void getMain(BaseDataSource.GetTaskCallback<MainEntity> callback) {
        remoteDataSource.getMain(callback);
    }

}
