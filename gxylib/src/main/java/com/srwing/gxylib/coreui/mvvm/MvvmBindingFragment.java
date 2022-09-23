package com.srwing.gxylib.coreui.mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.srwing.gxylib.coreui.BaseFragment;
import com.srwing.gxylib.coreui.BaseViewModel;
import com.trello.rxlifecycle4.components.support.RxFragment;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:MvvM 模式BaseFragment 处理ViewModel绑定
 * Created by srwing
 * Date: 23/1/2019
 * Email: surao@foryou56.com
 */
public abstract class MvvmBindingFragment<VB extends ViewDataBinding, VM extends BaseViewModel>
        extends BaseFragment implements IMvvmActivity {

    protected VB dataBinding;
    protected VM viewModel;

    public abstract int getLayoutId();

    @Override
    public int getBRId() {
        return -1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int i = getLayoutId();
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length == 2) {
                return initBindingViewModel(inflater, i);
            }
            if (actualTypeArguments.length == 1 && actualTypeArguments[0] instanceof ViewDataBinding) {
                return initBindingView(inflater, getLayoutId());
            }
        }
        return inflater.inflate(i, null, false);
    }

    //初始化要绑定的View
    private View initBindingView(LayoutInflater inflater, int layoutId) {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, null, false);
        return dataBinding.getRoot();
    }

    //初始化绑定ViewModel 加生命周期管理
    private View initBindingViewModel(LayoutInflater inflater, int layoutId) {
        viewModel = obtainViewModel(this);
        dataBinding = DataBindingUtil.inflate(inflater, layoutId, null, false);
        dataBinding.setVariable(getBRId(), viewModel);
        View root = dataBinding.getRoot();
        getLifecycle().addObserver(viewModel);
        return root;
    }

    //根据泛型参数  初始化ViewModel
    public VM obtainViewModel(RxFragment fragment) {
        // Use a Factory to inject dependencies into the ViewModel
        Class modelClass;

        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            modelClass = BaseViewModel.class;
        }
        ViewModelProvider vp = new ViewModelProvider(fragment);
        VM vm = (VM) vp.get(modelClass);
        return vm;
    }

    @Override
    public void onDestroy() {
        if (null != viewModel) {
            getLifecycle().removeObserver(viewModel);
        }
        super.onDestroy();
    }
}
