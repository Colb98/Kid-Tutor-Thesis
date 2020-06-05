package com.mus.myapplication.modules.views.scene;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ScrollView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.MoveTo;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;

public class TestMenuScene extends GameScene {
    public TestMenuScene(){
        super();
        initScene();
    }

    public void initScene(){
        final ScrollView scrollView = new ScrollView(this, 1400, 700);
        scrollView.setContentSize(3000, 1400);
        scrollView.setName("scrollView");
        final Sprite bg = new Sprite(scrollView);
        bg.setSpriteAnimation(new int[]{R.drawable.bg_sasuke});
        bg.setAnchorPoint(0.5f,0.5f);
        bg.setSwallowTouches(false);
        bg.setPosition(0,0);

        bg.setName("bg");

        final Sprite imgView = new Sprite( bg);
        imgView.setName("Debug");
        imgView.setSpriteAnimation(new int[]{R.drawable.cat_1});
        imgView.setAnchorPoint(0.5f, 0.5f);
        imgView.setScale(1f);

//        GameImageView imgView2 = new GameImageView(bg);
//        imgView2.init(R.drawable.dead_01);
//        imgView2.setContentSize(300, 250);
//
//        Button test = new Button(bg);
//        test.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                scrollView.setViewSize(2000, 1000);
//            }
//        });
//        test.setPosition(300, 400);
//
//        Button test2 = new Button(bg);
//        test2.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                scrollView.setContentSize(2000, 1000);
//            }
//        });
//        test2.setPosition(500, 400);
//
//        Button test3 = new Button(bg);
//        test3.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                scrollView.debug();
//            }
//        });
//        test3.setPosition(700, 400);
//
//        test.setName("test");
//        test2.setName("test2");
//        test3.setName("test3");

//        Button test4  = new Button(bg);
//        test4.setName("test4");
//        test4.setPosition(900, 400);
//        test4.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                Sounds.play(R.raw.sound_select);
//            }
//        });
//
//        Button test5  = new Button(bg);
//        test5.setName("test5");
//        test5.setPosition(1100, 400);
//        test5.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                imgView.debug();
//            }
//        });

        Button test1 = new Button();
        bg.addChild(test1);
        test1.setName("test1234");
        test1.setScale(3);
        test1.setPosition(900, 500);
        test1.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
            @Override
            public void run() {
                imgView.setScale(1.8f);
                Log.d("Test", "scale 1");
                imgView.debug();
            }
        });

        test1.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, new Runnable() {
            @Override
            public void run() {
                imgView.setScale(1f);
                Log.d("Test", "scale 1.5");
                imgView.debug();
            }
        });
    }
}
