package com.mus.myapplication.modules.views.base.actions;

import android.util.Log;

import com.mus.myapplication.modules.views.base.Sprite;

import java.util.HashMap;
import java.util.Map;

public abstract class Action {
    protected float duration;
    protected float timeElapsed;
    protected float timeWaiting;
    protected float timeToWait;
    protected boolean running = false;
    protected boolean started = false;
    protected Map<String, Runnable> callbacks;

    public Action(float duration){
        this.duration = duration;
        this.timeToWait = 0;
        timeElapsed = 0;
        timeWaiting = 0;
        callbacks = new HashMap<>();
    }

    public Action(float duration, float timeToWait){
        this.duration = duration;
        this.timeToWait = timeToWait;
        timeElapsed = 0;
        timeWaiting = 0;
        callbacks = new HashMap<>();
    }

    public void reset(){
        timeElapsed = 0;
        timeWaiting = 0;
    }

    public abstract void forceFinish(Sprite sprite);

    public void updateEveryFrame(float dt, Sprite sprite){
        if(!started)
            return;

        if(running){
            timeElapsed += dt;
            if(timeElapsed >= duration){
                sprite.removeAction(this);
                running = false;
                onFinished();
            }
        }
        else{
            timeWaiting += dt;
            if(timeWaiting >= timeToWait){
                timeElapsed += timeWaiting - timeToWait;
                timeWaiting = timeToWait;
                running = true;
            }
        }
    }

    public void removeAllCallbacks(){
        callbacks.clear();
    }

    public String addOnFinishedCallback(Runnable callback){
        return addOnFinishedCallback(callback, String.valueOf(callbacks.size()));
    }

    public String addOnFinishedCallback(Runnable callback, String name){
        callbacks.put(name, callback);
        return name;
    }

    public void removeOnFinishedCallback(String name){
        callbacks.remove(name);
    }

    protected void onFinished(){
//        Log.d(this.getClass().getName(), " ON FINISH with callbacks: " + callbacks.values().size());
        for(Runnable callback : callbacks.values()){
            callback.run();
        }
    }

    public void start(){
        started = true;
    }
}
