package com.example.gxylib.main.v;

import android.view.Gravity;
import android.view.View;

import com.example.gxylib.R;
import com.example.gxylib.databinding.ActivityMainBinding;
import com.example.gxylib.main.vm.MainViewModel;
import com.srwing.gxylib.coreui.mvvm.BaseMvvmActivity;
import com.srwing.gxylib.coreui.view.badgevview.BadgeView;
import com.srwing.t_network.GxyNet;
import com.srwing.t_network.interceptors.LogInterceptor;

public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void setTitleContent(View view) {
        // 添加标题的一些事件
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        setTitle("首页");

        new BadgeView(this).bindTarget(dataBinding.line1)
                .setBadgeNumber(6).setBadgeGravity(Gravity.TOP | Gravity.END);
//                .setGravityOffset(-6,true);


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
