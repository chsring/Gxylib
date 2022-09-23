package com.srwing.gxylib.coreui.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.srwing.gxylib.R;
import com.srwing.gxylib.coreui.BaseActivity;
import com.srwing.gxylib.coreui.TypeUtils;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.android.ActivityEvent;

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/19
 * Email: 694177407@qq.com
 */
public abstract class BaseMvpActivity<VB extends ViewDataBinding, P extends BasePresenter, M extends BaseModel>
        extends BaseActivity implements IBaseView {

    public P mPresenter;
    protected VB dataBinding;

    private LinearLayout baseLeftLayout;
    protected AppCompatTextView backText, titleText, baseRightText;
    protected AppCompatImageView backImg, baseRightImg, baseRightImgTwo, baseRightImgThree;

    @Override
    public void setContentView(int layoutResID) {
        View childView = LayoutInflater.from(this).inflate(layoutResID, null, false);
        setContentView(childView, true);
        mPresenter = TypeUtils.getInstance(this, 1);
        M model = TypeUtils.getInstance(this, 2);
        if (mPresenter != null) {
            mPresenter.attachView(this);
            model.setView(mPresenter.getView());
            mPresenter.attachModel(model);
        }
        initView();
        initData();
    }


    private void setContentView(View view, boolean hasTitle) {
        if (hasTitle) {
            //如果有标题
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.b_ui_activity_container, null, false);
            baseLeftLayout = linearLayout.findViewById(R.id.include_title).findViewById(R.id.baseLeftLayout);
            titleText = linearLayout.findViewById(R.id.include_title).findViewById(R.id.titleText);
            baseRightImg = linearLayout.findViewById(R.id.include_title).findViewById(R.id.baseRightImg);
            baseRightImgTwo = linearLayout.findViewById(R.id.include_title).findViewById(R.id.baseRightImgTwo);
            baseRightImgThree = linearLayout.findViewById(R.id.include_title).findViewById(R.id.baseRightImgThree);
            baseRightText = linearLayout.findViewById(R.id.include_title).findViewById(R.id.baseRightText);
            backText = linearLayout.findViewById(R.id.include_title).findViewById(R.id.backText);
            backImg = linearLayout.findViewById(R.id.include_title).findViewById(R.id.backImg);
            dataBinding = DataBindingUtil.bind(view);
            linearLayout.addView(dataBinding.getRoot(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            //添加ViewModel的生命周期管理
            super.setContentView(linearLayout);

        } else {
            dataBinding = DataBindingUtil.bind(view);
            super.setContentView(dataBinding.getRoot());
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
//        return bindToLifecycle();
    }

    public void setBackButton() {
        baseLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }
}