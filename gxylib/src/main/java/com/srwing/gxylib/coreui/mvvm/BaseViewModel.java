package com.srwing.gxylib.coreui.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public class BaseViewModel extends AndroidViewModel implements IViewModel {

    protected Application application;


    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }


    //activity onDestroy时的回调
    @Override
    public void onDestroy() {
        Log.e("viewModel", "onDestroy");
    }
}
