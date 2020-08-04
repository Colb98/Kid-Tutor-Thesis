package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;

public class RotateTo extends Action {
    private float startAngle;
    private float endAngle;

    public RotateTo(float duration, float angleRads){
        super(duration);
        this.endAngle = angleRads;
    }

    @Override
    public Action clone() {
        RotateTo ans = new RotateTo(duration, endAngle);
        ans.timeToWait = timeToWait;
        ans.timeElapsed = 0;
        ans.trueTimeElapsed = 0;
        ans.easeFunction = easeFunction;
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
            startAngle = Utils.degreeToRad(sprite.getRotation());
//            Log.d("MOVE TO", "total move: " + destination.subtract(startPoint));
        }
        super.updateEveryFrame(dt, sprite);

        if(!started) return;

        if(running){
//            Log.d("MOVE TO", "destination: " + destination + ", start: " + startPoint);
//            Log.d("MOVE TO", "fractal: " + timeElapsed);
//            Log.d("MOVE TO", "d move: " + (destination.subtract(startPoint)).product(timeElapsed/duration));
            float rotation = (endAngle-startAngle)*(timeElapsed/duration) + startAngle;
            sprite.setRotation(Utils.radToDegree(rotation));
        }
    }

    @Override
    public void forceFinish(Sprite sprite) {
        timeElapsed = duration;
        sprite.setRotation(Utils.radToDegree(endAngle));
    }
}
