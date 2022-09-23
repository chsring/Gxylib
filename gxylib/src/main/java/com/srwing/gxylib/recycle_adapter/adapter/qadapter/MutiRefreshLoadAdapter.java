package com.srwing.gxylib.recycle_adapter.adapter.qadapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.AdapterDelegate;

import java.util.List;

/**
 * Description: 基于多布局adapter的加载更多
 * Created by srwing
 * Date: 2022/6/27
 * Email: 694177407@qq.com
 */
public abstract class MutiRefreshLoadAdapter<T extends MultiItemEntity, VH extends BaseViewHolder>
        extends BaseMultiItemQuickAdapter<T, VH> implements AdapterDelegate<T> {
    public MutiRefreshLoadAdapter() {
    }

    public MutiRefreshLoadAdapter(@Nullable List<T> data) {
        super(data);
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
