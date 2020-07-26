package com.mus.myapplication.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.FindWordScene;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ScrollView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;
import com.mus.myapplication.modules.views.scene.MapScene;
import com.mus.myapplication.modules.views.home.ItemButton;
import com.mus.myapplication.modules.views.setting.SettingUI;
import com.vuforia.engine.ImageTargets.ImageTargets;
public class KitchenScene extends FindWordScene {
    public KitchenScene(GameView parent){
        super(parent);
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
        questionCount = 4;
        word = new String[]{"fridge","kitchen_cupboard","stove","chair","table"};
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }
    protected void initButtons(){
        super.initButtons();
        final KitchenScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("home"));
                    }
                });

                Runnable hideFlashcard = new Runnable() {
                    @Override
                    public void run() {
                        if(!isTesting) {
                            UIManager.getInstance().hideFlashcardPopup(that);
                        }
                    }
                };
                for(final GameView v: getAllChildrenWithName().values()){
                    if(v instanceof ItemButton){
                        ((ItemButton) v).addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                            @Override
                            public void run() {
                                if(isTesting){
                                    if(disableSubmitAnswer) return;
                                    submitAnswer(((ItemButton)v));
                                }
                                else{
                                    UIManager.getInstance().getFlashcardPopup(getWord(v.getName()), that);
                                }
                            }
                        });

                        ((ItemButton) v).addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);
                    }
                }
            }
        });
    }

    private void initScene(){
        float scaleFactor = 1.7f;
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*scaleFactor, 1080*scaleFactor);
        scroller.setScrollType(ScrollView.ScrollType.SENSOR);
        scroller.setSensorSensitivity(0.125f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.kitchen_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton fridge = new ItemButton(bg);
        mappingChild(fridge, "fridge");
        initSprite(fridge, R.drawable.kitchen_fridge, new Point(1600.5447f*scaleFactor/1.3f, 153.55157f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard1 = new ItemButton(bg);
        mappingChild(cupboard1, "cupboard1");
        initSprite(cupboard1, R.drawable.kitchen_cupboard1, new Point(196.04468f*scaleFactor/1.3f, 61.051575f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard2 = new ItemButton(bg);
        mappingChild(cupboard2, "cupboard2");
        initSprite(cupboard2, R.drawable.kitchen_cupboard2, new Point(189.54468f*scaleFactor/1.3f, 525.0516f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard3 = new ItemButton(bg);
        mappingChild(cupboard3, "cupboard3");
        initSprite(cupboard3, R.drawable.kitchen_cupboard3, new Point(1491.5447f*scaleFactor/1.3f, 581.0516f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard4 = new ItemButton(bg);
        mappingChild(cupboard4, "cupboard4");
        initSprite(cupboard4, R.drawable.kitchen_cupboard4, new Point(1472.0447f*scaleFactor/1.3f, 154.05157f*scaleFactor/1.3f), 1f);

        final ItemButton stove = new ItemButton(bg);
        mappingChild(stove, "stove");
        initSprite(stove, R.drawable.kitchen_stove, new Point(1214.0447f*scaleFactor/1.3f, 523.71704f*scaleFactor/1.3f), 1f);

        final ItemButton chair1 = new ItemButton(bg);
        mappingChild(chair1, "chair1");
        initSprite(chair1, R.drawable.kitchen_chair1, new Point(798.0446f*scaleFactor/1.3f, 810.71704f*scaleFactor/1.3f), 1f);

        final ItemButton chair2 = new ItemButton(bg);
        mappingChild(chair2, "chair2");
        initSprite(chair2, R.drawable.kitchen_chair2, new Point(1898.0447f*scaleFactor/1.3f, 806.71704f*scaleFactor/1.3f), 1f);

        final ItemButton table = new ItemButton(bg);
        mappingChild(table, "table");
        initSprite(table, R.drawable.kitchen_table, new Point(1100.0447f*scaleFactor/1.3f, 759.71704f*scaleFactor/1.3f), 1f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        Button btnTest = new Button(this);
        btnTest.setSpriteAnimation(R.drawable.button_test);
        btnTest.scaleToMaxWidth(150);
        btnTest.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", btnTest.getContentSize(false).width+bg.getContentSize().width*0.06f) , LayoutPosition.getRule("top", +bg.getContentSize().width*0.03f)));
        mappingChild(btnTest, "testBtn");

        GameView test = new GameView(this);
        mappingChild(test, "testGroup");

        Sprite countDownBox = new Sprite(test);
        countDownBox.setSpriteAnimation(R.drawable.school_iq_quiz_count_down);
        countDownBox.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", countDownBox.getContentSize(false).width+bg.getContentSize().width*0.06f), LayoutPosition.getRule("top", bg.getContentSize().width*0.03f)));
        final GameTextView lbCountDown = new GameTextView(countDownBox);
        lbCountDown.setText(Utils.secondToString(timeRemain));
        lbCountDown.setFont(FontCache.Font.UVNNguyenDu);
        lbCountDown.setPositionCenterParent(false, false);
        lbCountDown.addUpdateRunnable(new UpdateRunnable() {
            @Override
            public void run() {
                if(stoppedCountDown || !isTesting) return;
                timeRemain -= dt;
                if(timeRemain < 0){
                    onTimeOut();
                    return;
                }
                lbCountDown.setText(Utils.secondToString(timeRemain));
                lbCountDown.setPositionCenterParent(false, true);
            }
        });

        GameTextView question = new GameTextView(test);
        question.setText("Grandfather", FontCache.Font.UVNNguyenDu, 32);
        question.setPositionX(bg.getContentSize().width*0.06f);
        question.setPositionCenterScreen(true, false);
        mappingChild(question, "lbQuestion");
    }

    @Override
    protected void initSprite(Sprite s, int resId, Point pos, float scale) {
        super.initSprite(s, resId, pos, scale);
        s.setSwallowTouches(true);
        if(s instanceof ItemButton){
            ItemButton b = (ItemButton) s;
            b.setEnableClickEffect(false);
            b.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);
        }
    }

    /*protected void initScene(){
        float scaleFactor = 1.7f;
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*scaleFactor, 1080*scaleFactor);
        scroller.setScrollType(ScrollView.ScrollType.SENSOR);
        scroller.setSensorSensitivity(0.125f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.kitchen_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton fridge = new ItemButton(bg);
        fridge.setEnableClickEffect(false);
        fridge.setSpriteAnimation(R.drawable.kitchen_fridge);
        fridge.setSwallowTouches(false);
        fridge.setDebugMode(false);
        mappingChild(fridge, "fridge");
        fridge.setScale(1.0f);
        fridge.setPosition(1600.5447f*scaleFactor/1.3f, 153.55157f*scaleFactor/1.3f);
        fridge.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard1 = new ItemButton(bg);
        cupboard1.setEnableClickEffect(false);
        cupboard1.setSpriteAnimation(R.drawable.kitchen_cupboard1);
        cupboard1.setDebugMode(false);
        mappingChild(cupboard1, "cupboard1");
        cupboard1.setScale(1.0f);
        cupboard1.setPosition(196.04468f*scaleFactor/1.3f, 61.051575f*scaleFactor/1.3f);
        cupboard1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard2 = new ItemButton(bg);
        cupboard2.setEnableClickEffect(false);
        cupboard2.setSpriteAnimation(R.drawable.kitchen_cupboard2);
        cupboard2.setSwallowTouches(false);
        cupboard2.setDebugMode(false);
        mappingChild(cupboard2, "cupboard2");
        cupboard2.setScale(1.0f);
        cupboard2.setPosition(189.54468f*scaleFactor/1.3f, 525.0516f*scaleFactor/1.3f);
        cupboard2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard3 = new ItemButton(bg);
        cupboard3.setEnableClickEffect(false);
        cupboard3.setSpriteAnimation(R.drawable.kitchen_cupboard3);
        cupboard3.setDebugMode(false);
        mappingChild(cupboard3, "cupboard3");
        cupboard3.setScale(1.0f);
        cupboard3.setPosition(1491.5447f*scaleFactor/1.3f, 581.0516f*scaleFactor/1.3f);
        cupboard3.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard4 = new ItemButton(bg);
        cupboard4.setEnableClickEffect(false);
        cupboard4.setSpriteAnimation(R.drawable.kitchen_cupboard4);
        cupboard4.setDebugMode(false);
        mappingChild(cupboard4, "cupboard4");
        cupboard4.setScale(1.0f);
        cupboard4.setPosition(1472.0447f*scaleFactor/1.3f, 154.05157f*scaleFactor/1.3f);
        cupboard4.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton ventilation = new ItemButton(bg);
        ventilation.setEnableClickEffect(false);
        ventilation.setSpriteAnimation(R.drawable.kitchen_ventilation);
        ventilation.setDebugMode(false);
        mappingChild(ventilation, "ventilation");
        ventilation.setScale(1.0f);
        ventilation.setPosition(1191.0447f*scaleFactor/1.3f, 153.71707f*scaleFactor/1.3f);
        ventilation.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton stove = new ItemButton(bg);
        stove.setEnableClickEffect(false);
        stove.setSpriteAnimation(R.drawable.kitchen_stove);
        stove.setDebugMode(false);
        mappingChild(stove, "stove");
        stove.setScale(1.0f);
        stove.setPosition(1214.0447f*scaleFactor/1.3f, 523.71704f*scaleFactor/1.3f);
        stove.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton chair1 = new ItemButton(bg);
        chair1.setEnableClickEffect(false);
        chair1.setSpriteAnimation(R.drawable.kitchen_chair1);
        chair1.setDebugMode(false);
        mappingChild(chair1, "chair1");
        chair1.setScale(1.0f);
        chair1.setPosition(798.0446f*scaleFactor/1.3f, 810.71704f*scaleFactor/1.3f);
        chair1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton chair2 = new ItemButton(bg);
        chair2.setEnableClickEffect(false);
        chair2.setSpriteAnimation(R.drawable.kitchen_chair2);
        chair2.setDebugMode(false);
        mappingChild(chair2, "chair2");
        chair2.setScale(1.0f);
        chair2.setPosition(1898.0447f*scaleFactor/1.3f, 806.71704f*scaleFactor/1.3f);
        chair2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton table = new ItemButton(bg);
        table.setEnableClickEffect(false);
        table.setSpriteAnimation(R.drawable.kitchen_table);
        table.setDebugMode(false);
        mappingChild(table, "table");
        table.setScale(1.0f);
        table.setPosition(1100.0447f*scaleFactor/1.3f, 759.71704f*scaleFactor/1.3f);
        table.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }*/
}
