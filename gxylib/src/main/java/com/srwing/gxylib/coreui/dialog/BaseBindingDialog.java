package com.srwing.gxylib.coreui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
 * Description:androidx 1.1.0 版本上已经做了处理 处理了内存泄漏的问题
 * 和 Dialog 的区别：
 * 相比较 Dialog 来说，DialogFragment 其内嵌了一个 Dialog ，并对它进行一些灵活的管理，并且在 Activity 被异常销毁后重建的时候，
 * DialogFragment 也会跟着重建，单独使用 Dialog 就不会。而且我们可以在 DialogFragment 的 onSaveInstanceState 方法中保存一些我们的数据，
 * DialogFragment 跟着 Activity 重建的时候，从 onRestoreInstanceState 中取出数据，恢复页面显示。
 * Dialog 不适合复杂UI，而且不适合弹窗中有网络请求的逻辑开发。而 DialogFragment 可以当做一个 Fragment 来使用，比较适合做一些复杂的逻辑，网络请求。
 * <p>
 * 注意⚠️： 每次弹窗消失后再次使用的时候都要new，否则会有 FragmentManager not null 的内存泄漏，例如
 * dataBinding.civ.setOnClickListener{
 * val chessGiveUpDialog = ChessGiveUpDialog()
 * chessGiveUpDialog.showDialog(supportFragmentManager)
 * }
 * <p>
 * Created srwing
 * Date: 2023/1/12
 * Email: 694177407@qq.com
 *
 * @author surao
 */
public abstract class BaseBindingDialog<T extends ViewDataBinding> extends DialogFragment {
    private static final String TAG = BaseBindingDialog.class.getSimpleName();

    protected T databinding;
    //宽度比例
    private double defaultWidthRatio = 0.8;
    private int defaultHeight = WindowManager.LayoutParams.WRAP_CONTENT;
    //显示位置位置
    private int defaultGravity = Gravity.CENTER;
    //默认可取消
    private boolean mCancelable = true;
    //默认点击外部可取消
    private boolean mCanceledOnTouchOutside = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View mView = inflater.inflate(getLayoutId(), container, false);
        databinding = DataBindingUtil.bind(mView);
        initData();
        return mView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
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
            lp.width = (int) (getResources().getDisplayMetrics().widthPixels * defaultWidthRatio);
            lp.height = defaultHeight;
            lp.gravity = defaultGravity;
            lp.windowAnimations = android.R.style.Animation_InputMethod;
            window.setAttributes(lp);
        }
        mDialog.setOnKeyListener((dialog, keyCode, event) -> {
            Log.d(TAG, "onKey");
            return !mCancelable;
        });
        return mDialog;
    }

    /**
     * 设置位置
     */
    public void setGravity(int gravity) {
        defaultGravity = gravity;
    }

    /**
     * 设置宽
     */
    public void setWidth(double widthRatio) {
        defaultWidthRatio = widthRatio;
    }

    /**
     * 设置高
     */
    public void setHeight(int height) {
        defaultHeight = height;
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
    public void showDialog(boolean mCancelable, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().remove(this).commit();
        show(fragmentManager, getDialogTag());
    }
}