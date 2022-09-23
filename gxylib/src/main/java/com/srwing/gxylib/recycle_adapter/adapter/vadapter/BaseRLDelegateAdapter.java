package com.srwing.gxylib.recycle_adapter.adapter.vadapter;

import android.view.View;

import com.blankj.utilcode.util.CollectionUtils;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.AdapterDelegate;
import com.srwing.gxylib.recycle_adapter.BaseViewHolder;
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
 * Description: 支持 下拉刷新 和 加载更多
 * Created by srwing
 * Date: 2022/6/29
 * Email: 694177407@qq.com
 */
public abstract class BaseRLDelegateAdapter<T, VH extends BaseViewHolder> extends DelegateAdapter.Adapter<VH> implements AdapterDelegate<T> {
    LayoutHelper layoutHelper = new SingleLayoutHelper();
    int layoutId;

    public BaseRLDelegateAdapter() {
    }

    public BaseRLDelegateAdapter(LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    protected List<T> datas = new ArrayList<>();

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void setList(List<? extends T> datas) {
        this.datas.clear();
        if (!CollectionUtils.isEmpty(datas))
            this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void addList(List<? extends T> datas) {
        if (CollectionUtils.isEmpty(datas)) return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
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
}
