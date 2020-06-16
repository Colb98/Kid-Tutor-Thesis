package com.mus.myapplication.modules.views;

import android.content.Context;
import android.util.Log;

import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.spriteanimation.MonsterCat;
import com.mus.myapplication.spriteanimation.MonsterRabbit;

public class MonsterScene extends GameScene {
    private static final String LOGTAG = "MonsterScene";
    public MonsterScene() {
        super();
        Log.d(LOGTAG, "MainScene inited");
        initSections();
    }

//    public MonsterScene(GameView parent) {
//        super(parent);
//        Log.d(LOGTAG, "MainScene inited");
//        initSections();
//    }

    private void initSections(){
//        SectionSprite cat = new SectionSprite(this);
//        SectionSprite rabbit = new SectionSprite (this);
//
//        cat.setName("CAT");
//        rabbit.setName("RABBIT");
//
////        this.addChild(smolRabbit);
//
//
//        try {
//            cat.setSpriteAnimation(MonsterCat.IDLE, 12);
//            rabbit.setSpriteAnimation(MonsterRabbit.IDLE, 12);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        cat.setPosition(0, -50);
//        Log.d("LOL", "DS");
//        cat.setScale(1f);
//
//        final Context ref = this.getContext();
//        final MonsterScene curScene = this;
//        cat.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                // Mở activity mới
////                Director.getInstance().loadScene(new MainScene(curScene.getGameParent()));
//            }
//        });
//        rabbit.setPosition(50, 100);
//        rabbit.setScale(0.3f);

    }
}
