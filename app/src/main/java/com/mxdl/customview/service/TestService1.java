package com.mxdl.customview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.mxdl.customview.IMyAidlInterface;

public class TestService1 extends Service {
    MyService binder;

    @Override
    public void onCreate() {
        Log.v("MYTAG","onCreate");
        binder = new MyService();
    }

    public TestService1() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("MYTAG","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("MYTAG","onBind");
      return   binder;
    }
    class MyService extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void test(String name) throws RemoteException {

        }

        @Override
        public String test1(String name) throws RemoteException {
            return "hahaha:"+name;
        }
    }
}
