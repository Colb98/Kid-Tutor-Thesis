package com.mus.kidpartner.modules.views.home;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import com.mus.kidpartner.modules.views.base.ColorLayer;
import com.mus.kidpartner.modules.views.base.FindWordScene;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.ScrollView;
import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.HashMap;

public class BedroomScene extends FindWordScene {

    public BedroomScene(GameView parent){
        super(parent);
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
        questionCount = 9;
        word = new String[]{"window", "drawers", /*"bed", */"pillow", "blanket", "carpet", "pot", "picture", "mirror", "cupboard", "lamp"};
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();

        initLayer();
    }

    @Override
    public void openTestScene() {
        super.openTestScene();
        setItemTesting(true);
    }

    @Override
    public void openLearnScene() {
        super.openLearnScene();
        setItemTesting(true);
    }

    private void setItemTesting(boolean val){
        HashMap<String, GameView> map = getAllChildrenWithName();
        for(GameView v : map.values()){
            if(v instanceof ItemButton){
                ((ItemButton)v).isTesting = val;
            }
        }
    }

    protected void initLayer(){
        ColorLayer l = new ColorLayer(this);

        float width = Utils.getScreenWidth();

        Sprite tiltIcon = new Sprite(l);
        tiltIcon.setSpriteAnimation(R.drawable.tilt_phone_icon);

        GameTextView desc = new GameTextView(l);
        SpannableString string = new SpannableString("Nghiêng điện thoại để khám phá!!");
        string.setSpan(new ForegroundColorSpan(0xffffffff), 0, string.length(), 0);
        desc.setText(string, FontCache.Font.UVNKyThuat, 20);

        float groupW = desc.getContentSize().width + tiltIcon.getContentSize().width + 20;
        tiltIcon.setPosition((width - groupW)/2, 30);
        desc.setPosition((width - groupW)/2 + tiltIcon.getContentSize().width + 20, 30 + tiltIcon.getContentSize().height/2 - desc.getContentSize().height/2);
    }

    protected void initButtons(){
        super.initButtons();
        final BedroomScene that = this;
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
        bg.setSpriteAnimation(R.drawable.bedroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton drawers = new ItemButton(bg);
        mappingChild(drawers, "drawers");
        initSprite(drawers, R.drawable.bedroom_drawers, new Point(114.54144f*scaleFactor/1.3f, 915.41235f*scaleFactor/1.3f), 1f);

        final Sprite cupboard1 = new Sprite(bg);
        mappingChild(cupboard1, "cupboard1");
        initSprite(cupboard1, R.drawable.bedroom_cupboard, new Point(1269.4756f*scaleFactor/1.3f, 768.1133f*scaleFactor/1.3f), 1f);

        final ItemButton bed = new ItemButton(bg);
        mappingChild(bed, "bed");
        initSprite(bed, R.drawable.bedroom_bed, new Point(876.4136f*scaleFactor/1.3f, 632.28534f*scaleFactor/1.3f), 1f);

        final ItemButton window = new ItemButton(bg);
        mappingChild(window, "window");
        initSprite(window, R.drawable.bedroom_window, new Point(503.04144f*scaleFactor/1.3f, 293.5551f*scaleFactor/1.3f), 1f);

        final ItemButton mirror = new ItemButton(bg);
        mappingChild(mirror, "mirror");
        initSprite(mirror, R.drawable.bedroom_mirror, new Point(133.54144f*scaleFactor/1.3f, 415.41235f*scaleFactor/1.3f), 1f);

        final ItemButton picture = new ItemButton(bg);
        mappingChild(picture, "picture");
        initSprite(picture, R.drawable.bedroom_picture, new Point(1671.5415f*scaleFactor/1.3f, 381.41235f*scaleFactor/1.3f), 1f);

        final ItemButton lamp = new ItemButton(bg);
        mappingChild(lamp, "lamp");
        initSprite(lamp, R.drawable.bedroom_lamp, new Point(1315.3386f*scaleFactor/1.3f, 632.34753f*scaleFactor/1.3f), 1f);

        final ItemButton pillow = new ItemButton(bg);
        mappingChild(pillow, "pillow");
        initSprite(pillow, R.drawable.bedroom_pillow, new Point(1488.4136f*scaleFactor/1.3f, 666.28564f*scaleFactor/1.3f), 1f);

        final ItemButton blanket = new ItemButton(bg);
        mappingChild(blanket, "blanket");
        initSprite(blanket, R.drawable.bedroom_blanket, new Point(1022.07544f*scaleFactor/1.3f, 854.28564f*scaleFactor/1.3f), 1f);

        final ItemButton cupboard2 = new ItemButton(bg);
        mappingChild(cupboard2, "cupboard");
        initSprite(cupboard2, R.drawable.bedroom_cupboard, new Point(2053.413f*scaleFactor/1.3f, 856.28564f*scaleFactor/1.3f), 1f);

        final ItemButton bonsaiPot = new ItemButton(bg);
        mappingChild(bonsaiPot, "pot");
        initSprite(bonsaiPot, R.drawable.bedroom_bonsai_pot, new Point(2164.413f*scaleFactor/1.3f, 683.28564f*scaleFactor/1.3f), 1f);

        final ItemButton carpet = new ItemButton(bg);
        mappingChild(carpet, "carpet");
        initSprite(carpet, R.drawable.bedroom_carpet, new Point(1576.4135f*scaleFactor/1.3f, 1174.2854f*scaleFactor/1.3f), 1f);

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
}
