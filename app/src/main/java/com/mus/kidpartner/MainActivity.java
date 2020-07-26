package com.mus.kidpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.AppAlarmService;
import com.mus.kidpartner.modules.controllers.AreaMusicManager;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.controllers.Sounds;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.ViewContainer;
import com.mus.kidpartner.modules.views.popup.ConfirmPopup;
import com.vuforia.engine.ImageTargets.ImageTargets;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    boolean backPressed = false;
    final static int REQUEST_UPDATE_PLAY_API = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, AppAlarmService.class);
        intent.setAction("com.mus.kidpartner.AlarmIntent");
        startService(intent);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        // Must have. Setting up everything
        Director.getInstance().setMainActivity(this);
        Utils.init(this);
        try {
            Sounds.init(this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        Log.d("INITIATION", "dpRes: " + dpHeight + ", " + dpWidth + "\npx Res: " + Utils.getPxByDp(dpHeight) + ", " + Utils.getPxByDp(dpWidth));

        final GameView mainView = findViewById(R.id.gameView);
        ViewContainer container = (ViewContainer)findViewById(R.id.mainContainer);
        container.isTheRootContainer = true;
        mainView.setViewGroup(container);
        Director.getInstance().setMainGameView(mainView);

        // Main Scene
        Director.getInstance().loadScene(SceneCache.getScene("map"));
//        mainView.addChild(main);
//        main.setName("Map Scene");

        Thread thread = new Thread(){
            private long first = System.currentTimeMillis();
            private long lastUpdate = System.currentTimeMillis();

            @Override
            public void run() {
                while(!isInterrupted()){
                    try{
                        Thread.sleep(6);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long cur = System.currentTimeMillis();
                                if(cur-lastUpdate < 1000/120) {
                                    return;
                                }
                                mainView.update((cur - lastUpdate)/1000f);
                                lastUpdate = cur;
                            }
                        });
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        final int result = instance.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS){
            GameView v = Director.getInstance().getMainView();
            ConfirmPopup popup = new ConfirmPopup(v);
            v.mappingChild(popup, "confirm");
            popup.setMessage("Vui lòng cập nhật Google Play Service bản mới nhất");
            popup.setTextNormal("OK");
            popup.setTextRed("THÔI");
            popup.addOnNormalCallback(new Runnable() {
                @Override
                public void run() {
                    GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, result, REQUEST_UPDATE_PLAY_API);
                }
            });
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AreaMusicManager.getInstance().pauseMusic();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AreaMusicManager.getInstance().resumeMusic();
    }

    public void startImageTargetsActivity(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            Intent intent = new Intent(MainActivity.this, ImageTargets.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        GameScene scene = Director.getInstance().getMainView().getCurrentScene();
        if(scene != null){
            scene.onUserPressBack();
            return;
        }
        if(backPressed)
            super.onBackPressed();
        else{
            backPressed = true;
            Log.d("MainActivity", "Press back one more time to exit");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            }, 1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, ImageTargets.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.INIT_ERROR_NO_CAMERA_ACCESS), Toast.LENGTH_LONG).show();
            }
        }
    }
}