package com.mus.kidpartner.modules.views.home;

import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.UIManager;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.FindWordScene;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.ScrollView;
import com.mus.kidpartner.modules.views.base.Sprite;

public class LivingroomScene extends FindWordScene {
    public LivingroomScene(GameView parent){
        super(parent);
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
        questionCount = 8;
        word = new String[]{"window","curtain","cupboard","picture","lamp", "sofa", "pillow", "table", "pot", "ceilingLight"};
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }
    protected void initButtons(){
        super.initButtons();
        final LivingroomScene that = this;
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
        bg.setSpriteAnimation(R.drawable.livingroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton window1 = new ItemButton(bg);
        mappingChild(window1, "window1");
        initSprite(window1, R.drawable.livingroom_window1, new Point(1819.0415f*scaleFactor/1.3f, 280.5551f*scaleFactor/1.3f), 1f);

        final ItemButton window2 = new ItemButton(bg);
        mappingChild(window2, "window2");
        initSprite(window2, R.drawable.livingroom_window2, new Point(2215.042f*scaleFactor/1.3f, 283.5551f*scaleFactor/1.3f), 1f);

        final ItemButton curtain = new ItemButton(bg);
        mappingChild(curtain, "curtain");
        initSprite(curtain, R.drawable.livingroom_curtain, new Point(1531.5414f*scaleFactor/1.3f, 182.41235f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard = new ItemButton(bg);
        mappingChild(cupboard, "cupboard");
        initSprite(cupboard, R.drawable.livingroom_cupboard, new Point(1104.5414f*scaleFactor/1.3f, 866.4123f*scaleFactor/1.3f), 1f);

        final ItemButton picture = new ItemButton(bg);
        mappingChild(picture, "picture");
        initSprite(picture, R.drawable.livingroom_picture, new Point(367.5415f*scaleFactor/1.3f, 336.41223f*scaleFactor/1.3f), 1f);

        final ItemButton lamp = new ItemButton(bg);
        mappingChild(lamp, "lamp");
        initSprite(lamp, R.drawable.livingroom_lamp, new Point(1111.4756f*scaleFactor/1.3f, 613.1133f*scaleFactor/1.3f), 1f);

        final ItemButton sofa1 = new ItemButton(bg);
        mappingChild(sofa1, "sofa1");
        initSprite(sofa1, R.drawable.livingroom_sofa1, new Point(318.83862f*scaleFactor/1.3f, 835.34753f*scaleFactor/1.3f), 1f);

        final ItemButton sofa2 = new ItemButton(bg);
        mappingChild(sofa2, "sofa2");
        initSprite(sofa2, R.drawable.livingroom_sofa2, new Point(1674.8386f*scaleFactor/1.3f, 1048.3474f*scaleFactor/1.3f), 1f);

        final ItemButton pillow = new ItemButton(bg);
        mappingChild(pillow, "pillow");
        initSprite(pillow, R.drawable.livingroom_pillow, new Point(899.4136f*scaleFactor/1.3f, 901.28564f*scaleFactor/1.3f), 1f);

        final ItemButton table = new ItemButton(bg);
        mappingChild(table, "table");
        initSprite(table, R.drawable.livingroom_table, new Point(980.4136f*scaleFactor/1.3f, 1094.2852f*scaleFactor/1.3f), 1f);

        final ItemButton bonsaiPot = new ItemButton(bg);
        mappingChild(bonsaiPot, "pot");
        initSprite(bonsaiPot, R.drawable.livingroom_bonsai_pot, new Point(47.835327f*scaleFactor/1.3f, 822.28564f*scaleFactor/1.3f), 1f);

        final ItemButton ceilingLight1 = new ItemButton(bg);
        mappingChild(ceilingLight1, "ceilingLight1");
        initSprite(ceilingLight1, R.drawable.livingroom_ceiling_light1, new Point(816.8353f*scaleFactor/1.3f, -1.7143555f*scaleFactor/1.3f), 1f);

        final ItemButton ceilingLight2 = new ItemButton(bg);
        mappingChild(ceilingLight2, "ceilingLight2");
        initSprite(ceilingLight2, R.drawable.livingroom_ceiling_light2, new Point(1337.3352f*scaleFactor/1.3f, -0.71435547f*scaleFactor/1.3f), 1f);

        final ItemButton ceilingLight3 = new ItemButton(bg);
        mappingChild(ceilingLight3, "ceilingLight3");
        initSprite(ceilingLight3, R.drawable.livingroom_ceiling_light3, new Point(1881.3356f*scaleFactor/1.3f, -1.7143555f*scaleFactor/1.3f), 1f);

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
    /*private void initScene(){
        float scaleFactor = 1.7f;
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*scaleFactor, 1080*scaleFactor);
        scroller.setScrollType(ScrollView.ScrollType.SENSOR);
        scroller.setSensorSensitivity(0.125f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.livingroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton window1 = new ItemButton(bg);
        window1.setEnableClickEffect(false);
        window1.setSpriteAnimation(R.drawable.livingroom_window1);
        //window1.setSwallowTouches(false);
        window1.setDebugMode(false);
        mappingChild(window1, "window1");
        window1.setScale(1.0f);
        window1.setPosition(1819.0415f*scaleFactor/1.3f, 280.5551f*scaleFactor/1.3f);
        window1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton window2 = new ItemButton(bg);
        window2.setEnableClickEffect(false);
        window2.setSpriteAnimation(R.drawable.livingroom_window2);
        //window2.setSwallowTouches(false);
        window2.setDebugMode(false);
        mappingChild(window2, "window2");
        window2.setScale(1.0f);
        window2.setPosition(2215.042f*scaleFactor/1.3f, 283.5551f*scaleFactor/1.3f);
        window2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton curtain = new ItemButton(bg);
        curtain.setEnableClickEffect(false);
        curtain.setSpriteAnimation(R.drawable.livingroom_curtain);
        //curtain.setSwallowTouches(false);
        curtain.setDebugMode(false);
        mappingChild(curtain, "curtain");
        curtain.setScale(1.0f);
        curtain.setPosition(1531.5414f*scaleFactor/1.3f, 182.41235f*scaleFactor/1.3f);
        curtain.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard = new ItemButton(bg);
        cupboard.setEnableClickEffect(false);
        cupboard.setSpriteAnimation(R.drawable.livingroom_cupboard);
        //cupboard.setSwallowTouches(false);
        cupboard.setDebugMode(false);
        mappingChild(cupboard, "cupboard");
        cupboard.setScale(1.0f);
        cupboard.setPosition(1104.5414f*scaleFactor/1.3f, 866.4123f*scaleFactor/1.3f);
        cupboard.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton picture = new ItemButton(bg);
        picture.setEnableClickEffect(false);
        picture.setSpriteAnimation(R.drawable.livingroom_picture);
        //picture.setSwallowTouches(false);
        picture.setDebugMode(false);
        mappingChild(picture, "picture");
        picture.setScale(1.0f);
        picture.setPosition(367.5415f*scaleFactor/1.3f, 336.41223f*scaleFactor/1.3f);
        picture.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton lamp = new ItemButton(bg);
        lamp.setEnableClickEffect(false);
        lamp.setSpriteAnimation(R.drawable.livingroom_lamp);
        //lamp.setSwallowTouches(false);
        lamp.setDebugMode(false);
        mappingChild(lamp, "lamp");
        lamp.setScale(1.0f);
        lamp.setPosition(1111.4756f*scaleFactor/1.3f, 613.1133f*scaleFactor/1.3f);
        lamp.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton sofa1 = new ItemButton(bg);
        sofa1.setEnableClickEffect(false);
        sofa1.setSpriteAnimation(R.drawable.livingroom_sofa1);
        //sofa1.setSwallowTouches(false);
        sofa1.setDebugMode(false);
        mappingChild(sofa1, "sofa1");
        sofa1.setScale(1.0f);
        sofa1.setPosition(318.83862f*scaleFactor/1.3f, 835.34753f*scaleFactor/1.3f);
        sofa1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton sofa2 = new ItemButton(bg);
        sofa2.setEnableClickEffect(false);
        sofa2.setSpriteAnimation(R.drawable.livingroom_sofa2);
        //sofa2.setSwallowTouches(false);
        sofa2.setDebugMode(false);
        mappingChild(sofa2, "sofa2");
        sofa2.setScale(1.0f);
        sofa2.setPosition(1674.8386f*scaleFactor/1.3f, 1048.3474f*scaleFactor/1.3f);
        sofa2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton pillow = new ItemButton(bg);
        pillow.setEnableClickEffect(false);
        pillow.setSpriteAnimation(R.drawable.livingroom_pillow);
        //pillow.setSwallowTouches(false);
        pillow.setDebugMode(false);
        mappingChild(pillow, "pillow");
        pillow.setScale(1.0f);
        pillow.setPosition(899.4136f*scaleFactor/1.3f, 901.28564f*scaleFactor/1.3f);
        pillow.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton table = new ItemButton(bg);
        table.setEnableClickEffect(false);
        table.setSpriteAnimation(R.drawable.livingroom_table);
        //table.setSwallowTouches(false);
        table.setDebugMode(false);
        mappingChild(table, "table");
        table.setScale(1.0f);
        table.setPosition(980.4136f*scaleFactor/1.3f, 1094.2852f*scaleFactor/1.3f);
        table.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bonsaiPot = new ItemButton(bg);
        bonsaiPot.setEnableClickEffect(false);
        bonsaiPot.setSpriteAnimation(R.drawable.livingroom_bonsai_pot);
        //bonsaiPot.setSwallowTouches(false);
        bonsaiPot.setDebugMode(false);
        mappingChild(bonsaiPot, "bonsaiPot");
        bonsaiPot.setScale(1.0f);
        bonsaiPot.setPosition(47.835327f*scaleFactor/1.3f, 822.28564f*scaleFactor/1.3f);
        bonsaiPot.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton ceilingLight1 = new ItemButton(bg);
        ceilingLight1.setEnableClickEffect(false);
        ceilingLight1.setSpriteAnimation(R.drawable.livingroom_ceiling_light1);
        //ceilingLight1.setSwallowTouches(false);
        ceilingLight1.setDebugMode(false);
        mappingChild(ceilingLight1, "ceilingLight1");
        ceilingLight1.setScale(1.0f);
        ceilingLight1.setPosition(816.8353f*scaleFactor/1.3f, -1.7143555f*scaleFactor/1.3f);
        ceilingLight1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton ceilingLight2 = new ItemButton(bg);
        ceilingLight2.setEnableClickEffect(false);
        ceilingLight2.setSpriteAnimation(R.drawable.livingroom_ceiling_light2);
        //ceilingLight2.setSwallowTouches(false);
        ceilingLight2.setDebugMode(false);
        mappingChild(ceilingLight2, "ceilingLight2");
        ceilingLight2.setScale(1.0f);
        ceilingLight2.setPosition(1337.3352f*scaleFactor/1.3f, -0.71435547f*scaleFactor/1.3f);
        ceilingLight2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton ceilingLight3 = new ItemButton(bg);
        ceilingLight3.setEnableClickEffect(false);
        ceilingLight3.setSpriteAnimation(R.drawable.livingroom_ceiling_light3);
        //ceilingLight3.setSwallowTouches(false);
        ceilingLight3.setDebugMode(false);
        mappingChild(ceilingLight3, "ceilingLight3");
        ceilingLight3.setScale(1.0f);
        ceilingLight3.setPosition(1881.3356f*scaleFactor/1.3f, -1.7143555f*scaleFactor/1.3f);
        ceilingLight3.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }*/
}
