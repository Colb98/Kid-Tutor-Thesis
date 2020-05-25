package com.mus.myapplication.modules.views.base.actions;

import android.util.Log;

import com.mus.myapplication.modules.views.base.Sprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sequence extends Action{
    List<Action> actions;
    int curActionIndex = 0;

    public Sequence(List<Action> actions){
        super(0);
        this.actions = actions;
        setupCallbacks(this.actions);
    }

    public Sequence(Action[] actions){
        super(0);
        this.actions = new ArrayList<>();
        Collections.addAll(this.actions, actions);
        setupCallbacks(this.actions);
    }

    private void setupCallbacks(List<Action> actions) {
        int i = 0;
        for(Action action : actions){
            final int index = ++i;
            if(index < actions.size()){
                action.addOnFinishedCallback(new Runnable() {
                    @Override
                    public void run() {
//                        Log.d("Sequence", "run action index: " + index);
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
        if(!running) return;
        if(!started) return;
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
