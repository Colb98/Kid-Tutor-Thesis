package com.mus.myapplication.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.classes.WordCache;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.school.IQTestScene;
import com.mus.myapplication.modules.views.home.BedroomScene;
public class RelativeScene extends GameScene{
    private String category;
    private int step = 0;
    public RelativeScene(GameView parent){
        super(parent);
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
    private void initButtons(){
        final RelativeScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button) getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        if (step == 0)
                            Director.getInstance().loadScene(SceneCache.getScene("map"));
                        else
                        {}
                            //chooseCategory();
                    }
                });
            }
        });
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
    }

    private void initSprite(final Sprite s, int resId, Point pos, float scale){
        final RelativeScene that = this;
        s.setSpriteAnimation(resId);
        s.setScale(scale);
        s.setPosition(pos);
//        s.setDebugMode(true);
        s.setSwallowTouches(true);
        Runnable hideFlashcard = new Runnable() {
            @Override
            public void run() {
                UIManager.getInstance().hideFlashcardPopup(that);
            }
        };
        s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
            @Override
            public void run() {
                Sprite flashcard = UIManager.getInstance().getFlashcardPopup(s.getName(), that);
                flashcard.setPositionX(0);
                flashcard.setPositionCenterScreen(true, false);
            }
        });
        s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);
    }
}
