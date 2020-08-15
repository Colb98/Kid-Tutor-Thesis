package com.mus.kidpartner.modules.views.zoo;

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

public class ZooScene extends GameScene{
    private String category;
    private int step = 0;
    public ZooScene(GameView parent){
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
        AreaMusicManager.playArea("zoo");
    }

    private void initScene(){
        setSceneBackground(R.drawable.zoo_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        mappingChild(selectCategory, "category");

        Button pet = new Button(selectCategory);
        Button farmAnimal = new Button(selectCategory);
        Button wildAnimal = new Button(selectCategory);

        pet.setSpriteAnimation(R.drawable.zoo_icon_cat);
        farmAnimal.setSpriteAnimation(R.drawable.zoo_icon_pig);
        wildAnimal.setSpriteAnimation(R.drawable.zoo_icon_tiger);

        mappingChild(pet, "pet");
        mappingChild(farmAnimal, "farm");
        mappingChild(wildAnimal, "wild");

        float buttonWidth = pet.getContentSize(false).width;
        float buttonHeight = pet.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        pet.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - buttonHeight/2);
        farmAnimal.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);
        wildAnimal.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - buttonHeight/2);

        float height = Utils.getScreenHeight();
        GameTextView lbPet = new GameTextView(selectCategory);
        lbPet.setText("THÚ CƯNG", FontCache.Font.UVNChimBienNang, 30);
        lbPet.setFontColor(0xFFFFFFFF);
        lbPet.setPositionXCenterWithView(pet);
        lbPet.setPositionY(height * 3 / 4);

        GameTextView lbFarmAnimal = new GameTextView(selectCategory);
        lbFarmAnimal.setText("VẬT NUÔI", FontCache.Font.UVNChimBienNang, 30);
        lbFarmAnimal.setFontColor(0xFFFFFFFF);
        lbFarmAnimal.setPositionXCenterWithView(farmAnimal);
        lbFarmAnimal.setPositionY(height * 3 / 4);

        GameTextView lbWildAnimal = new GameTextView(selectCategory);
        lbWildAnimal.setText("DÃ THÚ", FontCache.Font.UVNChimBienNang, 30);
        lbWildAnimal.setFontColor(0xFFFFFFFF);
        lbWildAnimal.setPositionXCenterWithView(wildAnimal);
        lbWildAnimal.setPositionY(height * 3 / 4);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final ZooScene that = this;
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

                Button pet = (Button)getChild("pet");
                pet.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(ZooScene.this);
                    }
                });

                Button farm = (Button)getChild("farm");
                farm.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(ZooScene.this);
                    }
                });
                
                Button wild = (Button)getChild("wild");
                wild.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getNotUnlockedLayer(ZooScene.this);
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
