package com.srwing.gxylib.coreui;

import com.trello.rxlifecycle4.components.support.RxFragment;

/**
 * Description:
 * Created by small small su
 * Date: 2021/11/16
 * Email: surao@foryou56.com
 */
public abstract class BaseFragment extends RxFragment {

    private boolean isFirstLoad = true;
    @Override
    public void onResume() {
        super.onResume();
        onVisible(isFirstLoad || isVisible());
        isFirstLoad = false;
    }


    @Override
    public void onPause() {
        super.onPause();
        onVisible(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onVisible(!hidden);
    }

    public  void onVisible(boolean isVisible){

    }


}
