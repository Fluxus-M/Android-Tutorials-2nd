package com.practice.sample.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private final MyServiceBinder myServiceBinder = new MyServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myServiceBinder;
    }

    public class MyServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
