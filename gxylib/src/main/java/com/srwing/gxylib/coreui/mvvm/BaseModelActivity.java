package com.srwing.gxylib.coreui.mvvm;

import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.srwing.gxylib.coreui.BaseActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public abstract class BaseModelActivity<VM extends BaseViewModel> extends BaseActivity implements IMvvmActivity {

    protected VM viewModel;

    @Override
    public int getBRId() {
        return 0;
    }

    @Override
    public void setContentView(int layoutResId) {
        View layout = LayoutInflater.from(this).inflate(layoutResId, null, false);
        if (isMvvMMode()) {
            viewModel = obtainViewModel(this);
            getLifecycle().addObserver(viewModel);
        }
        if (getTitleLayout() != -1) {
            //如果有标题
            super.setContentView(setTitleContentView(layout));
        } else {
            super.setContentView(layout);
        }
    }

    /**
     * 判断是否使用了 MVVM MODE
     */
    private boolean isMvvMMode() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            return actualTypeArguments.length == 1;
        }
        return true;
    }

    /**
     * 根据Activity泛型参数 初始化ViewModel
     * @param <VM>     ViewModel 泛型参数
     */
    public <VM> VM obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel.class;
        }
        ViewModelProvider activityProvider = new ViewModelProvider(activity);
        VM vm = (VM) activityProvider.get(modelClass);
        return vm;
    }

    @Override
    protected void onDestroy() {
        if (null != viewModel) {
            getLifecycle().removeObserver(viewModel);
        }
        super.onDestroy();
    }
}
