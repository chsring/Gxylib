package com.srwing.gxylib.recycle_adapter.adapter.delegate;

import java.util.List;

/**
 * Description:
 * Created srwing
 * Date: 2022/9/28
 * Email: 694177407@qq.com
 */
public interface AdapterDelegate<T> {
    void setList(List<T> datas);

    void addList(List<T> datas);
}
