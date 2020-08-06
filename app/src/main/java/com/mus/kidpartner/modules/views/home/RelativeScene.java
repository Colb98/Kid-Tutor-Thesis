package com.mus.kidpartner.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.SceneCache;
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
        setSceneBackground(R.drawable.relative_background);
        Sprite bg = (Sprite) getChild("background");
        bg.scaleToMaxHeight(Utils.getScreenHeight());
        bg.setPositionCenterScreen(false, false);

        Sprite grandpa  = new Sprite(this);
        Sprite grandma  = new Sprite(this);
        Sprite father   = new Sprite(this);
        Sprite mother   = new Sprite(this);
        Sprite aunt     = new Sprite(this);
        Sprite uncle    = new Sprite(this);
        Sprite sister   = new Sprite(this);
        Sprite brother  = new Sprite(this);
        Sprite cousinB  = new Sprite(this);
        Sprite cousinG  = new Sprite(this);

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
        initSprite(grandpa, R.drawable.relative_grandfather , new Point(927.5139f, 197.81683f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(grandma, R.drawable.relative_grandmother , new Point(1406.3092f, 198.81592f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(father , R.drawable.relative_father      , new Point(760.5852f, 454.5791f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(mother , R.drawable.relative_mother      , new Point(1014.4767f, 449.5837f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(aunt   , R.drawable.relative_aunt        , new Point(1581.2344f, 450.58282f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(uncle  , R.drawable.relative_uncle       , new Point(1313.349f, 451.58185f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(sister , R.drawable.relative_sister      , new Point(760.5851f, 771.2859f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(brother, R.drawable.relative_brother     , new Point(1024.4724f, 776.28125f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(cousinB, R.drawable.relative_cousin1     , new Point(1310.3502f, 777.2803f).add(bg.getPosition().x, 0), bg.getScale());
        initSprite(cousinG, R.drawable.relative_cousin2     , new Point(1579.983f, 778.14685f).add(bg.getPosition().x, 0), bg.getScale());


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
