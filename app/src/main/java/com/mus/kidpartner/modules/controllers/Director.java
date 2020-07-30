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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.SaveData;
import com.mus.kidpartner.modules.classes.UIManager;
import com.mus.kidpartner.modules.models.common.Achievement;
import com.mus.kidpartner.modules.views.setting.SettingUI;
import com.vuforia.engine.ImageTargets.ImageTargets;
import com.mus.kidpartner.MainActivity;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class Director {
    private static final String LOGTAG = "Director";
    private static Director instance = null;
    private GameView mainGameView = null;
    private Context context = null;
    private MainActivity mainActivity = null;
    private boolean saveDataOnline = false;
    private GoogleSignInClient mGoogleSignInClient = null;
    private GoogleSignInAccount mGoogleSignInAccount = null;
    private SettingUI bindUI;

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
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mainActivity);
        updateSignInState(account);
    }

    private void loadData(GoogleSignInAccount account) {
        // saveDataOnline is being set up in updateSignInState();
        // If there is a Google account signed in then load save data from the firestore
        if(!saveDataOnline){
            SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
            if(sharedPreferences.contains("user")){
                SaveData data = new SaveData();
                data.user = sharedPreferences.getString("user", data.user);
                data.googleId = sharedPreferences.getString("googleId", data.googleId);
                data.volumeS = sharedPreferences.getFloat("volumeS", data.volumeS);
                data.volumeM = sharedPreferences.getFloat("volumeM", data.volumeM);
                data.achievement = sharedPreferences.getInt("achievement", data.achievement);
                for(int i=0;i<data.achievement;i++){
                    SaveData.AchievementData d = new SaveData.AchievementData();
                    d.idx = i;
                    d.cate = sharedPreferences.getString("cate_"+i, d.cate);
                    d.level = sharedPreferences.getInt("level_"+i, d.level);
                    d.time = sharedPreferences.getLong("time_"+i, d.time);
                    data.achievements.add(d);
                }
                Log.d("Load", data.toString());
                SaveData.applySaveData(data);
            }
        }
        // Load from firestore
        else{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(account.getId())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            SaveData data = new SaveData();
                            data.user = document.getString("user");
                            // New user
                            if(data.user == null){
                                saveOnline(SaveData.getSaveData());
                            }
                            else{
                                // TODO: compare local data with online data and ask for which one to keep
                                data.googleId = document.getString("googleId");
                                data.achievement = document.getLong("achievement").intValue();
                                data.volumeS = document.getDouble("volumeS").floatValue();
                                data.volumeM = document.getDouble("volumeM").floatValue();
                                for(int i=0;i<data.achievement;i++){
                                    SaveData.AchievementData d = new SaveData.AchievementData();
                                    d.idx = i;
                                    d.cate = document.getString("cate_"+i);
                                    d.time = document.getLong("time_"+i);
                                    d.level = document.getLong("level_"+i).intValue();
                                    data.achievements.add(d);
                                }

                                Log.d(LOGTAG, "get success: " + data.toString());
                                SaveData.applySaveData(data);

                            }
                        }
                    });
        }

        debugCurrentState();
    }

    public boolean isSignedInGoogleAccount(){
        return saveDataOnline;
    }

    public String getGoogleName(){
        if(mGoogleSignInAccount == null) return null;
        return mGoogleSignInAccount.getDisplayName();
    }

    public void updateSignInState(GoogleSignInAccount account){
        saveDataOnline = account != null;
        if(account != null){
            Log.d("Sign In Google", "sign in with: " + account.getDisplayName());
            Log.d("Sign In Google", "email: " + account.getEmail());
            Log.d("Sign In Google", "ID: " + account.getId());
            mGoogleSignInAccount = account;

            if(bindUI != null){
                bindUI.updateGoogleSignInState();
            }
        }
        loadData(account);
    }

    public void signOut(){
        saveData();
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(mainActivity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mGoogleSignInAccount = null;
                        saveDataOnline = false;
                        updateSignInState(null);
                        if(bindUI != null){
                            bindUI.updateGoogleSignInState();
                        }
                        Log.d(LOGTAG, "signed out");
                        debugCurrentState();
                    }
                });
    }

    public void debugCurrentState(){
        Log.e(LOGTAG, "current state " + SaveData.getSaveData().toString());
    }

    public void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mainActivity.startActivityForResult(signInIntent, MainActivity.RC_SIGN_IN);
    }

    private void createNewUser(){
        // Do nothing
    }

    public void resetData(){
        saveData(true);
        Log.d(LOGTAG, "reset data");
    }

    public void saveData(){
//        Log.d("a", "save pref A");
        saveData(false);
    }

    public void saveSetting(){
//        Log.d("a", "save pref s");
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SaveData data = SaveData.getSaveData();
        editor.putString("user", data.user);
        editor.putString("googleId", data.googleId);
        editor.putFloat("volumeM", data.volumeM);
        editor.putFloat("volumeS", data.volumeS);

        saveOnline(data);

        editor.apply();
    }

    public void saveOnline(final SaveData saveData){
        Log.d(LOGTAG, "try save Online");
        if(saveDataOnline){
            Map<String, Object> data = saveData.getMap();

            Log.e(LOGTAG, "start online");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(mGoogleSignInAccount.getId())
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(LOGTAG, "Add data success. " + saveData.toString());
                            Log.e(LOGTAG, "end online");
                        }
                    });
        }
    }

    public void saveAchievement(int index, Achievement a){
        Log.d("a", "save pref a");
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SaveData data = SaveData.getSaveData();
        editor.putString("user", data.user);
        editor.putString("googleId", data.googleId);

        editor.putInt("achievement", data.achievement);
        editor.putString("cate_"+index, a.getCategory());
        editor.putInt("level_"+index, a.getLevel());
        editor.putLong("time_"+index, a.getAchievedTimestamp());

        Log.d("a", "data: " + data.toString());
        saveOnline(data);
        editor.apply();
    }

    public String getUser(){
        return Settings.Secure.getString(mainActivity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public String getGoogleId(){
        if(mGoogleSignInAccount == null)
            return null;
        return mGoogleSignInAccount.getId();
    }

    public void saveData(boolean reset){
        SharedPreferences sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        SaveData data = SaveData.getSaveData();
        Log.e(LOGTAG, "start offline");
        editor.putString("user", data.user);
        editor.putString("googleId", data.googleId);
        editor.putFloat("volumeM", data.volumeM);
        editor.putFloat("volumeS", data.volumeS);

        if(!reset){
            // Achievement
            editor.putInt("achievement", data.achievement);
            for(int i=0;i<data.achievement;i++){
                SaveData.AchievementData a = data.achievements.get(i);
                editor.putString("cate_"+i, a.cate);
                editor.putInt("level_"+i, a.level);
                editor.putLong("time_"+i, a.time);
            }
        }
        else {
            data.clearAchievementData();
            SaveData.applySaveData(data);
        }
        Log.e(LOGTAG, "end offline");
        Log.d(LOGTAG, "reset: " + reset + ", data: " + data.toString());
        saveOnline(data);

        editor.apply();
    }

    public void bindUIToUpdate(SettingUI ui) {
        this.bindUI = ui;
    }
}
