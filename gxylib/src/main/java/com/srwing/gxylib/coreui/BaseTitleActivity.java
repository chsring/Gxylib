package com.srwing.gxylib.coreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.srwing.gxylib.R;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

/**
 * Description: 基础Activity类，设置主题的方法交由子类处理
 * Created srwing
 * Date: 2022/9/26
 * Email: 694177407@qq.com
 */
public abstract class BaseTitleActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.hide();
        }
        setContentView(getContentView());
        initViewData();
    }

    protected View setTitleContentView(View view) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_base_top_bar, null, false);
        setBackGroundColor(linearLayout);
        if (getTitleLayout() != -1) {
            View titleLayout = LayoutInflater.from(this).inflate(getTitleLayout(), null, false);
            setTitleContent(titleLayout);
            linearLayout.addView(titleLayout);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        linearLayout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return linearLayout;
    }

    // 标题布局
    protected int getTitleLayout() {
        return -1;
    }

    //获取到标题布局，进行详细设置
    protected void setTitleContent(View view) {

    }

    //子类用于 设置背景
    protected void setBackGroundColor(LinearLayout view) {

    }

    protected void initViewData() {

    }

    //设置内容布局
    protected abstract int getContentView();
}
