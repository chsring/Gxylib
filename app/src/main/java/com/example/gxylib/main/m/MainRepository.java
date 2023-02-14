package com.example.gxylib.main.m;

import com.srwing.t_network.base.BaseDataSource;
import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

public class MainRepository implements MainDataSource {

    private MainDataSource remoteDataSource;

    public MainRepository() {
        this.remoteDataSource = new MainRemoteDataSource();
    }

    public void getMainActivity(LifecycleProvider<ActivityEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback) {
        remoteDataSource.getMainActivity(provider,callback);
    }

    public void getMainFragment(LifecycleProvider<FragmentEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback) {
        remoteDataSource.getMainFragment(provider,callback);
    }

}
