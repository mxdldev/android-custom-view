package com.mxdl.customview.thread;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Description: <MyAsyncTask><br>
 * Author:      mxdl<br>
 * Date:        2019/11/24<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyAsyncTask extends AsyncTask<String,Integer,String> {
    @Override
    protected String doInBackground(String... strings) {
        Log.v("MYTAG","doInBackground start;currThread:"+Thread.currentThread().getName());

        return strings[0];
    }

    @Override
    protected void onPostExecute(String s) {
        Log.v("MYTAG","onPostExecute start;currThread:"+Thread.currentThread().getName()+";result:"+s);
    }
}
