package com.mxdl;

import android.app.Application;

import com.mxdl.customview.util.PixelUtil;

/**
 * Description: <MyApplication><br>
 * Author:      mxdl<br>
 * Date:        2019/10/11<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PixelUtil.init(this);
    }
}
