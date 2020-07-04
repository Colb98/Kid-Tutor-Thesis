package com.mus.myapplication.modules.views.res;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class ResScene extends GameScene{
    private String category;
    private int step = 0;
    public ResScene(GameView parent){
        super(parent);
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }private void initScene(){
        setSceneBackground(R.drawable.res_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        mappingChild(selectCategory, "category");

        Button spice = new Button(selectCategory);
        Button food = new Button(selectCategory);
        Button juice = new Button(selectCategory);

        spice.setSpriteAnimation(R.drawable.res_icon_spice);
        food.setSpriteAnimation(R.drawable.res_icon_food);
        juice.setSpriteAnimation(R.drawable.res_icon_juice);

        float buttonWidth = spice.getContentSize(false).width;
        float buttonHeight = spice.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        spice.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - buttonHeight/2);
        food.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);
        juice.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - buttonHeight/2);

        GameTextView lbSpice = new GameTextView(spice);
        lbSpice.setText("GIA VỊ", FontCache.Font.UVNChimBienNang, 30);
        lbSpice.setFontColor(0xFFFFFFFF);
        lbSpice.setPosition(spice.getX(), spice.getY() + buttonHeight);
        lbSpice.setPositionCenterParent(false, true);

        GameTextView lbFood = new GameTextView(food);
        lbFood.setText("ĐỒ ĂN", FontCache.Font.UVNChimBienNang, 30);
        lbFood.setFontColor(0xFFFFFFFF);
        lbFood.setPosition(food.getX(), food.getY() + buttonHeight);
        lbFood.setPositionCenterParent(false, true);

        GameTextView lbJuice = new GameTextView(juice);
        lbJuice.setText("THỨC UỐNG", FontCache.Font.UVNChimBienNang, 30);
        lbJuice.setFontColor(0xFFFFFFFF);
        lbJuice.setPosition(juice.getX(), juice.getY() + buttonHeight);
        lbJuice.setPositionCenterParent(false, true);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final ResScene that = this;
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
            }
        });
    }
/*
    private void openScene(int level){
        switch(category){
            case "item":
                IQTestScene scene = (IQTestScene) Director.getInstance().loadScene(SceneCache.getScene("iq"));
                scene.setTest(level);
                break;
            default:
                Log.d("School Scene", "select a category or level that is not available");
        }
    }
 */

    private void chooseCategory(){
        step = 0;
        GameView category = getChild("category");
        category.setVisible(true);
    }
}
