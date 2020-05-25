package com.mus.myapplication.modules.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mus.myapplication.ImageTargets;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.SectionSprite;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.spriteanimation.CuteGirl;

public class MainScene extends GameScene {
    private static final String LOGTAG = "MainScene";
    public MainScene(GameView parent) {
        super(parent);
        Log.d(LOGTAG, "MainScene inited");
        initSections();
    }

    private void initSections(){
        SectionSprite home = new SectionSprite(this);
        SectionSprite school = new SectionSprite (this);

        home.setName("HOME");
        school.setName("SCHOOL");

        try {
            home.setSpriteAnimation(CuteGirl.DEAD, 30);
            school.setSpriteAnimation(CuteGirl.DEAD, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }

        home.setScaleX(1.5f);
        home.setScaleY(2f);
        home.setPosition(0, -50);

        final Context ref = this.getContext();
        home.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                // Mở activity mới
                Log.d("TEST:", "ON CLICK HOME");
                Intent i = new Intent(ref, ImageTargets.class);
                ref.startActivity(i);
            }
        });
        school.setPosition(600, 57);
    }
}
