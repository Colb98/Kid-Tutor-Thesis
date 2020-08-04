package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.views.base.Sprite;

public class RepeatForever extends Action {
    Action toRepeat;
    public RepeatForever(Action a){
        super(0);
        toRepeat = a;
        toRepeat.addOnFinishedCallback(new Runnable() {
            @Override
            public void run() {
                toRepeat.start();
            }
        });
    }

    @Override
    public void start() {
        super.start();
        toRepeat.start();
    }

    @Override
    public Action clone() {
        return new RepeatForever(toRepeat);
    }

    @Override
    public void updateEveryFrame(float dt, Sprite sprite) {
        if(timeElapsed == 0){
            this.sprite = sprite;
        }
        if(!started)
            return;

        if(running){
            timeElapsed += dt;
        }
        else{
            timeWaiting += dt;
            if(timeWaiting >= timeToWait){
                timeElapsed += timeWaiting - timeToWait;
                timeWaiting = timeToWait;
                running = true;
            }
        }
//        Log.d("repeeat", "update with dt: " + dt);
        toRepeat.updateEveryFrame(dt, sprite);
    }

    @Override
    public void forceFinish(Sprite sprite) {
        toRepeat.removeAllCallbacks();
        onFinished();
        sprite.removeAction(this);
        running = false;
    }
}
