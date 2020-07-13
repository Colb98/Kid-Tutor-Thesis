package com.mus.myapplication.modules.views.gara;

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

public class GaraScene extends GameScene{
    private String category;
    private int step = 0;
    public GaraScene(GameView parent){
        super(parent);
    }
    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }private void initScene(){
        setSceneBackground(R.drawable.gara_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        mappingChild(selectCategory, "category");

        Button item = new Button(selectCategory);
        Button vehicle = new Button(selectCategory);
        Button machine = new Button(selectCategory);

        item.setSpriteAnimation(R.drawable.gara_icon_item);
        vehicle.setSpriteAnimation(R.drawable.gara_icon_vehicle);
        machine.setSpriteAnimation(R.drawable.gara_icon_machine);

        float buttonWidth = item.getContentSize(false).width;
        float buttonHeight = item.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        item.setPosition(screenWidth/2 - buttonWidth*1.5f - 150, screenHeight/2 - 3*buttonHeight/4);
        vehicle.setPosition(screenWidth/2 - buttonWidth*0.5f, screenHeight/2 - 3*buttonHeight/4);
        machine.setPosition(screenWidth/2 + buttonWidth*0.5f + 150, screenHeight/2 - 3*buttonHeight/4);

        mappingChild(item, "item");
        mappingChild(vehicle, "vehicle");
        mappingChild(machine, "machine");

        GameTextView lbItem = new GameTextView(selectCategory);
        lbItem.setText("VẬT DỤNG", FontCache.Font.UVNChimBienNang, 30);
        lbItem.setFontColor(0xFFFFFFFF);
        lbItem.setPosition(item.getPosition().x + buttonWidth/2 - lbItem.getContentSize(false).width/2, item.getPosition().y + buttonHeight);

        GameTextView lbVehicle = new GameTextView(selectCategory);
        lbVehicle.setText("PHƯƠNG TIỆN", FontCache.Font.UVNChimBienNang, 30);
        lbVehicle.setFontColor(0xFFFFFFFF);
        lbVehicle.setPosition(vehicle.getPosition().x + buttonWidth/2 - lbVehicle.getContentSize(false).width/2, vehicle.getPosition().y + buttonHeight);

        GameTextView lbMachine = new GameTextView(selectCategory);
        lbMachine.setText("MÁY MÓC", FontCache.Font.UVNChimBienNang, 30);
        lbMachine.setFontColor(0xFFFFFFFF);
        lbMachine.setPosition(machine.getPosition().x + buttonWidth/2 - lbMachine.getContentSize(false).width/2, machine.getPosition().y + buttonHeight);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final GaraScene that = this;
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

                String[] cate = new String[]{"item", "vehicle", "machine"};
                for(final String s : cate){
                    Button b = (Button)getChild(s);
                    b.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                        @Override
                        public void run() {
                            GaraTestScene scene = (GaraTestScene) Director.getInstance().loadScene(SceneCache.getScene("garaTest"));
                            scene.setLevel(s);
                        }
                    });
                }
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
