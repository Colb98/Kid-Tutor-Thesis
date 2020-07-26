package com.mus.kidpartner.modules.views.base;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class Slider extends Sprite {
    public SeekBar view;
    View[] subViews;

    public Slider(GameView parent){
        super();
        parent.addChild(this);
    }

    public Slider(){
        super();
    }

    @Override
    protected void afterAddChild() {
        initSlider();
        super.afterAddChild();
    }

    private void initSlider(){
        view = new SeekBar(getContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(900, 100);
        view.setLayoutParams(lp);
        view.setMax(100);
        view.setProgress(1);
        if(subViews == null)
            subViews = new View[1];
        subViews[0] = view;
    }

    public void addOnChangeValueCallback(final Runnable r){
        view.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                r.run();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public View[] getSubViews() {
        return subViews;
    }

    @Override
    public int getSubViewsCount() {
        return 1;
    }
}
