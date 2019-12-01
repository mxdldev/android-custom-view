package com.mxdl.customview.thread;

import android.os.AsyncTask;
import android.util.Log;

<<<<<<< HEAD
/**
 * Description: <MyAsyncTask><br>
 * Author:      mxdl<br>
 * Date:        2019/11/24<br>
=======
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description: <MyAsyncTask><br>
 * Author:      mxdl<br>
 * Date:        2019/11/25<br>
>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyAsyncTask extends AsyncTask<String,Integer,String> {
<<<<<<< HEAD
    @Override
    protected String doInBackground(String... strings) {
        Log.v("MYTAG","doInBackground start;currThread:"+Thread.currentThread().getName());

        return strings[0];
    }

    @Override
    protected void onPostExecute(String s) {
        Log.v("MYTAG","onPostExecute start;currThread:"+Thread.currentThread().getName()+";result:"+s);
    }
=======
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

>>>>>>> 75c4ddacbcbe9ad40d193590d1449f1fd1c37ad1
}
