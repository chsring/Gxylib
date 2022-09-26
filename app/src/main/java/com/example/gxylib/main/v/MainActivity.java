package com.example.gxylib.main.v;

import android.view.View;

import com.example.gxylib.R;
import com.example.gxylib.databinding.ActivityMainBinding;
import com.example.gxylib.main.vm.MainViewModel;
import com.srwing.gxylib.coreui.mvvm.MvvmBindingActivity;
import com.srwing.t_network.GxyNet;
import com.srwing.t_network.interceptors.LogInterceptor;

public class MainActivity extends MvvmBindingActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleLayout() {
        return com.srwing.gxylib.R.layout.b_base_title_bar;
    }

    @Override
    protected String getActivityTitle() {
        return "首页";
    }

    @Override
    protected void setTitleContent(View view) {
        // 添加标题的一些事件
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        GxyNet.init(this)
                .withApiHost("https://api.19x19.com")
                .withInterceptor(new LogInterceptor())
                .withLoggerAdapter() //设置LogAdapter
                .withDebugMode(true) //设置是否打印请求 日志
                .withNoProxy(false)
                .configure(); //配置生效


        viewModel.getLiveData().observe(this, data -> {
            if (data == null) return;
//              dataBinding.setBean(data);
            // TODO
        });
        viewModel.getMain();
    }
}
