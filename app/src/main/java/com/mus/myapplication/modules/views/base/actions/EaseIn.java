package com.mus.myapplication.modules.views.base.actions;

public class EaseIn implements EaseFunction {
    @Override
    public float getEaseTime(float current, float duration) {
        return current * current / duration;
    }
}
