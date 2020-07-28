package com.mus.kidpartner.modules.controllers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.models.common.Achievement;
import com.vuforia.engine.ImageTargets.ImageTargets;
import com.mus.kidpartner.MainActivity;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;

import java.util.List;
import java.util.Map;

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

    public void loadSaveFiles(){
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        Log.d("a", "get pref");
        if(sharedPreferences.contains("user")){
            Log.d("a", "has pref");
            Map<String, ?> data = sharedPreferences.getAll();

            if(data == null) {
                createNewUser();
                return;
            }

            String saveID = (String)data.get("user");
            String androidId = Settings.Secure.getString(mainActivity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.d("save ID", saveID);
            Log.d("device ID", androidId);
            if(!saveID.equals(androidId)){
                // Create new user
                // No need to load more
                createNewUser();
                return;
            }
            try{
                Sounds.setMusicVolume((Float) data.get("volumeM"));
                Sounds.setSoundVolume((Float) data.get("volumeS"));
                Integer achievementCount = (Integer) data.get("achievement");
                if(achievementCount != null) {
                    for(int i=0;i<achievementCount;i++){
                        String cate = (String) data.get("cate_"+i);
                        int level = (Integer) data.get("level_"+i);
                        long time = (Long) data.get("time_"+i);
                        AchievementManager.getInstance().setAchieved(cate, level, time);
                    }
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private void createNewUser(){
        // Do nothing
    }

    public void resetData(){
        saveData(true);
    }

    public void saveData(){
        Log.d("a", "save pref A");
        saveData(false);
    }

    public void saveSetting(){
        Log.d("a", "save pref s");
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String androidId = Settings.Secure.getString(mainActivity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        editor.putString("user", androidId);
        editor.putFloat("volumeM", Sounds.getMusicVolume());
        editor.putFloat("volumeS", Sounds.getSoundVolume());

        editor.apply();
    }
    public void saveAchievement(int index, Achievement a){
        Log.d("a", "save pref a");
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String androidId = Settings.Secure.getString(mainActivity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        editor.putString("user", androidId);

        List<Achievement> achievements = AchievementManager.getInstance().getAchieved();
        editor.putInt("achievement", achievements.size());
        editor.putString("cate_"+index, a.getCategory());
        editor.putInt("level_"+index, a.getLevel());
        editor.putLong("time_"+index, a.getAchievedTimestamp());

        editor.apply();
    }

    public void saveData(boolean reset){
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        String androidId = Settings.Secure.getString(mainActivity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        editor.putString("user", androidId);
        editor.putFloat("volumeM", Sounds.getMusicVolume());
        editor.putFloat("volumeS", Sounds.getSoundVolume());

        if(!reset){
            // Achievement
            List<Achievement> achievements = AchievementManager.getInstance().getAchieved();
            editor.putInt("achievement", achievements.size());
            for(int i=0;i<achievements.size();i++){
                Achievement a = achievements.get(i);
                editor.putString("cate_"+i, a.getCategory());
                editor.putInt("level_"+i, a.getLevel());
                editor.putLong("time_"+i, a.getAchievedTimestamp());
            }
        }

        editor.apply();
    }
}
