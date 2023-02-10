package com.example.gxylib.main.v;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.gxylib.R;
import com.example.gxylib.databinding.ActivityMainBinding;
import com.example.gxylib.main.base.BindingActivity;

/**
 * Description:
 * Created srwing
 * Date: 2023/2/9
 * Email: 694177407@qq.com
 */
public class TestActivity3 extends BindingActivity<ActivityMainBinding> {
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
    @Override
    protected int getTitleLayout() {
        return R.layout.b_base_title_bar;
    }

    @Override
    protected void setTitleContent(View view) {
        // 添加标题的一些事件
        AppCompatTextView tv = view.findViewById(R.id.titleText);
        tv.setText("页面3");
    }
    @Override
    protected void initViewData() {
        super.initViewData();
        dataBinding.tvText.setText("这是页面3 data: "+getIntent().getExtras().get("data"));
        dataBinding.tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data","这是页面3的返回值");
                setResult(100,intent);
                TestActivity3.this.finish();
            }
        });
    }
}
