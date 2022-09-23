package com.srwing.gxylib.coreui.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Houqixin on 2018/11/30 11:15
 * E-Mail Address：houqixin@foryou56.com
 *
 * 自带清理Edit内容
 */
public class GolaxyTextWatcherWithClear implements TextWatcher {

    private final EditText mEdit;
    private final View mClearView;

    public GolaxyTextWatcherWithClear(EditText edit, View clearView) {
        this.mEdit = edit;
        this.mClearView = clearView;
        clearView.setVisibility(View.GONE);
        mClearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.setText("");
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
       if(s!=null&&s.length()>0){
           mClearView.setVisibility(View.VISIBLE);
       }else{
           mClearView.setVisibility(View.GONE);
       }
    }
}
