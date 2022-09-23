package com.srwing.gxylib.livedatabus;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/21
 * Email: 694177407@qq.com
 */
public class BaseEventEntity {

    public String TAG;
    public Object o;

    public BaseEventEntity(String TAG, Object o) {
        this.TAG = TAG;
        this.o = o;
    }

}
