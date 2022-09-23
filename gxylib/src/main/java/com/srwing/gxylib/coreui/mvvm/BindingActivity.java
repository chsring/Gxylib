package com.srwing.gxylib.coreui.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.srwing.gxylib.coreui.BaseActivity;

/**
 * Description: 只带标题的 Activity
 * Created by srwing
 * Date: 2022/7/28
 * Email: 694177407@qq.com
 */
public class BindingActivity<VB extends ViewDataBinding> extends BaseActivity {
    public VB dataBinding;

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null, false);
        super.setContentView(setInnerContentView(view));
        dataBinding = DataBindingUtil.bind(view);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(setInnerContentView(view));
        dataBinding = DataBindingUtil.bind(view);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(setInnerContentView(view));
        dataBinding = DataBindingUtil.bind(view);
        initView();
        initData();
    }
}
