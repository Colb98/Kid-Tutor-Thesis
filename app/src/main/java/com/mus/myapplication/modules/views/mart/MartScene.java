package com.mus.myapplication.modules.views.mart;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.UIManager;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.AreaMusicManager;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;
import com.mus.kidpartner.modules.views.res.ResScene;

public class MartScene extends GameScene{
    private String category;
    private int step = 0;
    public MartScene(GameView parent){
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
        AreaMusicManager.playArea("market");
    }

    private void initScene(){
        setSceneBackground(R.drawable.mart_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        mappingChild(selectCategory, "category");

        Button fruit = new Button(selectCategory);
        Button vegetable = new Button(selectCategory);
        Button meat = new Button(selectCategory);

        fruit.setSpriteAnimation(R.drawable.mart_icon_fruit);
        vegetable.setSpriteAnimation(R.drawable.mart_icon_vegetable);
        meat.setSpriteAnimation(R.drawable.mart_icon_meat);

        mappingChild(fruit, "fruit");
        mappingChild(vegetable, "vegetable");
        mappingChild(meat, "meat");

        float buttonWidth = fruit.getContentSize(false).width;
        float buttonHeight = fruit.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        fruit.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - buttonHeight/2);
        vegetable.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);
        meat.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - buttonHeight/2);

        GameTextView lbFruit = new GameTextView(fruit);
        lbFruit.setText("HOA QUẢ", FontCache.Font.UVNChimBienNang, 30);
        lbFruit.setFontColor(0xFFFFFFFF);
        lbFruit.setPosition(fruit.getX(), fruit.getY() + buttonHeight);
        lbFruit.setPositionCenterParent(false, true);

        GameTextView lbVegetable = new GameTextView(vegetable);
        lbVegetable.setText("RAU CỦ", FontCache.Font.UVNChimBienNang, 30);
        lbVegetable.setFontColor(0xFFFFFFFF);
        lbVegetable.setPosition(vegetable.getX(), vegetable.getY() + buttonHeight);
        lbVegetable.setPositionCenterParent(false, true);

        GameTextView lbMeat = new GameTextView(meat);
        lbMeat.setText("THỰC PHẨM", FontCache.Font.UVNChimBienNang, 30);
        lbMeat.setFontColor(0xFFFFFFFF);
        lbMeat.setPosition(meat.getX(), meat.getY() + buttonHeight);
        lbMeat.setPositionCenterParent(false, true);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final MartScene that = this;
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

                Button fruit = (Button)getChild("fruit");
                fruit.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(MartScene.this);
                    }
                });

                Button vegetable = (Button)getChild("vegetable");
                vegetable.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(MartScene.this);
                    }
                });

                Button meat = (Button)getChild("meat");
                meat.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(MartScene.this);
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
