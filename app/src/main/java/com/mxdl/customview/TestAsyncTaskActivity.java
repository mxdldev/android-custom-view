package com.mxdl.customview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.mxdl.customview.thread.MyAsyncTask;
import com.mxdl.customview.thread.MyIntentService;

public class TestAsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_async_task);
//        new MyAsyncTask("AsyncTask1").execute("");
//        new MyAsyncTask("AsyncTask2").execute("");
//        new MyAsyncTask("AsyncTask3").execute("");
//        new MyAsyncTask("AsyncTask4").execute("");
//        new MyAsyncTask("AsyncTask5").execute("");

//        HandlerThread handlerThread = new HandlerThread("handler-thread");
//        handlerThread.start();
//        Handler handler = new Handler(handlerThread.getLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                Log.v("MYTAG","handleMessage what:"+msg.what+";currThread:"+Thread.currentThread().getName());
//            }
//        };
//
//        handler.sendEmptyMessage(0);
//        handler.sendEmptyMessage(1);
        new MyAsyncTask("AsyncTask1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("AsyncTask2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsyncTask("AsyncTask3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("AsyncTask4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        new MyAsyncTask("AsyncTask5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
//        Log.v("MYTAG","onCreate:"+Thread.currentThread().getName());
//        startService(new Intent(this, MyIntentService.class));
//        startService(new Intent(this, MyIntentService.class));
//        startService(new Intent(this, MyIntentService.class));

    }
}
