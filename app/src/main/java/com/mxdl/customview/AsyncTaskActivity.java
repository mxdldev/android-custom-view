package com.mxdl.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.mxdl.customview.thread.MyAsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        AsyncTask<String, Integer, String> execute = new MyAsyncTask().execute("www.baidu.com");
        new MyAsyncTask().execute("www.baidu.com1");
        new MyAsyncTask().execute("www.baidu.com2");
        new MyAsyncTask().execute("www.baidu.com3");
        new MyAsyncTask().execute("www.baidu.com4");

//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        for(int i = 0; i < 10; i++){
//            final int finalI = i;
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Log.v("MYTAG","run start;currThread:"+Thread.currentThread().getName()+";i:"+ finalI);
//                }
//            });
//        }
        test((String)null);
    }
    public void test(String str){

    }
}
