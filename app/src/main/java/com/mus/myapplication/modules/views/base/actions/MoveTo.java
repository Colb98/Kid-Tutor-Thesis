package com.mus.myapplication.modules.views.base.actions;

import android.util.Log;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.views.base.Sprite;

import java.util.HashMap;

public class MoveTo extends Action {
    private Point destination;
    private Point startPoint;

    public MoveTo(float duration, Point destination){
        super(duration);
        this.destination = destination;
    }

    public MoveTo(float duration, float timeToWait, Point destination){
        super(duration, timeToWait);
        this.destination = destination;
    }

    public MoveTo(float duration, float x, float y){
        super(duration);
        this.destination = new Point(x,y);
    }

    public MoveTo(float duration, float timeToWait, float x, float y){
        super(duration, timeToWait);
        this.destination = new Point(x, y);
    }

    @Override
    public Action clone() {
        MoveTo ans = new MoveTo(duration, destination);
        ans.timeToWait = timeToWait;
        ans.timeElapsed = 0;
        ans.timeWaiting = 0;
        ans.callbacks = new HashMap<>();
        for(String s : callbacks.keySet()){
            ans.callbacks.put(s, callbacks.get(s));
        }
        return ans;
    }

    @Override
    public void updateEveryFrame(float dt, Sprite sprite) {
        if(timeElapsed == 0){
            startPoint = sprite.getPosition();
//            Log.d("MOVE TO", "total move: " + destination.subtract(startPoint));
        }
        super.updateEveryFrame(dt, sprite);
        if(!started) return;

        if(running){
//            Log.d("MOVE TO", "destination: " + destination + ", start: " + startPoint);
//            Log.d("MOVE TO", "fractal: " + timeElapsed);
//            Log.d("MOVE TO", "d move: " + (destination.subtract(startPoint)).product(timeElapsed/duration));
            Point curPos = startPoint.add((destination.subtract(startPoint)).product(timeElapsed/duration));
            sprite.setPosition(curPos);
        }
    }

    @Override
    protected void onFinished() {
        super.onFinished();
        if(sprite != null){
            sprite.setPosition(destination);
        }
    }

    @Override
    public void forceFinish(Sprite sprite) {
        running = false;
        if(sprite != null){
            sprite.setPosition(destination);
        }
    }
}
