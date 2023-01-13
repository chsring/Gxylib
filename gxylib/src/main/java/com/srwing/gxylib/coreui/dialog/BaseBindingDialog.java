package com.srwing.gxylib.coreui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Description:
 * Created srwing
 * Date: 2023/1/12
 * Email: 694177407@qq.com
 */
public abstract class BaseBindingDialog<T extends ViewDataBinding> extends DialogFragment {

    protected T databinding;
    private int DEFAULT_WIDTH = WindowManager.LayoutParams.MATCH_PARENT;//宽
    private int DEFAULT_HEIGHT = WindowManager.LayoutParams.WRAP_CONTENT;//高
    private int DEFAULT_GRAVITY = Gravity.CENTER;//Gravity.BOTTOM;//位置

    private boolean mCancelable = true;//默认可取消
    private boolean mCanceledOnTouchOutside = true;//默认点击外部可取消

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(getLayoutId(), container, false);
        databinding = DataBindingUtil.bind(mView);
        initData();
        return mView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog mDialog = super.onCreateDialog(savedInstanceState);
        //初始化
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        mDialog.setCancelable(mCancelable);
        Window window = mDialog.getWindow();
        if (null != window) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);
            lp.height = DEFAULT_HEIGHT;
            lp.gravity = DEFAULT_GRAVITY;
            lp.windowAnimations = android.R.style.Animation_InputMethod;
            window.setAttributes(lp);
        }
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return !mCancelable;
            }
        });
        return mDialog;
    }

    /**
     * 设置位置
     */
    public void setGravity(int gravity) {
        DEFAULT_GRAVITY = gravity;
    }

    /**
     * 设置宽
     */
    public void setWidth(int width) {
        DEFAULT_WIDTH = width;
    }

    /**
     * 设置高
     */
    public void setHeight(int height) {
        DEFAULT_HEIGHT = height;
    }

    /**
     * 设置点击返回按钮是否可取消
     */
    @Override
    public void setCancelable(boolean cancelable) {
        mCancelable = cancelable;
    }

    /**
     * 设置点击外部是否可取消
     */
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * 获取弹窗的TAG
     */
    protected abstract String getDialogTag();

    /**
     * 设置布局
     */
    protected abstract int getLayoutId();

    /**
     * ⚠️ 初始化业务逻辑必须从这里设置
     */
    protected abstract void initData();


    //展示弹窗
    public void showDialog(boolean cancleAble, FragmentManager fragmentManager) {
        setCancelable(cancleAble);
        show(fragmentManager, getDialogTag());
    }
}