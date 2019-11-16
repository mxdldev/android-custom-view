package com.mxdl.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import com.mxdl.customview.service.IMyService;
import com.mxdl.customview.service.TestService;
public class TestServiceActivity extends AppCompatActivity {
    public static String TAG = TestServiceActivity.class.getSimpleName();
    private IMyService mMyService;
    private ServiceConnection mConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        findViewById(R.id.btn_bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        //startService(new Intent(TestServiceActivity.this, TestService.class));
    }

    private void bindService() {
        if(mConn == null)
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.v(TAG, "onServiceConnected start...");
                mMyService = (IMyService) service;
                mMyService.test();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent(TestServiceActivity.this, TestService.class), mConn, Context.BIND_AUTO_CREATE);
    }
}
