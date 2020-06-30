package com.mus.myapplication.modules.views.base;

public abstract class TestScene extends GameScene {
    protected float timeRemain;
    public abstract void onTimeOut();

    public TestScene(GameView parent){
        super(parent);
    }
}
