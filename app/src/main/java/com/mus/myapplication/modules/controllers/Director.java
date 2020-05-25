package com.mus.myapplication.modules.controllers;

import android.content.Context;
import android.util.Log;

import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;

public class Director {
    private static Director instance = null;
    private GameView mainGameView = null;

    private Director(){

    }

    public Context getContext(){
        synchronized (instance){
            return mainGameView.getContext();
        }
    }

    public void setMainGameView(GameView gv){
        synchronized (instance) {
            mainGameView = gv;
        }
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
}
