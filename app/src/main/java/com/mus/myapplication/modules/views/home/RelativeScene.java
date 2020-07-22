package com.mus.myapplication.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.classes.WordCache;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.FindWordScene;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.TestScene;
import com.mus.myapplication.modules.views.base.actions.DelayTime;
import com.mus.myapplication.modules.views.popup.ConfirmPopup;
import com.mus.myapplication.modules.views.popup.ResultPopup;
import com.mus.myapplication.modules.views.school.IQTestScene;
import com.mus.myapplication.modules.views.home.BedroomScene;
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

        initSprite(grandpa, R.drawable.relative_grandfather , new Point(1137.5139f, 197.81683f), bg.getScale());
        initSprite(grandma, R.drawable.relative_grandmother , new Point(1616.3092f, 198.81592f), bg.getScale());
        initSprite(father , R.drawable.relative_father      , new Point(970.5852f, 454.5791f), bg.getScale());
        initSprite(mother , R.drawable.relative_mother      , new Point(1224.4767f, 449.5837f), bg.getScale());
        initSprite(aunt   , R.drawable.relative_aunt        , new Point(1791.2344f, 450.58282f), bg.getScale());
        initSprite(uncle  , R.drawable.relative_uncle       , new Point(1523.349f, 451.58185f), bg.getScale());
        initSprite(sister , R.drawable.relative_sister      , new Point(970.5851f, 771.2859f), bg.getScale());
        initSprite(brother, R.drawable.relative_brother     , new Point(1234.4724f, 776.28125f), bg.getScale());
        initSprite(cousinB, R.drawable.relative_cousin1     , new Point(1520.3502f, 777.2803f), bg.getScale());
        initSprite(cousinG, R.drawable.relative_cousin2     , new Point(1789.983f, 778.14685f), bg.getScale());


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
