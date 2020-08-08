package com.mus.kidpartner.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.FindWordScene;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;

public class RelativeScene extends FindWordScene {
    public RelativeScene(GameView parent){
        super(parent);
        questionCount = 8;
        word = new String[]{"grandfather", "grandmother", "father", "mother", "aunt", "uncle", "sister", "brother", "cousin"};
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }


    protected void initButtons(){
        super.initButtons();
        final RelativeScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button) getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        onBackButton(new Runnable() {
                            @Override
                            public void run() {
                                Director.getInstance().loadScene(SceneCache.getScene("home"));
                            }
                        });
                            //chooseCategory();
                    }
                });
            }
        });
    }

    @Override
    protected void loadQuestion(int idx) {
        super.loadQuestion(idx);

        Sprite bg = (Sprite)getChild("background");
        GameTextView question = (GameTextView) getChild("lbQuestion");
        float bgOffset = (Utils.getScreenWidth() - bg.getContentSize().width)/2;
        question.setPositionX(bgOffset + bg.getContentSize().width*0.06f);
    }

    private void initScene() {
        Sprite bg = new Sprite(this);
        bg.setSpriteAnimation(R.drawable.relative_background);
        bg.scaleToMaxHeight(Utils.getScreenHeight());
        bg.setPositionCenterScreen(false, false);
        mappingChild(bg, "background");

        Sprite grandpa  = new Sprite(bg);
        Sprite grandma  = new Sprite(bg);
        Sprite father   = new Sprite(bg);
        Sprite mother   = new Sprite(bg);
        Sprite aunt     = new Sprite(bg);
        Sprite uncle    = new Sprite(bg);
        Sprite sister   = new Sprite(bg);
        Sprite brother  = new Sprite(bg);
        Sprite cousinB  = new Sprite(bg);
        Sprite cousinG  = new Sprite(bg);

        mappingChild(grandpa, "grandfather");
        mappingChild(grandma, "grandmother");
        mappingChild(father , "father");
        mappingChild(mother , "mother");
        mappingChild(aunt   , "aunt");
        mappingChild(uncle  , "uncle");
        mappingChild(sister , "sister");
        mappingChild(brother, "brother");
        mappingChild(cousinB, "cousin");
        mappingChild(cousinG, "cousin");

        Log.d("debug", "scale: " + bg.getPosition().x);
        Size size = bg.getContentSize();
        initSprite(grandpa, R.drawable.relative_grandfather , new Point(0.4841309f * size.width, 0.18317053f * size.height), 1);
        initSprite(grandma, R.drawable.relative_grandmother , new Point(0.7315354f * size.width, 0.1831707f * size.height),1);
        initSprite(father , R.drawable.relative_father      , new Point(0.39479107f * size.width, 0.42184705f * size.height), 1);
        initSprite(mother , R.drawable.relative_mother      , new Point(0.5291134f * size.width, 0.4185163f * size.height), 1);
        initSprite(aunt   , R.drawable.relative_aunt        , new Point(0.8233745f * size.width, 0.41740668f * size.height), 1);
        initSprite(uncle  , R.drawable.relative_uncle       , new Point(0.68530273f * size.width, 0.41740686f * size.height), 1);
        initSprite(sister , R.drawable.relative_sister      , new Point(0.39604023f * size.width, 0.71381f * size.height), 1);
        initSprite(brother, R.drawable.relative_brother     , new Point(0.53411156f * size.width, 0.7210253f * size.height), 1);
        initSprite(cousinB, R.drawable.relative_cousin1     , new Point(0.6834286f * size.width, 0.72158027f * size.height), 1);
        initSprite(cousinG, R.drawable.relative_cousin2     , new Point(0.8219678f * size.width, 0.7214334f * size.height), 1);


        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        float bgOffset = (Utils.getScreenWidth() - bg.getContentSize().width)/2;
        Button btnTest = new Button(this);
        btnTest.setSpriteAnimation(R.drawable.button_test);
        btnTest.scaleToMaxWidth(150);
        btnTest.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", btnTest.getContentSize(false).width+bgOffset+10) , LayoutPosition.getRule("top", 50)));
        mappingChild(btnTest, "testBtn");

        GameView test = new GameView(this);
        mappingChild(test, "testGroup");

        Sprite countDownBox = new Sprite(test);
        countDownBox.setSpriteAnimation(R.drawable.school_iq_quiz_count_down);
        countDownBox.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", countDownBox.getContentSize(false).width+bgOffset+10), LayoutPosition.getRule("top", 50)));
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
        question.setPositionX(bgOffset + bg.getContentSize().width*0.06f);
        question.setPositionCenterScreen(true, false);
        mappingChild(question, "lbQuestion");
    }

}
