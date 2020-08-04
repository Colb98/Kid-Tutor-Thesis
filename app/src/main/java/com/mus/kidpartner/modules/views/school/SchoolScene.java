package com.mus.kidpartner.modules.views.school;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.AreaMusicManager;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;

public class SchoolScene extends GameScene {
    private String category;
    private int step = 0;
    public SchoolScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }

    @Override
    public void show() {
        super.show();
        AreaMusicManager.playArea("school");
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        GameView selectLevel = new GameView(bg);
        selectLevel.setVisible(false);
        mappingChild(selectCategory, "category");
        mappingChild(selectLevel, "level");

        Button alphabet = new Button(selectCategory);
        Button math = new Button(selectCategory);
        Button iq = new Button(selectCategory);

        alphabet.setSpriteAnimation(R.drawable.school_icon_alphabet);
        math.setSpriteAnimation(R.drawable.school_icon_math);
        iq.setSpriteAnimation(R.drawable.school_icon_iq);

        float buttonWidth = iq.getContentSize(false).width;
        float buttonHeight = iq.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        alphabet.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - buttonHeight/2);
        math.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);
        iq.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - buttonHeight/2);

        Button level1 = new Button(selectLevel);
        Button level2 = new Button(selectLevel);
        Button level3 = new Button(selectLevel);

        level1.setSpriteAnimation(R.drawable.school_level_1);
        level2.setSpriteAnimation(R.drawable.school_level_2);
        level3.setSpriteAnimation(R.drawable.school_level_3);

        level2.setPositionCenterScreen(false, false);
        level1.setPosition(level2.getPosition().add(0, -100 - level2.getContentSize(false).height));
        level3.setPosition(level2.getPosition().add(0, 100 + level2.getContentSize(false).height));

        mappingChild(level1, "lv1");
        mappingChild(level2, "lv2");
        mappingChild(level3, "lv3");

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(iq, "iq");
        mappingChild(alphabet, "alphabet");
        mappingChild(math, "math");
        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final SchoolScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        if(step == 0)
                            Director.getInstance().loadScene(SceneCache.getScene("map"));
                        else
                            chooseCategory();
                    }
                });

                Button iq = (Button)getChild("iq");
                iq.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        category = "iq";
                        chooseLevel();
                    }
                });

                Button alphabet = (Button)getChild("alphabet");
                alphabet.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        category = "alphabet";
                        chooseLevel();
                    }
                });

                Button lv1 = (Button)getChild("lv1");
                lv1.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene(0);
                    }
                });

                Button lv2 = (Button)getChild("lv2");
                lv2.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene(1);
                    }
                });

                Button lv3 = (Button)getChild("lv3");
                lv3.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene(2);
                    }
                });
            }
        });
    }

    private void openScene(int level){
        switch(category){
            case "iq":
                IQTestScene iqScene = (IQTestScene) Director.getInstance().loadScene(SceneCache.getScene("iq"));
                iqScene.setTest(level);
                break;
            case "alphabet":
                ABCLearnScene abcScene = (ABCLearnScene) Director.getInstance().loadScene(SceneCache.getScene("alphabet"));
                abcScene.setLevel(level);
                break;
            default:
                Log.d("School Scene", "select a category or level that is not available");
        }
    }

    private void chooseLevel(){
        step = 1;
        GameView level = getChild("level");
        level.setVisible(true);

        GameView category = getChild("category");
        category.setVisible(false);
    }

    private void chooseCategory(){
        step = 0;
        GameView level = getChild("level");
        level.setVisible(false);

        GameView category = getChild("category");
        category.setVisible(true);
    }
}
