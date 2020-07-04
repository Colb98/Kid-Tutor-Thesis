package com.mus.myapplication.modules.views.zoo;

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
    }private void initScene(){
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

        float buttonWidth = pet.getContentSize(false).width;
        float buttonHeight = pet.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        pet.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - buttonHeight/2);
        farmAnimal.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);
        wildAnimal.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - buttonHeight/2);

        GameTextView lbPet = new GameTextView(pet);
        lbPet.setText("THÚ CƯNG", FontCache.Font.UVNChimBienNang, 30);
        lbPet.setFontColor(0xFFFFFFFF);
        lbPet.setPosition(pet.getX(), pet.getY() + buttonHeight);
        lbPet.setPositionCenterParent(false, true);

        GameTextView lbFarmAnimal = new GameTextView(farmAnimal);
        lbFarmAnimal.setText("VẬT NUÔI", FontCache.Font.UVNChimBienNang, 30);
        lbFarmAnimal.setFontColor(0xFFFFFFFF);
        lbFarmAnimal.setPosition(farmAnimal.getX(), farmAnimal.getY() + buttonHeight);
        lbFarmAnimal.setPositionCenterParent(false, true);

        GameTextView lbWildAnimal = new GameTextView(wildAnimal);
        lbWildAnimal.setText("DÃ THÚ", FontCache.Font.UVNChimBienNang, 30);
        lbWildAnimal.setFontColor(0xFFFFFFFF);
        lbWildAnimal.setPosition(wildAnimal.getX(), wildAnimal.getY() + buttonHeight);
        lbWildAnimal.setPositionCenterParent(false, true);

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
