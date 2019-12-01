package com.mxdl.customview.thread;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Description: <MyIntentService><br>
 * Author:      mxdl<br>
 * Date:        2019/11/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v("MYTAG","onHandleIntent currThread:"+Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
