package com.srwing.gxylib.recycle_adapter.adapter.vadapter;

import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.blankj.utilcode.util.CollectionUtils;
import com.srwing.gxylib.recycle_adapter.BaseViewHolder;
import com.srwing.gxylib.recycle_adapter.MultipleItemEntity;
import com.srwing.gxylib.vlayout.LayoutHelper;

/**
 * Description:多item的 快速布局的 支持vlayout的adapter，如果不用vlayout 数据有单一接口来源 就用 qadapter包下面的
 * Created by srwing
 * Date: 2022/6/29
 * Email: 694177407@qq.com
 */
public abstract class MutiDelegateAdapter<T extends MultipleItemEntity, VH extends BaseViewHolder> extends BaseRLDelegateAdapter<MultipleItemEntity, VH> {
    //必须使用MultipleItemEntity的类型
    SparseIntArray layouts;

    public MutiDelegateAdapter(LayoutHelper layoutHelper) {
        super(layoutHelper);
        layouts = new SparseIntArray();
    }

    public abstract void convert(VH holder, int type, MultipleItemEntity data, int position);

    //多item布局必须在子类构造函数中调用addItemType;
    public void addItemType(int viewType, @LayoutRes int layoutResId) {
        layouts.put(viewType, layoutResId);
    }

    @Override
    public int getItemViewType(int position) {
//        return datas.get(position).getItemType();
        int type = datas.get(position).getItemType();
        return CollectionUtils.isEmpty(datas) ? super.getItemViewType(position) : type;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return createBaseViewHolder(new View(parent.getContext()));
        }
//        return createBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layouts.get(viewType), parent, false));

        layoutId = layouts.get(viewType);
        if (layoutId != -1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            if (view != null)
                return (VH) new BaseViewHolder(view);
        }
        return createBaseViewHolder(new View(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (CollectionUtils.isEmpty(datas)) return;
//        convert(holder, datas.get(position).getItemType(), datas.get(position), position);
        convert(holder, datas.get(position).getItemType(), mDiffUtil.getCurrentList().get(position), position);

    }
}
