package com.example.gxylib.main.m;

import com.example.gxylib.main.m.MainEntity;
import com.srwing.t_network.base.BaseDataSource;


interface MainDataSource {

    void getMain(BaseDataSource.GetTaskCallback<MainEntity> callback);

}
