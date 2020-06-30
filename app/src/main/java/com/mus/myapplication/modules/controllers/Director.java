package com.mus.myapplication.modules.controllers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.mus.myapplication.R;
import com.vuforia.engine.ImageTargets.ImageTargets;
import com.mus.myapplication.MainActivity;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;

import androidx.core.app.NotificationCompat;

public class Director {
    private static Director instance = null;
    private GameView mainGameView = null;
    private Context context = null;
    private MainActivity mainActivity = null;

    private Director(){

    }

    public Context getContext(){
        synchronized (instance){
            return context;
        }
    }

    public void setMainGameView(GameView gv){
        synchronized (instance) {
            mainGameView = gv;
            context = gv.getContext();
        }
    }

    public GameView getMainView(){
        return mainGameView;
    }

    public void setMainActivity(MainActivity a){
        mainActivity = a;
    }

    public static Director getInstance(){
        if(instance == null){
            instance = new Director();
        }
        synchronized (instance){
            return instance;
        }
    }

    public GameScene loadScene(GameScene scene){
        synchronized (instance){
            if(mainGameView != null){
//                mainGameView.releaseCurrentScene();
                mainGameView.setCurrentScene(scene);
                return scene;
            }
        }
        return null;
    }

    public void runActivity(Class activityClass){
        try{
            if(activityClass.equals(ImageTargets.class)){
//                Log.d("HIHIHI", "start activity");
                mainActivity.startImageTargetsActivity();
            }
            else{
                Intent intent = new Intent(context, activityClass);
                context.startActivity(intent);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startAlarmBroadcastReceiver(long delay) {
        Intent _intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // Remove any previous pending intent.
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
    }

    public void startAlarmBroadcastReceiverByTimestamp(long timestamp) {
        Intent _intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // Remove any previous pending intent.
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
    }

    public void showNotification(Context context){
        if(context == null){
            context = this.context;
        }
        String CHANNEL_ID = "my_app";
        Notification notification;
        NotificationCompat.Builder mBuilder;
        CharSequence name = context.getResources().getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
            mBuilder = new NotificationCompat.Builder(context)
//                .setContentText("4")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setLights(Color.RED, 300, 300)
                    .setChannelId(CHANNEL_ID)
                    .setContentTitle(context.getResources().getString(R.string.NOTIFY_TEST));
        }
        else{
            mBuilder = new NotificationCompat.Builder(context)
//                .setContentText("4")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setLights(Color.RED, 300, 300)
                    .setContentTitle(context.getResources().getString(R.string.NOTIFY_TEST));
        }

        mBuilder.setContentText(context.getResources().getString(R.string.NOTIFY_CONTENT) + " " + context.getResources().getString(R.string.NOTIFY_FOO));//You have new pending delivery.
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(11, mBuilder.build());
    }


}
