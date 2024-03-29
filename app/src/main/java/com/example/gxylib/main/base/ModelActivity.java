package com.example.gxylib.main.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.gxylib.R;
import com.srwing.gxylib.coreui.mvvm.BaseModelActivity;
import com.srwing.gxylib.coreui.mvvm.BaseViewModel;

/**
 * Description:
 * Created srwing
 * Date: 2022/9/26
 * Email: 694177407@qq.com
 */
public abstract class ModelActivity<VM extends BaseViewModel> extends BaseModelActivity<VM> {
    protected LinearLayout baseLeftLayout;
    protected AppCompatTextView backText, titleText, baseRightText;
    protected AppCompatImageView backImg, baseRightImg, baseRightImgTwo, baseRightImgThree;
    protected View includeView;

    public static final String THEME_BLACK = "THEME_BLACK";
    public static final String THEME_WHITE = "THEME_WHITE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 设置为竖屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        boolean isBlack = THEME_BLACK.equals(SharedPreferencesUtil.getThemeColor(this));
//        if (isBlack) {
//            setTheme(R.style.AppThemeBlack);
//        } else {
//            setTheme(R.style.AppThemeWhite);
//        }
        //设置主题一定要在onCreate之前
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getTitleLayout() {
        return R.layout.b_base_title_bar;
    }

    @Override
    protected void setTitleContent(View view) {
        includeView = view.findViewById(R.id.baseTitleBar);
        baseLeftLayout = view.findViewById(R.id.baseLeftLayout);
        titleText = view.findViewById(R.id.titleText);
        baseRightImg = view.findViewById(R.id.baseRightImg);
        baseRightImgTwo = view.findViewById(R.id.baseRightImgTwo);
        baseRightImgThree = view.findViewById(R.id.baseRightImgThree);
        baseRightText = view.findViewById(R.id.baseRightText);
        backText = view.findViewById(R.id.backText);
        backImg = view.findViewById(R.id.backImg);
        if (null != baseLeftLayout)
            if (isBack())
                baseLeftLayout.setOnClickListener(v -> ModelActivity.this.finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String localClassName = getLocalClassName();
//        MobclickAgent.onPageStart(localClassName.contains(".") ? localClassName.split("\\.")[1] : localClassName);
//        MobclickAgent.onResume(this);
//        MobclickAgent.onEvent(this, localClassName.contains(".") ? localClassName.split("\\.")[1] : localClassName);
//        AppLog.onResume(this);
//        //由于人人对弈去研究需要尽量使得界面不变，所以研究界面状态栏颜色自己维护
//        if (localClassName.equals("activity.PlayAnalysisActivity"))
//            return;
//        String themeStr = SharedPreferencesUtil.getThemeColor(this);
//        switch (themeStr) {
//            case THEME_BLACK:
//                int themeBackgroundColor = ContextCompat.getColor(this, R.color.themeBackgroundColorBlack);
//                StatusBarCompat.setStatusBarDarkMode(this, themeBackgroundColor);
//                break;
//            case THEME_WHITE:
//                themeBackgroundColor = ContextCompat.getColor(this, R.color.themeBackgroundColorWhite);
//                StatusBarCompat.setStatusBarLightMode(this, themeBackgroundColor);
//                break;
//            default:
//                break;
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getLocalClassName().contains(".") ? getLocalClassName().split("\\.")[1] : getLocalClassName());
//        MobclickAgent.onPause(this);
//        AppLog.onPause(this);
    }

    protected boolean isBack() {
        return true;
    }

    protected void setTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            titleText.setText(text);
        }
    }


}
