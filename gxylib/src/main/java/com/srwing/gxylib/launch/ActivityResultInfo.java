package com.srwing.gxylib.launch;

import android.content.Intent;
import android.net.Uri;

/**
 * Description:
 * Created by srwing
 * Date: 2022/8/2
 * Email: 694177407@qq.com
 */
public class ActivityResultInfo {
    public int resultCode;
    public Intent data;
    public Uri uri ;
    ActivityResultInfo(int resultCode, Intent data) {
        this.resultCode = resultCode;
        this.data = data;
    }
    public Uri getUri() {
        return uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
