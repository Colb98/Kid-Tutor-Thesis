package com.mus.kidpartner.modules.views.base.actions;

import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Sequence extends Action{
    List<Action> actions;
    int curActionIndex = 0;

    public Sequence(List<Action> actions){
        super(0);
        this.actions = actions;
        setupCallbacks(this.actions);
        duration = 0;
        for(Action a : actions){
            duration += a.duration;
        }
    }

    public Sequence(Action... actions){
        super(0);
        this.actions = new ArrayList<>();
        Collections.addAll(this.actions, actions);
        setupCallbacks(this.actions);
        duration = 0;
        for(Action a : actions){
            duration += a.duration;
        }
    }


    @Override
    public Action clone() {
        Sequence ans = new Sequence(actions);
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

    private void setupCallbacks(final List<Action> actions) {
        int i = 0;
        for(Action action : actions){
            final int index = ++i;
            if(index < actions.size()){
                action.addOnFinishedCallback(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("Sequence", "run action index: " + index + ". class: " + actions.get(index).getClass().getName());
                        runAction(index);
                    }
                });
            }
            else{
                action.addOnFinishedCallback(new Runnable() {
                    @Override
                    public void run() {
                        running = false;
                        onFinished();
                    }
                });
            }
        }
    }

    private void runAction(int index){
        actions.get(index).start();
        curActionIndex = index;
    }

    @Override
    public void updateEveryFrame(float dt, Sprite sprite) {
//        Log.d("sequence", "update " + running + " " + started);
        if(!running) return;
        if(!started) return;
//        Log.d("sequence","play cur: " + curActionIndex);
        actions.get(curActionIndex).updateEveryFrame(dt, sprite);
    }

    @Override
    public void start() {
        super.start();
        running = true;
        runAction(0);
    }

    @Override
    public void forceFinish(Sprite sprite) {
        for(Action action : actions){
            action.forceFinish(sprite);
        }
    }
}
