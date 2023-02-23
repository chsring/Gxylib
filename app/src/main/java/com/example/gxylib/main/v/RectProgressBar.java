package com.example.gxylib.main.v;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.srwing.gxylib.timer.CutDownTimer;
import com.srwing.gxylib.timer.OnCountDownTimerListener;
import com.srwing.t_network.utils.GxyLogger;

/**
 * Description:
 * Created srwing
 * Date: 2023/2/22
 * Email: 694177407@qq.com
 */
public class RectProgressBar extends View {

    private float left;
    private float top;
    private float right;
    private float bottom;

    private Paint paint;    // 画笔
    private float weight;
    private float height;

    private int bgColor = Color.parseColor("#40d43a");    // 随时设置随时变化
    private int color = Color.parseColor("#646464");  // 随时设置随时变化

    private final double PI = 3.1415926;
    //画笔宽度
    private float thickness = 10;

    //圆弧的半径
    private float radius = 20;

    private RectF rectLeftTop, rectLeftBottom, rectRightTop, rectRightBottom;

    private Path path;

    //倒计时 秒
    private float second = 10;

    //速度 = 路程/倒计时
    private float speed = 0;

    private float timesPerMillionSecond = 20;

    private float firstDistance;
    private float secondDistance;
    private float thirdDistance;
    private float forthDistance;
    private float fifthDistance;

    private float sixthDistance;
    private float seventhDistance;
    private float eighthDistance;
    private float ninthDistance;

    private float currentDistance;

    private CutDownTimer cutDownTimer;

    public RectProgressBar(Context context) {
        super(context);
        initData();
    }

    public RectProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public RectProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(thickness);
//        paint.setDither(true);
        rectLeftTop = new RectF();
        rectLeftBottom = new RectF();
        rectRightTop = new RectF();
        rectRightBottom = new RectF();
        path = new Path();
    }


    // 设置背景色，默认绿色
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    // 设置 倒计时的颜色，默认灰色
    public void setColor(int color) {
        this.color = color;
    }

    //设置线条宽度
    public void setThickness(float thickness) {
        this.thickness = thickness;
        if (paint != null) {
            paint.setStrokeWidth(thickness);
        }
    }

    //设置矩形框的圆角
    public void setRadius(float radius) {
        this.radius = radius;
        requestLayout();
    }

    //设置倒计时 ，秒
    public void setSecond(float second) {
        this.second = second;
        //触发 onMeasure ，重新给速度赋值
        requestLayout();
    }


    //设置刷新间隔 毫秒
    public void setTimesPerMillionSecond(float timesPerMillionSecond) {
        this.timesPerMillionSecond = timesPerMillionSecond;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float arcDistance = (float) (radius * 3.14 / 2);
        left = 0 + thickness / 2;
        top = 0 + thickness / 2;
        right = getMeasuredWidth() - thickness / 2;
        bottom = getMeasuredHeight() - thickness / 2;
        weight = right - left;
        height = bottom - top;

        //开始 在顶部 中间的位置
        currentDistance = weight / 2 + thickness / 2;
        //第一段距离 减去圆角的半径
        firstDistance = weight - radius;
        secondDistance = firstDistance + arcDistance;
        thirdDistance = secondDistance + height - 2 * radius;
        forthDistance = thirdDistance + arcDistance;
        fifthDistance = forthDistance + weight - 2 * radius;
        sixthDistance = fifthDistance + arcDistance;
        seventhDistance = sixthDistance + height - 2 * radius;
        eighthDistance = seventhDistance + arcDistance;
        ninthDistance = eighthDistance + weight / 2 - 2 * radius;


        float count = weight * 2 + height * 2;
        speed = (count) / (second * 1000 / timesPerMillionSecond);
        GxyLogger.d("test-Porgress", "count: " + count + "\nsecond:" + second + "\ntimesPerMillionSecond: " + timesPerMillionSecond + "\nspeed: " + speed);
//d
        //左上角圆弧的
        rectLeftTop.left = left;
        rectLeftTop.top = top;
        rectLeftTop.right = left + 2 * radius;
        rectLeftTop.bottom = top + 2 * radius;

        //右上角的圆弧
        rectRightTop.left = right - 2 * radius;
        rectRightTop.right = right;
        rectRightTop.top = top;
        rectRightTop.bottom = top + 2 * radius;

        //左下角的圆弧
        rectLeftBottom.left = left;
        rectLeftBottom.top = bottom - 2 * radius;
        rectLeftBottom.bottom = bottom;
        rectLeftBottom.right = left + 2 * radius;

        //右下角的圆弧
        rectRightBottom.left = right - 2 * radius;
        rectRightBottom.top = bottom - 2 * radius;
        rectRightBottom.right = right;
        rectRightBottom.bottom = bottom;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(left, top + radius);

        path.arcTo(rectLeftTop, 180, 90);
        path.lineTo(right - radius, top);

        path.arcTo(rectRightTop, 270, 90);
        path.lineTo(right, bottom - radius);

        path.arcTo(rectRightBottom, 360, 90);
        path.lineTo(left + radius, bottom);

        path.arcTo(rectLeftBottom, 90, 90);
        path.lineTo(left, top + radius);
        paint.setColor(bgColor);
        canvas.drawPath(path, paint);


        path.reset();
        path.moveTo(left + weight / 2, top);
        GxyLogger.e("test-Porgress", "currentDis- draw:" +
                (currentDistance - weight / 2 - thickness / 2));
        if (currentDistance < firstDistance) {
            path.lineTo(currentDistance, top);
        } else if (currentDistance >= firstDistance && currentDistance < secondDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, getDegreeFromDistance(currentDistance - firstDistance, radius));
        } else if (currentDistance >= secondDistance && currentDistance < thirdDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, currentDistance - secondDistance + radius);

        } else if (currentDistance >= thirdDistance && currentDistance < forthDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, getDegreeFromDistance(currentDistance - thirdDistance, radius));
        } else if (currentDistance >= forthDistance && currentDistance < fifthDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, 90);
            path.lineTo(right - (currentDistance - forthDistance + radius), bottom);

        } else if (currentDistance >= fifthDistance && currentDistance < sixthDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, 90);
            path.lineTo(left + radius, bottom);
            path.arcTo(rectLeftBottom, 90, getDegreeFromDistance(currentDistance - fifthDistance, radius));
        } else if (currentDistance >= sixthDistance && currentDistance < seventhDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, 90);
            path.lineTo(left + radius, bottom);
            path.arcTo(rectLeftBottom, 90, 90);
            path.lineTo(left, bottom - (currentDistance - sixthDistance + radius));
        } else if (currentDistance >= seventhDistance && currentDistance < eighthDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, 90);
            path.lineTo(left + radius, bottom);
            path.arcTo(rectLeftBottom, 90, 90);
            path.lineTo(left, top + radius);
            path.arcTo(rectLeftTop, 180, getDegreeFromDistance(currentDistance - seventhDistance, radius));

        } else if (currentDistance >= eighthDistance) {
            path.lineTo(right - radius, top);
            path.arcTo(rectRightTop, 270, 90);
            path.lineTo(right, bottom - radius);
            path.arcTo(rectRightBottom, 0, 90);
            path.lineTo(left + radius, bottom);
            path.arcTo(rectLeftBottom, 90, 90);
            path.lineTo(left, top + radius);
            path.arcTo(rectLeftTop, 180, 90);
            path.lineTo(left + (currentDistance - eighthDistance) + radius, top);
        }
        currentDistance += speed;
        GxyLogger.i("test-Porgress", "-------------------------------");
        paint.setColor(color);
        canvas.drawPath(path, paint);
    }

    private float getDegreeFromDistance(float distance, float radius) {
        return (float) (distance * 360 / (2 * PI * radius));
    }

    public void reset() {
        //开始 在顶部 中间的位置
        if (cutDownTimer != null) {
            cutDownTimer.stop();
            cutDownTimer.reset();
        }
        requestLayout();
    }

    public void stop() {
        if (cutDownTimer != null) {
            cutDownTimer.stop();
        }
    }

    public void start() {
        if (cutDownTimer == null) {
            cutDownTimer = new CutDownTimer(null, (long) second * 1000, 0, (long) timesPerMillionSecond);
            cutDownTimer.setOnCountDownTimerListener(new OnCountDownTimerListener() {
                @Override
                public void onTick(long millisUntilFinished) {
                    invalidate();
                    GxyLogger.e("test-Porgress", "currentDis- invalidate:" +
                            (currentDistance - weight / 2 - thickness / 2));
                }

                @Override
                public void onFinish() {
                    GxyLogger.i("test-Porgress", "currentDis:" +
                            (currentDistance - weight / 2 - thickness / 2));

                }

                @Override
                public void onCancel() {

                }
            });
        }
        cutDownTimer.start();
    }

}
