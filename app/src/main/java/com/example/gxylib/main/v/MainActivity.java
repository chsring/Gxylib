package com.example.gxylib.main.v;

import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.gxylib.R;
import com.example.gxylib.databinding.ActivityMainBinding;
import com.example.gxylib.main.vm.MainViewModel;
import com.srwing.b_applib.launch.JLauncher;
import com.srwing.gxylib.coreui.mvvm.BaseMvvmActivity;
import com.srwing.t_network.GxyNet;
import com.srwing.t_network.interceptors.LogInterceptor;
import com.srwing.t_network.utils.GxyLogger;

public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, MainViewModel> implements View.OnClickListener{
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleLayout() {
        return R.layout.b_base_title_bar;
    }

    @Override
    protected void setTitleContent(View view) {
        // 添加标题的一些事件
        AppCompatTextView tv = view.findViewById(R.id.titleText);
        tv.setText("首页");
    }

    @Override
    protected void initViewData() {
        super.initViewData();
        setTitle("首页");


        GxyNet.init(this).withApiHost("https://server6.19x19.com")
                .withInterceptor(new LogInterceptor()).withLoggerAdapter() //设置LogAdapter
                .withDebugMode(true) //设置是否打印请求 日志
                .withNoProxy(false).configure(); //配置生效


        viewModel.getLiveData().observe(this, data -> {
            if (data == null) return;
            ToastUtils.showLong("aaaaa");

        });
        viewModel.getMain();

        dataBinding.start.setOnClickListener(this);
        dataBinding.pause.setOnClickListener(this);
        dataBinding.stop.setOnClickListener(this);
        dataBinding.rpvv.setSecond(30);
        dataBinding.rpvv.setTimesPerMillionSecond(20);

        JLauncher jLauncher = new JLauncher(this);
        jLauncher.launch(new Intent(MainActivity.this,TestActivity2.class), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:

                GxyLogger.v("CutDownTimer","----start-----");
                dataBinding.rpvv.start();
//                dataBinding.rpv.start(20);
                break;
            case R.id.pause:
//                dataBinding.rpv.pause();
                GxyLogger.v("CutDownTimer","----stop-----");
                dataBinding.rpvv.stop();
                break;
            case R.id.stop:

                GxyLogger.v("CutDownTimer","----reset-----");
                dataBinding.rpvv.reset();
                break;
        }
    }

}