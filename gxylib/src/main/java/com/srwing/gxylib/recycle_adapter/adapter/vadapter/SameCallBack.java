package com.srwing.gxylib.recycle_adapter.adapter.vadapter;

import androidx.annotation.NonNull;

/**
 * Description:
 * Created srwing
 * Date: 2022/9/28
 * Email: 694177407@qq.com
 */
public interface SameCallBack<T> {
    boolean itemsSame(@NonNull T oldItem, @NonNull T newItem);

    boolean contentsSame(@NonNull T oldItem, @NonNull T newItem);
}
