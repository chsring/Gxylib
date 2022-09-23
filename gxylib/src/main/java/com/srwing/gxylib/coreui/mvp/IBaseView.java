package com.srwing.gxylib.coreui.mvp;

import com.trello.rxlifecycle4.LifecycleTransformer;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/19
 * Email: 694177407@qq.com
 */
public interface IBaseView {
    <T> LifecycleTransformer<T> bindToLife();
}
