package com.srwing.gxylib.coreui.mvvm;

import com.trello.rxlifecycle4.LifecycleProvider;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/18
 * Email: 694177407@qq.com
 */
public class LifeDataSource {
    protected LifecycleProvider lifecycleProvider;

    public LifeDataSource(LifecycleProvider lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }
}
