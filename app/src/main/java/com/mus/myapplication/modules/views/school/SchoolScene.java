package com.mus.myapplication.modules.views.school;

import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class SchoolScene extends GameScene {
    public SchoolScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_background);
        Sprite bg = (Sprite) getChild("background");
        Button alphabet = new Button(bg);
        Button math = new Button(bg);
        Button iq = new Button(bg);

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


        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(iq, "iq");
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
                        Director.getInstance().loadScene(SceneCache.getScene("map"));
                    }
                });

                Button iq = (Button)getChild("iq");
                iq.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("iq"));
                    }
                });
            }
        });
    }
}
