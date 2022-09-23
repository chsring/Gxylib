package com.srwing.gxylib.coreui.mvp;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/19
 * Email: 694177407@qq.com
 */
public class BaseModel {
    public IBaseView getView() {
        return view;
    }

    public void setView(IBaseView view) {
        this.view = view;
    }

    private IBaseView view;

}
