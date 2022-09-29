package com.srwing.gxylib.recycle_adapter.adapter.vadapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;

import com.blankj.utilcode.util.CollectionUtils;
import com.srwing.gxylib.recycle_adapter.BaseViewHolder;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.AdapterDelegate;
import com.srwing.gxylib.vlayout.DelegateAdapter;
import com.srwing.gxylib.vlayout.LayoutHelper;
import com.srwing.gxylib.vlayout.layout.SingleLayoutHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 支持  vLayout 的 下拉刷新 和 加载更多
 * Created by srwing
 * Date: 2022/6/29
 * Email: 694177407@qq.com
 * <p>
 * AsyncListDiffer 踩坑： 会引入动画，使用时应该禁用动画 recyclerView.setItemAnimator(null);
 * 会自动滚动
 */
public abstract class BaseRLDelegateAdapter<T, VH extends BaseViewHolder> extends DelegateAdapter.Adapter<VH> implements AdapterDelegate<T>, SameCallBack<T> {
    LayoutHelper layoutHelper = new SingleLayoutHelper();
    int layoutId;
    protected AsyncListDiffer<T> mDiffUtil; //局部刷新使用。 如果不用AsyncListDiffer，vlayout在一个adapter 调用notifyDataSetChanged 刷新时，全部adapter都会刷新
    public BaseRLDelegateAdapter() {
        mDiffUtil = new AsyncListDiffer<>(this, new DelegateCallback(this));
    }

    public BaseRLDelegateAdapter(LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
        mDiffUtil = new AsyncListDiffer<>(this, new DelegateCallback(this));
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    protected List<T> datas = new ArrayList<>();

    @Override
    public int getItemCount() {
//        return datas.size();
        return mDiffUtil.getCurrentList().size();
    }


    @Override
    public void setList(List<T> datas) {
        this.datas.clear();
        if (!CollectionUtils.isEmpty(datas))
            this.datas.addAll(datas);
//        notifyDataSetChanged();
        List<T> subList = new ArrayList<>(datas);
        mDiffUtil.submitList(subList);

    }

    @Override
    public void addList(List<T> datas) {
        if (CollectionUtils.isEmpty(datas)) return;
        this.datas.addAll(datas);

        List<T> subList = new ArrayList<>(this.datas);
        mDiffUtil.submitList(subList);
//        notifyDataSetChanged();
    }

    //创建范型 VH 的对象
    protected VH createBaseViewHolder(View view) {
        Class<?> temp = null;
        Class<?> z = null;
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp);
            temp = temp.getSuperclass();
        }
        VH vh = null;
        if (z == null) {
            vh = (VH) new BaseViewHolder(view);
        } else {
            createBaseGenericKInstance(z, view);
        }
        if (vh == null) {
            return (VH) new BaseViewHolder(view);
        }
        return vh;
    }

    private Class<?> getInstancedGenericKClass(Class<?> z) {
        try {
            Type type = z.getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) type).getActualTypeArguments();
                for (Type temp : types) {
                    if (temp instanceof Class<?>) {
                        if (BaseViewHolder.class.isAssignableFrom((Class<?>) temp)) {
                            return (Class<?>) temp;
                        }
                    } else if (temp instanceof ParameterizedType) {
                        Type rawType = ((ParameterizedType) temp).getRawType();
                        if (rawType instanceof Class<?> && BaseViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                            return (Class<?>) rawType;
                        }
                    }
                }
            }
        } catch (java.lang.reflect.GenericSignatureFormatError e) {
            e.printStackTrace();
        } catch (TypeNotPresentException e) {
            e.printStackTrace();
        } catch (java.lang.reflect.MalformedParameterizedTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private VH createBaseGenericKInstance(Class<?> z, View view) {
        try {
            Constructor<?> constructor;
            // inner and unstatic class
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class DelegateCallback extends DiffUtil.ItemCallback<T> {

        SameCallBack<T> callBack;

        public DelegateCallback() {
        }

        public DelegateCallback(SameCallBack<T> callBack) {
            this.callBack = callBack;
        }

        @Override
        public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return callBack != null && callBack.itemsSame(oldItem, newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return callBack != null && callBack.contentsSame(oldItem, newItem);
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
            return super.getChangePayload(oldItem, newItem);
        }
    }


    @Override
    public boolean itemsSame(@NonNull T oldItem, @NonNull T newItem) {
        //默认不刷新
        return false;
    }

    @Override
    public boolean contentsSame(@NonNull T oldItem, @NonNull T newItem) {
        //默认不刷新
        return false;
    }
}
