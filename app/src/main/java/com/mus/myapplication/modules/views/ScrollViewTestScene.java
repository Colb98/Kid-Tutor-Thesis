package com.mus.myapplication.modules.views;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.Sprite;

public class ScrollViewTestScene extends GameScene {

    private static final String LOGTAG = "ScrollViewTestScene";
    public ScrollViewTestScene() {
        super();
        Log.d(LOGTAG, "ScrollViewScene inited");
        initSections();
    }

    private void initSections(){
//        ScrollView container = new ScrollView(this, new Size(1000, 500));
//        final SectionSprite cat = new SectionSprite();
//        SectionSprite rabbit = new SectionSprite ();
//        GameTextView text = new GameTextView();
//
////        container.setScrollType(ScrollView.ScrollType.BOTH);
//        container.setName("ScrollView");
//
//        cat.setName("CAT");
//        rabbit.setName("RABBIT");
//
//        container.addChild(cat);
//        container.addChild(rabbit);
//        cat.addChild(text);
//        text.setText("Hello world");
//        text.setFont();
//        text.setFontSize(30);
//
//        try {
//            cat.setSpriteAnimation(MonsterCat.IDLE, 12);
//            rabbit.setSpriteAnimation(MonsterRabbit.IDLE, 12);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        cat.setScale(0.41f);
//        cat.setPosition(200, 400);
////        rabbit.setScale(0.3f);
//        rabbit.setPosition(50, 100);
//
//        Log.d("MAIN", "set Z ORDER");
//        cat.setZOrder(10);
//
//        Button button = new Button(this);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lp.rightMargin = 50;
//        lp.bottomMargin = 100;
//        button.setLayoutParams(lp);
//
////        createScaleTypeButtons(cat);
//        createScaleButtons(cat);
//        createAlignParentButtons(cat);
    }

    private void createAlignParentButtons(final Sprite cat){
        Button reset = new Button(this);
        Button top = new Button(this);
        Button left = new Button(this);
        Button bottom = new Button(this);
        Button right = new Button(this);
        Button start = new Button(this);
        Button end = new Button(this);

        reset.setPosition(300, 750);
        reset.setLabel("reset");
        top.setPosition(700, 750);
        top.setLabel("top");
        left.setPosition(1000, 750);
        left.setLabel("left");
        bottom.setPosition(1300, 750);
        bottom.setLabel("bottom");
        right.setPosition(700, 900);
        right.setLabel("right");
        start.setPosition(1000, 900);
        start.setLabel("start");
        end.setPosition(1300, 900);
        end.setLabel("end");

        reset.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setPosition(50, 100);
            }
        });
        top.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                lp.topMargin = 0;
                cat.applyLayoutParam(lp);
            }
        });
        left.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lp.leftMargin = 0;
                cat.applyLayoutParam(lp);
            }
        });
        bottom.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                lp.bottomMargin = 0;
                cat.applyLayoutParam(lp);
            }
        });
        right.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                lp.rightMargin = 0;
                cat.applyLayoutParam(lp);
            }
        });
        start.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.setMarginStart(0);
                cat.applyLayoutParam(lp);
            }
        });
        end.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_END);
                lp.setMarginEnd(0);
                cat.applyLayoutParam(lp);
            }
        });
    }

    private void createScaleButtons(final Sprite cat){
        Button plus = new Button(this);
        Button minus = new Button(this);

        plus.setLabel("PLUS");
        minus.setLabel("MINUS");

        plus.setScale(2f);
        minus.setScale(2f);
        plus.setPosition(1400, 0);
        minus.setPosition(1000, 0);

        cat.setAnchorPoint(0.5f, 0.5f);

        plus.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                float scale = cat.getScale();
                if(scale > 3)
                    return;
                cat.setScale(scale+0.05f);
            }
        });

        minus.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                float scale = cat.getScale();
                if(scale < 0.1)
                    return;
                cat.setScale(scale-0.05f);
            }
        });
    }

    private void createScaleTypeButtons(final Sprite cat){

        Button test1 = new Button(this);
        Button test2 = new Button(this);
        Button test3 = new Button(this);
        Button test4 = new Button(this);
        Button test5 = new Button(this);
        Button test6 = new Button(this);
        Button test7 = new Button(this);

        test1.setPosition(0, 0);
        test2.setPosition(0, 216);
        test3.setPosition(0, 432);
        test4.setPosition(0, 648);
        test5.setPosition(0, 860);
        test6.setPosition(400, 0);
        test7.setPosition(400, 216);

        test1.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.FIT_XY);
            }
        });

        test2.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.FIT_END);
            }
        });

        test3.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.FIT_CENTER);
            }
        });

        test4.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.FIT_START);
            }
        });

        test5.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.CENTER_CROP);
            }
        });

        test6.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.CENTER);
            }
        });

        test7.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                cat.setScaleType(ScaleType.CENTER_INSIDE);
            }
        });
    }
}
