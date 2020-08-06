package com.mus.kidpartner.modules.views.scene;

import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.SceneCache;
import com.mus.kidpartner.modules.classes.UIManager;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.AreaMusicManager;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.ScrollView;
import com.mus.kidpartner.modules.views.base.Sprite;
import com.mus.kidpartner.modules.views.setting.SettingUI;
import com.vuforia.engine.ImageTargets.ImageTargets;

public class MapScene extends GameScene {
    public MapScene(GameView parent){
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
        AreaMusicManager.playArea("map");
    }

    @Override
    public void show() {
        super.show();
        AreaMusicManager.playArea("map");
    }

    private void initButtons(){
        final MapScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button btnSetting = (Button)getChild("setting");
                btnSetting.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getUI(SettingUI.class, that).show();
                    }
                });

                Button achievement = (Button)getChild("achievement");
                achievement.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("achievement"));
                    }
                });
                Button school = (Button)getChild("school");
                school.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("school"));
                    }
                });
                Button home = (Button)getChild("home");
                home.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("home"));
                    }
                });
                Button mart = (Button)getChild("mart");
                mart.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("mart"));
                    }
                });
                Button zoo = (Button)getChild("zoo");
                zoo.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("zoo"));
                    }
                });
                Button gara = (Button)getChild("gara");
                gara.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("gara"));
                    }
                });
                Button res = (Button)getChild("res");
                res.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("res"));
                    }
                });
            }
        });
    }

    private void initScene(){
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*1.3f, 1080*1.3f);
//        scroller.setScrollType(ScrollView.ScrollType.NONE);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.map_background);

        bg.setScale(1.3f);
        bg.setSwallowTouches(false);
        scroller.moveToCenter();

        Button btn_setting = new Button(this);
        btn_setting.setZOrder(100);
        btn_setting.setSpriteAnimation(R.drawable.map_setting);
        btn_setting.setScale(0.3f);
        btn_setting.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("top", 50), LayoutPosition.getRule("right", btn_setting.getContentSize(false).width + 100)));
        mappingChild(btn_setting, "setting");

        Button btn_achievement = new Button(this);
        btn_achievement.setZOrder(100);
        btn_achievement.setSpriteAnimation(R.drawable.map_achievement);
        btn_achievement.setScale(0.3f);
        btn_achievement.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("top", 50), LayoutPosition.getRule("right", btn_setting.getContentSize(false).width + 150 + btn_achievement.getContentSize(false).width)));
        mappingChild(btn_achievement, "achievement");

//        Button testVuforia = new Button(this);
//        testVuforia.setZOrder(100);
//        testVuforia.setPosition(100, 50);
//        testVuforia.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                Director.getInstance().runActivity(ImageTargets.class);
//            }
//        });

        Button home = new Button(bg);
        home.setSpriteAnimation(R.drawable.map_home);
        home.setAnchorPoint(0.5f, 1);
        home.setEnableClickEffect(false);
        home.setSwallowTouches(false);
//        home.setDebugMode(true);
        mappingChild(home, "home");
        home.setScale(0.75434494f);
        home.setPosition(1557.7744f, 269.22153f);
        Utils.setSpriteDancing(home, 1.05f, 0.95f);
//        Utils.setSpriteShaking(home, 0.07f, true);

        Button gara = new Button(bg);
        gara.setSpriteAnimation(R.drawable.map_gara);
        gara.setAnchorPoint(0.5f, 1);
        gara.setEnableClickEffect(false);
        gara.setSwallowTouches(false);
//        gara.setDebugMode(true);
        mappingChild(gara, "gara");
        gara.setScale(0.6517831f);
        gara.setPosition(1125.0138f, 79.41954f);
        Utils.setSpriteDancing(gara, 1.05f, 0.95f);

        Button mart = new Button(bg);
        mart.setSpriteAnimation(R.drawable.map_mart);
        mart.setAnchorPoint(0.5f, 1);
        mart.setEnableClickEffect(false);
        mart.setSwallowTouches(false);
//        mart.setDebugMode(true);
        mappingChild(mart, "mart");
        mart.setScale(0.55076957f);
        mart.setPosition(639.7569f, 38.030396f);
        Utils.setSpriteDancing(mart, 1.05f, 0.95f);

        Button res = new Button(bg);
        res.setSpriteAnimation(R.drawable.map_restaurant);
        res.setAnchorPoint(0.5f, 1);
        res.setEnableClickEffect(false);
        res.setSwallowTouches(false);
//        res.setDebugMode(true);
        mappingChild(res, "res");
        res.setScale(0.9338676f);
        res.setPosition(1804.6984f, 582.2868f);
        Utils.setSpriteDancing(res, 1.05f, 0.95f);

        Button school = new Button(bg);
        school.setSpriteAnimation(R.drawable.map_school);
        school.setAnchorPoint(0.5f, 1);
        school.setEnableClickEffect(false);
        school.setSwallowTouches(false);
//        school.setDebugMode(true);
        mappingChild(school, "school");
        school.setScale(1.0190055f);
        school.setPosition(214.77255f, 481.86075f);
        Utils.setSpriteDancing(school, 1.05f, 0.95f);

        Button zoo = new Button(bg);
        zoo.setSpriteAnimation(R.drawable.map_zoo);
        zoo.setAnchorPoint(0.5f, 1);
        zoo.setEnableClickEffect(false);
        zoo.setSwallowTouches(false);
//        zoo.setDebugMode(true);
        mappingChild(zoo, "zoo");
        zoo.setScale(0.98910654f);
        zoo.setPosition(792.2727f, 761.769f);
        Utils.setSpriteDancing(zoo, 1.05f, 0.95f);
    }
}
