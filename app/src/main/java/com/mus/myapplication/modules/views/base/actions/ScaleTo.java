package com.mus.myapplication.modules.views.base.actions;

import android.util.Log;

import com.mus.myapplication.modules.views.base.Sprite;

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
