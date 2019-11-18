package com.mxdl.customview.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Description: <MyReceiver><br>
 * Author:      mxdl<br>
 * Date:        2019/11/18<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("MYTAG","onReceive start...");
    }
}
