package com.srwing.gxylib.coreui.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/19
 * Email: 694177407@qq.com
 */
public abstract class BasePresenter<V extends IBaseView,M extends BaseModel> {

    protected Reference<V> mRefView;
    protected M mModel;

    protected V getView() {
        if (!isViewAttached()) {
            throw new IllegalStateException("mvp's view is not attached, please check again!");
        }
        return mRefView.get();
    }

    private boolean isViewAttached() {
        return mRefView != null && mRefView.get() != null;
    }

    public void attachView(V view) {
        mRefView = new WeakReference<>(view);
    }

    public void detachView() {
        if (mRefView != null) {
            mRefView.clear();
            mRefView = null;
        }
    }

    protected M getModel() {
        return mModel;
    }

    public void attachModel(M model) {
        mModel = model;
    }
}
