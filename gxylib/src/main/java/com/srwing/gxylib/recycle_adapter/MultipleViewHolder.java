package com.srwing.gxylib.recycle_adapter;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/27
 * Email: 694177407@qq.com
 */
public class MultipleViewHolder extends BaseViewHolder {

    public MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }


}