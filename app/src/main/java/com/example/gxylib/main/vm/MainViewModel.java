package com.example.gxylib.main.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.gxylib.main.m.MainEntity;
import com.example.gxylib.main.m.MainRepository;
import com.srwing.gxylib.coreui.mvvm.BaseLifeViewModel;
import com.srwing.t_network.base.BaseDataSource;
import com.srwing.t_network.http.CorrCode;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

/**
 * @author surao
 */
public class MainViewModel extends BaseLifeViewModel<FragmentEvent> {

    private final MainRepository repository;

    private final MutableLiveData<MainEntity> liveData = new MutableLiveData<>();

    public MutableLiveData<MainEntity> getLiveData() {
        return liveData;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new MainRepository();
    }

    public void getMain() {
//        repository.getMainActivity(this, new BaseDataSource.GetTaskCallback<>() {
//            @Override
//            public void onTaskLoaded(MainEntity task) {
//                if (task != null && task.code == CorrCode.CODE_CORRECT.code && !CollectionUtils.isEmpty(task.data)) {
//                    liveData.setValue(task);
//                }
//            }
//
//            @Override
//            public void onDataNotAvailable(int code, String desc) {
//                ToastUtils.showShort(desc);
//            }
//        });

        repository.getMainFragment(this, new BaseDataSource.GetTaskCallback<>() {
            @Override
            public void onTaskLoaded(MainEntity task) {
                if (task != null && task.code == CorrCode.CODE_CORRECT.code && !CollectionUtils.isEmpty(task.data)) {
                    liveData.setValue(task);
                }
            }

            @Override
            public void onDataNotAvailable(int code, String desc) {
                ToastUtils.showShort(desc);
            }
        });
    }

}
