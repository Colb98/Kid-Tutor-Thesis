package com.mus.myapplication.modules.views.scene;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.ScrollView;
import com.mus.myapplication.modules.views.base.Sprite;

public class MapScene extends GameScene {
    public MapScene(){
        super();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        initScene();
    }

    private void initScene(){
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*1.3f, 1080*1.3f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.map_background);

        bg.setScale(1.3f);
        bg.setSwallowTouches(false);

        Button btn_achievement = new Button(this);
        btn_achievement.setSpriteAnimation(R.drawable.map_achievement);
        btn_achievement.setScale(0.3f);
        btn_achievement.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("top", 50), LayoutPosition.getRule("right", 250)));
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) btn_achievement.getLayoutParams();
//        btn_achievement.requestLayout();
//        btn_achievement.move(-50,50);
        Button home = new Button(bg);
        home.setSpriteAnimation(R.drawable.map_home);
        home.setEnableClickEffect(false);
        home.setSwallowTouches(false);
//        home.setDebugMode(true);
        home.setName("Home");
        home.setScale(0.75434494f);
        home.setPosition(1557.7744f, 269.22153f);

        Button gara = new Button(bg);
        gara.setSpriteAnimation(R.drawable.map_gara);
        gara.setEnableClickEffect(false);
        gara.setSwallowTouches(false);
//        gara.setDebugMode(true);
        gara.setName("Gara");
        gara.setScale(0.6517831f);
        gara.setPosition(1125.0138f, 79.41954f);

        Button mart = new Button(bg);
        mart.setSpriteAnimation(R.drawable.map_mart);
        mart.setEnableClickEffect(false);
        mart.setSwallowTouches(false);
//        mart.setDebugMode(true);
        mart.setName("Mart");
        mart.setScale(0.55076957f);
        mart.setPosition(639.7569f, 38.030396f);

        Button res = new Button(bg);
        res.setSpriteAnimation(R.drawable.map_restaurant);
        res.setEnableClickEffect(false);
        res.setSwallowTouches(false);
//        res.setDebugMode(true);
        res.setName("Restaurant");
        res.setScale(0.9338676f);
        res.setPosition(1804.6984f, 582.2868f);

        Button school = new Button(bg);
        school.setSpriteAnimation(R.drawable.map_school);
        school.setEnableClickEffect(false);
        school.setSwallowTouches(false);
//        school.setDebugMode(true);
        school.setName("School");
        school.setScale(1.0190055f);
        school.setPosition(214.77255f, 481.86075f);

        Button zoo = new Button(bg);
        zoo.setSpriteAnimation(R.drawable.map_zoo);
        zoo.setEnableClickEffect(false);
        zoo.setSwallowTouches(false);
//        zoo.setDebugMode(true);
        zoo.setName("Zoo");
        zoo.setScale(0.98910654f);
        zoo.setPosition(792.2727f, 761.769f);

    }
}
