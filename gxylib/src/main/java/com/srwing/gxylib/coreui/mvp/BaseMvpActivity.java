package com.srwing.gxylib.coreui.mvp;

import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.srwing.gxylib.coreui.BaseTitleActivity;
import com.srwing.gxylib.coreui.TypeUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.android.ActivityEvent;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/19
 * Email: 694177407@qq.com
 */
public abstract class BaseMvpActivity<VB extends ViewDataBinding, P extends BasePresenter, M extends BaseModel>
        extends BaseTitleActivity implements IBaseView {

    public P mPresenter;
    protected VB dataBinding;

    @Override
    public void setContentView(int layoutResId) {
        View layout = LayoutInflater.from(this).inflate(layoutResId, null, false);
        dataBinding = DataBindingUtil.bind(layout);
        if (getTitleLayout() != -1) {
            //如果有标题
            super.setContentView(setTitleContentView(layout));
        } else {
            super.setContentView(dataBinding.getRoot());
        }
        mPresenter = TypeUtils.getInstance(this, 1);
        M model = TypeUtils.getInstance(this, 2);
        if (mPresenter != null) {
            mPresenter.attachView(this);
            model.setView(mPresenter.getView());
            mPresenter.attachModel(model);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
//        return bindToLifecycle();
    }
}