package com.srwing.gxylib.coreui.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.srwing.gxylib.coreui.BaseToolBarActivity;

/**
 * Description: 只带标题的 Activity
 * Created by srwing
 * Date: 2022/7/28
 * Email: 694177407@qq.com
 */
public abstract class BindingActivity<VB extends ViewDataBinding> extends BaseToolBarActivity {
    public VB dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View childView = LayoutInflater.from(this).inflate(getContentView(), null, false);
        dataBinding = DataBindingUtil.bind(childView);
        initViewData();
    }
}
