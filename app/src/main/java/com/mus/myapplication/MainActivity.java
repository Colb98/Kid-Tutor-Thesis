package com.mus.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.AppAlarmService;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ViewContainer;
import com.mus.myapplication.modules.views.scene.MapScene;
import com.mus.myapplication.modules.views.scene.TestMenuScene;
import com.mus.myapplication.modules.views.setting.SettingUI;
import com.vuforia.engine.ImageTargets.ImageTargets;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, AppAlarmService.class);
        intent.setAction("com.mus.myapplication.AlarmIntent");
        startService(intent);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
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
        mainView.setViewGroup((ViewContainer)findViewById(R.id.mainContainer));
        Director.getInstance().setMainGameView(mainView);

        // Main Scene
        MapScene main = new MapScene(mainView);
//        mainView.addChild(main);
        main.setName("Map Scene");

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
                                if(cur - first > 5000){
                                    ((ViewContainer) findViewById(R.id.mainContainer)).getDrawOrder();
                                }
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
    protected void onStop() {
        super.onStop();
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
