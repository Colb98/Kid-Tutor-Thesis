package com.mus.myapplication.modules.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vuforia.engine.ImageTargets.ImageTargets;
import com.mus.myapplication.MainActivity;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;

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

    public void loadScene(GameScene scene){
        synchronized (instance){
            if(mainGameView != null){
                mainGameView.releaseCurrentScene();
                mainGameView.setCurrentScene(scene);
            }
        }
    }

    public void runActivity(Class activityClass){
        try{
            if(activityClass.equals(ImageTargets.class)){
                Log.d("HIHIHI", "start activity");
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
}
