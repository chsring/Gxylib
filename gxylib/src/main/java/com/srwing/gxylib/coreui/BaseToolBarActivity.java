package com.srwing.gxylib.coreui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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
public abstract class BaseToolBarActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar)
            supportActionBar.hide();
        setContentView(R.layout.activity_base_top_bar);
        LinearLayout linearTitle = findViewById(R.id.act_toolbar);
        if (!TextUtils.isEmpty(getActivityTitle()) && getTitleLayout() != -1) {
            View view = LayoutInflater.from(this).inflate(getTitleLayout(), null, false);
            setTitleContent(view);
            linearTitle.addView(view);
            FrameLayout viewContent = findViewById(R.id.view_content);
            LayoutInflater.from(this).inflate(getContentView(), viewContent);
        } else {
            linearTitle.setVisibility(View.GONE);
        }
    }

    protected String getActivityTitle() {
        return "";
    }

    protected int getTitleLayout() {
        return -1;
    }

    //具体设置 标题的方法
    protected void setTitleContent(View view) {

    }

    protected void initViewData() {

    }

    //设置内容
    protected abstract int getContentView();
}
