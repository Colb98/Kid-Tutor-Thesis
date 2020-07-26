package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;
import java.util.Map;

public abstract class Action {
    protected float duration;
    protected float trueTimeElapsed;
    protected float timeElapsed;
    protected float timeWaiting;
    protected float timeToWait;
    protected boolean paused = false;
    protected boolean running = false;
    protected boolean started = false;
    protected EaseFunction easeFunction = null;
    protected Sprite sprite = null;
    protected Map<String, Runnable> callbacks;

    public static EaseFunction EaseIn = new EaseIn();
    public static EaseFunction EaseOut = new EaseOut();

    public Action(float duration){
        this.duration = duration;
        this.timeToWait = 0;
        timeElapsed = 0;
        trueTimeElapsed = 0;
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

    public abstract Action clone();

    public void setEaseFunction(EaseFunction f){
        easeFunction = f;
    }

    public void reset(){
        trueTimeElapsed = 0;
        timeElapsed = 0;
        timeWaiting = 0;
    }

    public void pause(){
        paused = true;
    }

    public void resume(){
        paused = true;
    }

    public boolean isPaused(){
        return paused;
    }

    public abstract void forceFinish(Sprite sprite);

    public void updateEveryFrame(float dt, Sprite sprite){
        if(timeElapsed == 0){
            this.sprite = sprite;
        }
        if(!started)
            return;

        if(paused)
            return;

        if(running){
            trueTimeElapsed += dt;

            if(easeFunction != null)
                timeElapsed = easeFunction.getEaseTime(trueTimeElapsed, duration);
            else
                timeElapsed = trueTimeElapsed;

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
        if(timeElapsed > 0 || timeWaiting > 0){
            reset();
        }
        started = true;
    }
}
