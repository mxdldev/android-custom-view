package com.mxdl.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.mxdl.customview.service.IMyService;
import com.mxdl.customview.service.TestService;
import com.mxdl.customview.service.TestService1;

public class TestServiceActivity extends AppCompatActivity {

    private IMyAidlInterface mMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        findViewById(R.id.btn_bind_service).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        //startService(new Intent());
    }

    private void bindService() {
        bindService(new Intent(TestServiceActivity.this, TestService1.class), new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ITest iTest = ITest.Stub.asInterface(service);
            mMyService = IMyAidlInterface.Stub.asInterface(service);
            //mMyService = (IMyAidlInterface) service;
            Log.v("MYTAG","onServiceConnected succ");
            try {
                String result = mMyService.test1("fafafa");
                Log.v("MYTAG",result);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }, Context.BIND_AUTO_CREATE);
    }
}
