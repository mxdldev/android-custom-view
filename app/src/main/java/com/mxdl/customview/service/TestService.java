package com.mxdl.customview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mxdl.customview.TestServiceActivity;

/**
 * Description: <TestService><br>
 * Author:      mxdl<br>
 * Date:        2019/11/13<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class TestService extends Service implements IMyService{
    public static String TAG = TestService.class.getSimpleName();
    MyService binder;

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate start...");
        binder = new MyService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "onStartCommand start...");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind start...");
        return binder;
    }

    @Override
    public void test() {
        Log.v(TAG, "hello world...");
    }

    class MyService extends Binder implements IMyService {
        @Override
        public void test() {
            TestService.this.test();
        }
    }
}
