package com.mus.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ViewContainer;
import com.mus.myapplication.modules.views.scene.TestMenuScene;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Must have. Setting up everything
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
        TestMenuScene main = new TestMenuScene();
        mainView.addChild(main);
        main.setName("Scroll Scene");

        Thread thread = new Thread(){
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
}
