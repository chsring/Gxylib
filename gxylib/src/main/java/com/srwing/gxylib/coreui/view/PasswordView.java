package com.srwing.gxylib.coreui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.srwing.gxylib.R;

/**
 * Description: 输入密码的 自定义view
 * Created by srwing
 * Date: 2022/8/3
 * Email: 694177407@qq.com
 */
public class PasswordView extends RelativeLayout {

    private AppCompatImageView ivImage;
    private ClearEditText etContent;
    private String text;
    private boolean isShowText = false;
    private int resClose = R.mipmap.close_eyes;
    private int resOpen = R.mipmap.open_eyes;

    public PasswordView(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.b_password_view, this);
        ivImage = view.findViewById(R.id.ivImage);
        etContent = view.findViewById(R.id.etContent);
        if (attrs != null) {
            @SuppressLint("CustomViewStyleable") final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.c_applib_password_view);
            text = a.getString(R.styleable.c_applib_password_view_hint_text);
            a.recycle();
        }
        if (etContent != null) {
            if (!TextUtils.isEmpty(text)) {
                etContent.setHint(text);
            }
        }
        if (ivImage != null && etContent != null) {
            ivImage.setVisibility(GONE);
            etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        ivImage.setVisibility(View.VISIBLE);
                    } else {
                        ivImage.setVisibility(View.GONE);
                    }
                }
            });
            ivImage.setImageResource(resClose);
            ivImage.setOnClickListener(v -> {
                if (isShowText) {
                    etContent.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivImage.setImageResource(resClose);
                } else {
                    etContent.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivImage.setImageResource(resOpen);
                }
                isShowText = !isShowText;
                etContent.setSelection(etContent.getText().length());
            });
        }
    }

    public void setTheme(boolean isBlack) {
        //黑色主题用白色 眼睛
        resClose = isBlack ? R.mipmap.close_eyes : R.mipmap.close_eyes_black;
        resOpen = isBlack ? R.mipmap.open_eyes : R.mipmap.open_eyes_black;
        ivImage.setImageResource(resClose);
    }

    public String getText() {
        if (etContent != null && etContent.getText() != null && !TextUtils.isEmpty(etContent.getText())) {
            return etContent.getText().toString();
        }
        return "";
    }

    public void setHintText(String text) {
        if (etContent != null && text != null) {
            etContent.setHint(text);
        }
    }

}
