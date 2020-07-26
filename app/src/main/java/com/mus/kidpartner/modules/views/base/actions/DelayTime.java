package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;

public class DelayTime extends Action {
    public DelayTime(float duration){
        super(duration);
    }

    public DelayTime(float duration, Runnable callback){
        super(duration);
        addOnFinishedCallback(callback);
    }

    @Override
    public Action clone() {
        DelayTime ans = new DelayTime(duration);
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
    public void forceFinish(Sprite sprite) {
        running = false;
    }
}
