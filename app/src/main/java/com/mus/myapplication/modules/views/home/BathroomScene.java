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
public class BathroomScene extends FindWordScene {
    public BathroomScene(GameView parent){
        super(parent);
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
        questionCount = 6;
        word = new String[]{"bathroom_window", "toilet", "bathroom_mirror", "washbasin", "bathtub", "shower", "bathroom_curtain", "door"};
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }
    protected void initButtons(){
        super.initButtons();
        final BathroomScene that = this;
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
        bg.setSpriteAnimation(R.drawable.bathroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton window = new ItemButton(bg);
        mappingChild(window, "bathroom_window");
        initSprite(window, R.drawable.bathroom_window, new Point(52.04126f*scaleFactor/1.3f, 220.55511f*scaleFactor/1.3f), 1f);

        final ItemButton toilet = new ItemButton(bg);
        mappingChild(toilet, "toilet");
        initSprite(toilet, R.drawable.bathroom_toilet, new Point(430.5448f*scaleFactor/1.3f, 703.0516f*scaleFactor/1.3f), 1f);

        final ItemButton mirror = new ItemButton(bg);
        mappingChild(mirror, "bathroom_mirror");
        initSprite(mirror, R.drawable.bathroom_mirror, new Point(735.5448f*scaleFactor/1.3f, 288.05157f*scaleFactor/1.3f), 1f);

        final ItemButton washbasin = new ItemButton(bg);
        mappingChild(washbasin, "washbasin");
        initSprite(washbasin, R.drawable.bathroom_washbasin, new Point(738.5448f*scaleFactor/1.3f, 677.5516f*scaleFactor/1.3f), 1f);

        final ItemButton bathtub = new ItemButton(bg);
        mappingChild(bathtub, "bathtub");
        initSprite(bathtub, R.drawable.bathroom_bathtub, new Point(1138.5447f*scaleFactor/1.3f, 859.5516f*scaleFactor/1.3f), 1f);

        final ItemButton shower = new ItemButton(bg);
        mappingChild(shower, "shower");
        initSprite(shower, R.drawable.bathroom_shower, new Point(1425.5447f*scaleFactor/1.3f, 372.55157f*scaleFactor/1.3f), 1f);

        final ItemButton curtain = new ItemButton(bg);
        mappingChild(curtain, "bathroom_curtain");
        initSprite(curtain, R.drawable.bathroom_curtain, new Point(1605.5447f*scaleFactor/1.3f, 291.55157f*scaleFactor/1.3f), 1f);

        final ItemButton door = new ItemButton(bg);
        mappingChild(door, "door");
        initSprite(door, R.drawable.bathroom_door, new Point(2217.545f*scaleFactor/1.3f, 199.55157f*scaleFactor/1.3f), 1f);

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

        /*final ItemButton window = new ItemButton(bg);
        window.setEnableClickEffect(false);
        window.setSpriteAnimation(R.drawable.bathroom_window);
        window.setSwallowTouches(false);
        window.setDebugMode(false);
        mappingChild(window, "window");
        window.setScale(1.0f);
        window.setPosition(52.04126f*scaleFactor/1.3f, 220.55511f*scaleFactor/1.3f);
        window.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton toilet = new ItemButton(bg);
        toilet.setEnableClickEffect(false);
        toilet.setSpriteAnimation(R.drawable.bathroom_toilet);
        toilet.setSwallowTouches(false);
        toilet.setDebugMode(false);
        mappingChild(toilet, "toilet");
        toilet.setScale(1.0f);
        toilet.setPosition(430.5448f*scaleFactor/1.3f, 703.0516f*scaleFactor/1.3f);
        toilet.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton mirror = new ItemButton(bg);
        mirror.setEnableClickEffect(false);
        mirror.setSpriteAnimation(R.drawable.bathroom_mirror);
        mirror.setSwallowTouches(false);
        mirror.setDebugMode(false);
        mappingChild(mirror, "mirror");
        mirror.setScale(1.0f);
        mirror.setPosition(735.5448f*scaleFactor/1.3f, 288.05157f*scaleFactor/1.3f);
        mirror.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton washbasin = new ItemButton(bg);
        washbasin.setEnableClickEffect(false);
        washbasin.setSpriteAnimation(R.drawable.bathroom_washbasin);
        washbasin.setSwallowTouches(false);
        washbasin.setDebugMode(false);
        mappingChild(washbasin, "washbasin");
        washbasin.setScale(1.0f);
        washbasin.setPosition(738.5448f*scaleFactor/1.3f, 677.5516f*scaleFactor/1.3f);
        washbasin.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bathtub = new ItemButton(bg);
        bathtub.setEnableClickEffect(false);
        bathtub.setSpriteAnimation(R.drawable.bathroom_bathtub);
        //bathtub.setSwallowTouches(false);
        bathtub.setDebugMode(false);
        mappingChild(bathtub, "bathtub");
        bathtub.setScale(1.0f);
        bathtub.setPosition(1138.5447f*scaleFactor/1.3f, 859.5516f*scaleFactor/1.3f);
        bathtub.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton shower = new ItemButton(bg);
        shower.setEnableClickEffect(false);
        shower.setSpriteAnimation(R.drawable.bathroom_shower);
        shower.setSwallowTouches(false);
        shower.setDebugMode(false);
        mappingChild(shower, "shower");
        shower.setScale(1.0f);
        shower.setPosition(1425.5447f*scaleFactor/1.3f, 372.55157f*scaleFactor/1.3f);
        shower.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton curtain = new ItemButton(bg);
        curtain.setEnableClickEffect(false);
        curtain.setSpriteAnimation(R.drawable.bathroom_curtain);
        curtain.setSwallowTouches(false);
        curtain.setDebugMode(false);
        mappingChild(curtain, "curtain");
        curtain.setScale(1.0f);
        curtain.setPosition(1605.5447f*scaleFactor/1.3f, 291.55157f*scaleFactor/1.3f);
        curtain.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton door = new ItemButton(bg);
        door.setEnableClickEffect(false);
        door.setSpriteAnimation(R.drawable.bathroom_door);
        door.setSwallowTouches(false);
        door.setDebugMode(false);
        mappingChild(door, "door");
        door.setScale(1.0f);
        door.setPosition(2217.545f*scaleFactor/1.3f, 199.55157f*scaleFactor/1.3f);
        door.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");*/
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
}
