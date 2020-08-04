package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;


public class ScaleTo extends Action {
    public float lastScale;
    private float firstScale;

    public ScaleTo(float duration, float scale) {
        super(duration);
        lastScale = scale;
        firstScale = 1;
    }

    public ScaleTo(float duration, float timeToWait, float scale) {
        super(duration, timeToWait);
        lastScale = scale;
        firstScale = 1;
    }

    @Override
    public Action clone() {
        ScaleTo ans = new ScaleTo(duration, lastScale);
        ans.firstScale = firstScale;
        ans.timeToWait = timeToWait;
        ans.timeElapsed = 0;
        ans.easeFunction = easeFunction;
        ans.trueTimeElapsed = 0;
        ans.timeWaiting = 0;
        ans.callbacks = new HashMap<>();
        for(String s : callbacks.keySet()){
            ans.callbacks.put(s, callbacks.get(s));
        }
        return ans;
    }

    public void setLastScale(float lastScale) {
        this.lastScale = lastScale;
    }

    @Override
    public void updateEveryFrame(float dt, Sprite sprite) {
        if(timeElapsed == 0){
            firstScale = sprite.getScale();
        }
        super.updateEveryFrame(dt, sprite);
        if(!started) return;
        if(running){
            float scale = firstScale + (timeElapsed/duration)*(lastScale - firstScale);
            sprite.setScale(scale);
        }
    }

    @Override
    public void forceFinish(Sprite sprite) {
        if(timeElapsed + timeWaiting > duration + timeToWait)
            return;
        running = true;
        timeElapsed = duration;
        sprite.setScale(lastScale);
    }
}
