package com.srwing.gxylib.coreui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.srwing.gxylib.R;

/**
 * Description: 从左到右 依次是： ivLeftImg（默认gone）、tvLeftText（visible）----
 * ---tvRightText（gone）、ivRightImg（gone）、ivRightIcon（gone）
 * Created by srwing
 * Date: 2022/8/2
 * Email: 694177407@qq.com
 */
public class KeyValueView extends ConstraintLayout {

    private AppCompatTextView tvLeftText;
    private AppCompatTextView tvRightText;
    private AppCompatImageView ivLeftImg;
    private AppCompatImageView ivRightImg;
    private AppCompatImageView ivRightIcon;
    private View vLine;

    public KeyValueView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public KeyValueView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KeyValueView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * <declare-styleable name="c_applib_key_value_icon_view">
     * <!-- left_tv -->
     * <attr name="left_tv_text" format="string|reference" />
     * <attr name="left_tv_color" format="color" />
     * <attr name="left_tv_size" format="dimension" />
     * <attr name="left_tv_visibility" format="enum">
     * <enum name="visible" value="0" />
     * <enum name="invisible" value="1" />
     * </attr>
     * <!-- right_tv -->
     * <attr name="right_tv_text" format="string|reference" />
     * <attr name="right_tv_color" format="color" />
     * <attr name="right_tv_size" format="dimension" />
     * <attr name="right_tv_visibility" format="enum">
     * <enum name="visible" value="0" />
     * <enum name="invisible" value="1" />
     * </attr>
     * <!-- left_img -->
     * <attr name="left_img_visibility" format="enum">
     * <enum name="visible" value="0" />
     * <enum name="invisible" value="1" />
     * </attr>
     * <attr name="left_img_src" format="reference" />
     * <!-- right_img -->
     * <attr name="right_img_visibility" format="enum">
     * <enum name="visible" value="0" />
     * <enum name="invisible" value="1" />
     * </attr>
     * <attr name="right_img_src" format="reference" />
     * <!-- right_icon -->
     * <attr name="right_icon_visibility" format="enum">
     * <enum name="visible" value="0" />
     * <enum name="invisible" value="1" />
     * </attr>
     * <attr name="right_icon_src" format="reference" />
     */

    private String textLeftTv, textRightTv;
    private int colorLeftTv, colorRightTv, colorLine;
    private float sizeLeftTv, sizeRightTv;

    private int visibleLeftTv, visibleRightTv, visibleLeftImg, visibleRightImg, visibleRightIcon, visibleLine;
    private Drawable drawableLeftImg, drawableRightImg, drawableRightIcon;

    public final int VISIBLE = 0x00000000;
    public final int INVISIBLE = 0x00000004;
    public final int GONE = 0x00000008;


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_key_value, this);
        ivLeftImg = view.findViewById(R.id.iv_left_img);
        tvLeftText = view.findViewById(R.id.tv_left_text);
        ivRightIcon = view.findViewById(R.id.iv_right_icon);
        ivRightImg = view.findViewById(R.id.iv_right_img);
        tvRightText = view.findViewById(R.id.tv_right_text);
        vLine = view.findViewById(R.id.v_line);

        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.c_applib_key_value_icon_view);

            textLeftTv = a.getString(R.styleable.c_applib_key_value_icon_view_left_tv_text);
            colorLeftTv = a.getColor(R.styleable.c_applib_key_value_icon_view_left_tv_color, Color.BLACK);
            sizeLeftTv = a.getDimensionPixelSize(R.styleable.c_applib_key_value_icon_view_left_tv_size, ConvertUtils.sp2px(16));
            visibleLeftTv = a.getInt(R.styleable.c_applib_key_value_icon_view_left_tv_visibility, VISIBLE);//显示位置

            textRightTv = a.getString(R.styleable.c_applib_key_value_icon_view_right_tv_text);
            colorRightTv = a.getColor(R.styleable.c_applib_key_value_icon_view_right_tv_color, Color.BLACK);
            sizeRightTv = a.getDimensionPixelSize(R.styleable.c_applib_key_value_icon_view_right_tv_size, ConvertUtils.sp2px(16));
            visibleRightTv = a.getInt(R.styleable.c_applib_key_value_icon_view_right_tv_visibility, GONE);//显示位置

            drawableLeftImg = a.getDrawable(R.styleable.c_applib_key_value_icon_view_left_img_src);
            visibleLeftImg = a.getInt(R.styleable.c_applib_key_value_icon_view_left_img_src, GONE);//显示位置

            drawableRightImg = a.getDrawable(R.styleable.c_applib_key_value_icon_view_right_img_src);
            visibleRightImg = a.getInt(R.styleable.c_applib_key_value_icon_view_right_img_visibility, GONE);//显示位置

            drawableRightIcon = a.getDrawable(R.styleable.c_applib_key_value_icon_view_right_icon_src);
            visibleRightIcon = a.getInt(R.styleable.c_applib_key_value_icon_view_right_icon_visibility, VISIBLE);//显示位置

            visibleLine = a.getInt(R.styleable.c_applib_key_value_icon_view_v_line_visible, VISIBLE);//显示位置
            colorLine = a.getInt(R.styleable.c_applib_key_value_icon_view_v_line_color, VISIBLE);//显示位置

            a.recycle();
        }

        if (tvLeftText != null) {
            if (!TextUtils.isEmpty(textLeftTv)) {
                tvLeftText.setText(textLeftTv);
            }
            tvLeftText.setTextColor(colorLeftTv);
            tvLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeLeftTv);
            tvLeftText.setVisibility(visibleLeftTv);
        }

        if (tvRightText != null) {
            if (!TextUtils.isEmpty(textRightTv)) {
                tvRightText.setText(textRightTv);
            }
            tvRightText.setTextColor(colorRightTv);
            tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeRightTv);
            tvRightText.setVisibility(visibleRightTv);
        }

        if (ivLeftImg != null) {
            if (drawableLeftImg != null) {
                ivLeftImg.setImageDrawable(drawableLeftImg);
            }
            ivLeftImg.setVisibility(visibleLeftImg);
        }
        if (ivRightImg != null) {
            if (drawableRightImg != null) {
                ivRightImg.setImageDrawable(drawableRightImg);
            }
            ivRightImg.setVisibility(visibleRightImg);
        }
        if (ivRightIcon != null) {
            if (drawableRightIcon != null) {
                ivRightIcon.setImageDrawable(drawableRightIcon);
            }
            ivRightIcon.setVisibility(visibleRightIcon);
        }
        if(vLine!=null){
            vLine.setBackgroundColor(colorLine);
            vLine.setVisibility(visibleLine);
        }
    }


    public void setLeftImgVisible(int visible) {
        if (ivLeftImg == null) return;
        ivLeftImg.setVisibility(visible);
    }

    public void setLeftImgReource(int reource) {
        if (ivLeftImg == null) return;
        ivLeftImg.setImageResource(reource);
    }

    public void setRightImgVisible(int visible) {
        if (ivRightImg == null) return;
        ivRightImg.setVisibility(visible);
    }

    public void setRightImgReource(int reource) {
        if (ivRightImg == null) return;
        ivRightImg.setImageResource(reource);
    }


    public void setRightIconVisible(int visible) {
        if (ivRightIcon == null) return;
        ivRightIcon.setVisibility(visible);
    }

    public void setRightIconReource(int reource) {
        if (ivRightIcon == null) return;
        ivRightIcon.setImageResource(reource);
    }

    public void setTvLeftTextVisible(int visible) {
        if (tvLeftText == null) return;
        tvLeftText.setVisibility(visible);
    }

    public void setTvLeftTextStr(String text) {
        if (tvLeftText == null || TextUtils.isEmpty(text)) return;
        tvLeftText.setText(text);
    }

    public void setTvRightTextVisible(int visible) {
        if (tvRightText == null) return;
        tvRightText.setVisibility(visible);
    }

    public void setTvRightTextStr(String text) {
        if (tvRightText == null || TextUtils.isEmpty(text)) return;
        tvRightText.setText(text);
    }
}
