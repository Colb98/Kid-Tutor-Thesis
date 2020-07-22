package com.mus.myapplication.modules.views.home;

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
import com.mus.myapplication.modules.views.school.IQTestScene;
import com.mus.myapplication.modules.views.home.BedroomScene;

public class HomeScene extends GameScene{
    private String category;
    private int step = 0;
    public HomeScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }

    private void initScene(){
        setSceneBackground(R.drawable.home_background);
        Sprite bg = (Sprite) getChild("background");

        GameView selectCategory = new GameView(bg);
        GameView selectRoom = new GameView(bg);
        selectRoom.setVisible(false);
        mappingChild(selectCategory, "category");
        mappingChild(selectRoom, "room");

        Button relative = new Button(selectCategory);
        Button item = new Button(selectCategory);

        relative.setSpriteAnimation(R.drawable.home_icon_relative);
        item.setSpriteAnimation(R.drawable.home_icon_item);

        float buttonWidth = item.getContentSize(false).width;
        float buttonHeight = item.getContentSize(false).height;
        float screenWidth = Utils.getScreenWidth();
        float screenHeight = Utils.getScreenHeight();

        relative.setPosition(screenWidth/2 - buttonWidth*1.5f, screenHeight/2 - buttonHeight/2);
        item.setPosition(screenWidth/2 + buttonWidth*0.5f, screenHeight/2 - buttonHeight/2);

        GameTextView lbItem = new GameTextView(item);
        lbItem.setText("VẬT DỤNG", FontCache.Font.UVNChimBienNang, 30);
        lbItem.setFontColor(0xFFFFFFFF);
        lbItem.setPosition(item.getX(), item.getY() + buttonHeight);
        lbItem.setPositionCenterParent(false, true);

        GameTextView lbRelative = new GameTextView(relative);
        lbRelative.setText("NGƯỜI THÂN", FontCache.Font.UVNChimBienNang, 30);
        lbRelative.setFontColor(0xFFFFFFFF);
        lbRelative.setPosition(relative.getX(), relative.getY() + buttonHeight);
        lbRelative.setPositionCenterParent(false, true);

        Button livingroom = new Button(selectRoom);
        Button bedroom = new Button(selectRoom);
        Button kitchen = new Button(selectRoom);
        Button bathroom = new Button(selectRoom);

        livingroom.setSpriteAnimation(R.drawable.home_buttom_room);
        bedroom.setSpriteAnimation(R.drawable.home_buttom_room);
        kitchen.setSpriteAnimation(R.drawable.home_buttom_room);
        bathroom.setSpriteAnimation(R.drawable.home_buttom_room);

        livingroom.setPosition(screenWidth/2 - buttonWidth*1.25f, screenHeight/2 - buttonHeight);
        bedroom.setPosition(screenWidth/2 + buttonWidth*0.25f, screenHeight/2 - buttonHeight);
        kitchen.setPosition(screenWidth/2 - buttonWidth*1.25f, screenHeight/2);
        bathroom.setPosition(screenWidth/2 + buttonWidth*0.25f, screenHeight/2);

        mappingChild(livingroom, "livingroom");
        mappingChild(bedroom, "bedroom");
        mappingChild(kitchen, "kitchen");
        mappingChild(bathroom, "bathroom");

        GameTextView lbLivingroom = new GameTextView(livingroom);
        lbLivingroom.setText("PHÒNG KHÁCH", FontCache.Font.UVNChimBienNang, 20);
        lbLivingroom.setFontColor(0xFFFFFFFF);
        lbLivingroom.setPositionCenterParent(false, false);

        GameTextView lbBedroom = new GameTextView(bedroom);
        lbBedroom.setText("PHÒNG NGỦ", FontCache.Font.UVNChimBienNang, 20);
        lbBedroom.setFontColor(0xFFFFFFFF);
        lbBedroom.setPositionCenterParent(false, false);

        GameTextView lbKitchen = new GameTextView(kitchen);
        lbKitchen.setText("NHÀ BẾP", FontCache.Font.UVNChimBienNang, 20);
        lbKitchen.setFontColor(0xFFFFFFFF);
        lbKitchen.setPositionCenterParent(false, false);

        GameTextView lbBathroom = new GameTextView(bathroom);
        lbBathroom.setText("PHÒNG TẮM", FontCache.Font.UVNChimBienNang, 20);
        lbBathroom.setFontColor(0xFFFFFFFF);
        lbBathroom.setPositionCenterParent(false, false);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(item, "item");
        mappingChild(relative, "relative");
        mappingChild(btnBack, "btnBack");
    }

    private void initButtons(){
        final HomeScene that = this;
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

                Button relative = (Button)getChild("relative");
                relative.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene("relative");
                    }
                });

                Button item = (Button)getChild("item");
                item.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        category = "item";
                        chooseRoom();
                    }
                });

                Button livingroom = (Button)getChild("livingroom");
                livingroom.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene("livingroom");
                    }
                });

                Button bedroom = (Button)getChild("bedroom");
                bedroom.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene("bedroom");
                    }
                });

                Button kitchen = (Button)getChild("kitchen");
                kitchen.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene("kitchen");
                    }
                });
                Button bathroom = (Button)getChild("bathroom");
                bathroom.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openScene("bathroom");
                    }
                });
            }
        });
    }

    private void openScene(String room){
        switch(room){
            case "bedroom":
                BedroomScene bedroomScene = (BedroomScene) Director.getInstance().loadScene(SceneCache.getScene("bedroom"));
                break;
            case "livingroom":
                LivingroomScene livingroomScene = (LivingroomScene) Director.getInstance().loadScene(SceneCache.getScene("livingroom"));
                break;
            case "bathroom":
                BathroomScene bathroomScene = (BathroomScene) Director.getInstance().loadScene(SceneCache.getScene("bathroom"));
                break;
            case "kitchen":
                KitchenScene kitchenScene = (KitchenScene) Director.getInstance().loadScene(SceneCache.getScene("kitchen"));
                break;
            case "relative":
                RelativeScene scene = (RelativeScene) Director.getInstance().loadScene(SceneCache.getScene("relative"));
                break;
            default:
                Log.d("School Scene", "select a category or level that is not available");
        }
    }

    private void chooseRoom(){
        step = 1;
        GameView room = getChild("room");
        room.setVisible(true);

        GameView category = getChild("category");
        category.setVisible(false);
    }

    private void chooseCategory(){
        step = 0;
        GameView level = getChild("room");
        level.setVisible(false);

        GameView category = getChild("category");
        category.setVisible(true);
    }
}
