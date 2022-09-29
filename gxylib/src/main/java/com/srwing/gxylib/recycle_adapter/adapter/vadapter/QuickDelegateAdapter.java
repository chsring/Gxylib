package com.srwing.gxylib.recycle_adapter.adapter.vadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.srwing.gxylib.recycle_adapter.BaseViewHolder;
import com.srwing.gxylib.vlayout.LayoutHelper;

/**
 * Description:单item的 快速布局的 支持vlayout的adapter
 * Created by srwing
 * Date: 2022/6/28
 * Email: 694177407@qq.com
 */
public abstract class QuickDelegateAdapter<T, VH extends BaseViewHolder> extends BaseRLDelegateAdapter<T, VH> {

    public QuickDelegateAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    public QuickDelegateAdapter(LayoutHelper layoutHelper, int layoutId) {
        super(layoutHelper);
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        if (view != null) {
            return createBaseViewHolder(view);
        } else {
            return createBaseViewHolder(new View(parent.getContext()));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (CollectionUtils.isEmpty(datas)) return;
//        convert(holder, datas.get(position), position);
        convert(holder, mDiffUtil.getCurrentList().get(position), position);
    }

    public abstract void convert(VH holder, T data, int position);
}
