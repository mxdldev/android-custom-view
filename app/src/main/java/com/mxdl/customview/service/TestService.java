package com.mxdl.customview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Description: <TestService><br>
 * Author:      mxdl<br>
 * Date:        2019/11/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TestService extends Service{
    MyService bind;
    @Override
    public void onCreate() {
        bind = new MyService();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return bind;
    }
    class MyService extends Binder implements IMyService{

        @Override
        public void test() {
            Log.v("MYTAG","hello world...");
        }
    }
}
