package com.srwing.gxylib.coreui.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.srwing.gxylib.coreui.BaseTitleActivity;

/**
 * Description: 只带标题的 Activity
 * Created by srwing
 * Date: 2022/7/28
 * Email: 694177407@qq.com
 */
public abstract class BaseBindingActivity<VB extends ViewDataBinding> extends BaseTitleActivity {
    public VB dataBinding;

    @Override
    public void setContentView(int layoutResID) {
        View layout = LayoutInflater.from(this).inflate(layoutResID, null, false);
        dataBinding = DataBindingUtil.bind(layout);
        if (getTitleLayout() != -1) {
            //如果有标题
            super.setContentView(setTitleContentView(layout));
        } else {
            super.setContentView(dataBinding.getRoot());
        }
    }
}
