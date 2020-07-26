package com.mus.kidpartner.modules.views.base.actions;

import android.util.Log;

import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;

public class Repeat extends Action {
    int timeToRepeat;
    int curRepeatCount;
    Action toRepeat;
    public Repeat(Action toRepeat, int time){
        super(0);
        this.toRepeat = toRepeat;
        duration = time * toRepeat.duration;
        timeToRepeat = time;
        curRepeatCount = 0;
    }

    @Override
    public Action clone() {
        Repeat ans = new Repeat(toRepeat, timeToRepeat);
        ans.easeFunction = easeFunction;
        ans.timeWaiting = 0;
        ans.callbacks = new HashMap<>();
        for(String s : callbacks.keySet()){
            ans.callbacks.put(s, callbacks.get(s));
        }
        return ans;
    }

    @Override
    public void reset() {
        super.reset();
        curRepeatCount = 0;
        toRepeat.reset();
    }

    @Override
    public void start() {
        super.start();
        toRepeat.start();
    }

    @Override
    public void updateEveryFrame(float dt, Sprite sprite) {
        if(timeElapsed == 0){
            this.sprite = sprite;
        }
        if(!started)
            return;

//        Log.d("repeat", "state: " + running + ". curRepeatCount: " + curRepeatCount + ". Time elapsed: " + timeElapsed + "/" + duration);
        if(running){
            timeElapsed += dt;
            if(curRepeatCount != (int)(timeElapsed/toRepeat.duration)){
                toRepeat.start();
                curRepeatCount = (int)(timeElapsed/toRepeat.duration);
            }
            if(timeElapsed > duration){
                running = false;
                started = false;
                onFinished();
                return;
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
        Log.d("a", "updating for: " + toRepeat.getClass().getName());
        toRepeat.updateEveryFrame(dt, sprite);
    }

    @Override
    public void forceFinish(Sprite sprite) {
        running = false;
        onFinished();
        timeElapsed = duration;
        curRepeatCount = timeToRepeat;
    }
}
