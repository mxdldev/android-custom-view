package com.mxdl.customview.thread;

import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: <MyAsyncTask><br>
 * Author:      mxdl<br>
 * Date:        2019/11/25<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyAsyncTask extends AsyncTask<String,Integer,String> {
    public String taskName;

    public MyAsyncTask(String taskName) {
        super();
        this.taskName = taskName;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return taskName;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.e("MYTAG", "result:"+result);
    }

}
