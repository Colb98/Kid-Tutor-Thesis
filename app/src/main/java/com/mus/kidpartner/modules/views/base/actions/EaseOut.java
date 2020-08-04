package com.mus.kidpartner.modules.views.base.actions;

public class EaseOut implements EaseFunction {
    @Override
    public float getEaseTime(float current, float duration) {
        return (float)(Math.sqrt(current) * Math.sqrt(duration));
    }
}
