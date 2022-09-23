package com.srwing.gxylib.recycle_adapter.adapter.qadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.AdapterDelegate;

import java.util.List;

/**
 * Description: 基于BaseQuickAdapter的加载更多和刷新的adapter
 * Created by srwing
 * Date: 2022/6/27
 * Email: 694177407@qq.com
 */
public abstract class QuickRefreshLoadAdapter<T, VH extends BaseViewHolder>
        extends BaseQuickAdapter<T, VH> implements AdapterDelegate<T> {
    public QuickRefreshLoadAdapter(int layoutResId) {
        super(layoutResId);
    }

    public QuickRefreshLoadAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public void setList(List<? extends T> datas) {
        super.setList(datas);
    }

    @Override
    public void addList(List<? extends T> datas) {
        super.addData(datas);
    }

}
