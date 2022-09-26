package com.srwing.gxylib.coreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.srwing.gxylib.R;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public class BaseActivity extends RxAppCompatActivity {

    protected LinearLayout baseLeftLayout;
    protected AppCompatTextView backText, titleText, baseRightText;
    protected AppCompatImageView backImg, baseRightImg, baseRightImgTwo, baseRightImgThree;
    protected View includeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置为竖屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar)
            supportActionBar.hide();
    }

    /**
     * 将应用层的布局放置到容器中 当前activity中 不调用 就 无标题
     */
    protected View setInnerContentView(View view) {
        setContentView(R.layout.activity_base_top_bar);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.b_ui_activity_container, null, false);
        includeView = linearLayout.findViewById(R.id.include_title);
        baseLeftLayout = includeView.findViewById(R.id.baseLeftLayout);
        titleText = includeView.findViewById(R.id.titleText);
        baseRightImg = includeView.findViewById(R.id.baseRightImg);
        baseRightImgTwo = includeView.findViewById(R.id.baseRightImgTwo);
        baseRightImgThree = includeView.findViewById(R.id.baseRightImgThree);
        baseRightText = includeView.findViewById(R.id.baseRightText);
        backText = includeView.findViewById(R.id.backText);
        backImg = includeView.findViewById(R.id.backImg);
        linearLayout.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return linearLayout;
    }

    protected void setContentView(){
        super.setContentView(R.layout.activity_base_top_bar);

    }

    protected String getActivityTitle() {
        return "";
    }

    protected int getTitleLayout() {
        return -1;
    }

    protected void initView() {
    }

    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
