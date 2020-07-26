package com.mus.kidpartner.modules.controllers;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class AppAlarmService extends Service {
    private final IBinder binder = new LocalBinder();
    public static boolean isServiceRunning = false;

    public class LocalBinder extends Binder {
        public AppAlarmService getService(){
            return AppAlarmService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals("com.mus.kidpartner.AlarmIntent")) {
            isServiceRunning = true;
        }
        else stopMyService();
        return START_STICKY;
    }

    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
