package com.mus.kidpartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
    public final static int RC_SIGN_IN = 11;
    final static String LOGTAG = "Main Activity";

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
//        initializeFirestore();


        // Must have. Setting up everything
        Director.getInstance().setMainActivity(this);
        try {
            Sounds.init(this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Utils.init(this);
        Director.getInstance().loadSaveFiles();
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
    protected void onStart() {
        super.onStart();
    }

    private void initializeFirestore() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(LOGTAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(LOGTAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();
        Director.getInstance().saveData();
    }

    @Override
    protected void onStop() {
        Log.d(LOGTAG, "onStop");
        Director.getInstance().saveData();
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(LOGTAG, "onPause");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            updateUI(account);
            Director.getInstance().updateSignInState(account);
        } catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(LOGTAG, "signInResult:failed code=" + e.getStatusCode());
            Director.getInstance().updateSignInState(null);
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
