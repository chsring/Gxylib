package com.srwing.gxylib.coreui.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.srwing.gxylib.coreui.BaseActivity;
import com.srwing.gxylib.coreui.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public class MvvmBindingActivity<VB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity implements IMvvmActivity {

    protected VB dataBinding;
    protected VM viewModel;

    @Override
    public int getBRId() {
        return -1;
    }

    @Override
    public void setContentView(int layoutResID) {
        View childView = LayoutInflater.from(this).inflate(layoutResID, null, false);
        //默认有标题
        setContentView(childView, true);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, true);
        initView();
        initData();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setContentView(view, true);
        initView();
        initData();
    }

    private void setContentView(View view, boolean hasTitle) {
        if (isMvvMMode()) {
            dataBinding = DataBindingUtil.bind(view);
            viewModel = obtainViewModel(this);
            int brId = getBRId();
            dataBinding.setVariable(brId, viewModel);
            //添加ViewModel的生命周期管理
            getLifecycle().addObserver(viewModel);
            if (hasTitle) {
                //如果有标题
                super.setContentView(setInnerContentView(view));

            } else {
                super.setContentView(dataBinding.getRoot());
            }
        } else {
            if (hasTitle) {
                super.setContentView(setInnerContentView(view));
            } else {
                super.setContentView(view);
            }
        }
    }

    /**
     * 设置没有Title的ContentView
     *
     * @param layoutResID
     */
    public void setContentViewNoTitle(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null, false);
        setContentView(view, false);
    }

    /**
     * 设置没有Title的ContentView
     */
    public void setContentViewNoTitle(View view) {
        setContentView(view, false);
    }

    /**
     * 判断是否使用了 MVVM MODE
     *
     * @return
     */
    private boolean isMvvMMode() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            return actualTypeArguments.length == 2;
        }
        return true;
    }

    /**
     * 根据Activity泛型参数 初始化ViewModel
     *
     * @param activity
     * @param <VM>     ViewModel 泛型参数
     * @return
     */
    public <VM> VM obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
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
