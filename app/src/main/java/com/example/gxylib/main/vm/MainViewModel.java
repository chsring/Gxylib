package com.example.gxylib.main.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.CollectionUtils;
import com.srwing.gxylib.coreui.BaseViewModel;
import com.srwing.t_network.base.BaseDataSource;

import com.blankj.utilcode.util.ToastUtils;

import com.example.gxylib.main.m.MainEntity;
import com.example.gxylib.main.m.MainRepository;
import com.srwing.t_network.http.CorrCode;

public class MainViewModel extends BaseViewModel {

     private MainRepository repository;

     private MutableLiveData<MainEntity.DataBean> liveData = new MutableLiveData<>();

     public MutableLiveData<MainEntity.DataBean> getLiveData() {
            return liveData;
      }

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository(this);
    }

    public void getMain() {
        repository.getMain(new BaseDataSource.GetTaskCallback<MainEntity>(){
               @Override
               public void onTaskLoaded(MainEntity task) {
//                   if (task!=null && task.code == CorrCode.CODE_CORRECT.code && !CollectionUtils.isEmpty(task.data)) {
//                       liveData.setValue();
//                   }
               }
               @Override
               public void onDataNotAvailable(int code, String desc) {
                   ToastUtils.showShort(desc);
               }
        });
     }

}
