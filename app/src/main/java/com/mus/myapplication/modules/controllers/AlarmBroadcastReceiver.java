package com.mus.myapplication.modules.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mus.myapplication.modules.classes.Utils;

public class AlarmBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarmed", Toast.LENGTH_SHORT).show();
        Director.getInstance().showNotification(context);
    }

}