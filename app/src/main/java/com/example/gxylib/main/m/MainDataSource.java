package com.example.gxylib.main.m;

import com.srwing.t_network.base.BaseDataSource;
import com.trello.rxlifecycle4.LifecycleProvider;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;


interface MainDataSource {

    void getMainActivity(LifecycleProvider<ActivityEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback);

    void getMainFragment(LifecycleProvider<FragmentEvent> provider, BaseDataSource.GetTaskCallback<MainEntity> callback);
}
