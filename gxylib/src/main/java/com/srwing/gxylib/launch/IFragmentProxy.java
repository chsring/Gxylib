package com.srwing.gxylib.launch;

import android.content.Intent;

import io.reactivex.rxjava3.core.Observable;

/**
 * Description:
 * Created by srwing
 * Date: 2022/8/2
 * Email: 694177407@qq.com
 */
public interface IFragmentProxy {

    Observable<ActivityResultInfo> startForResult(final Intent intent);

    void startForResult(Intent intent, StartActivityUtil.Callback callback);

    void startActivityNoResult(Intent intent);

}
