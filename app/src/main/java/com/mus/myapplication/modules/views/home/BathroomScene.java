package com.mus.myapplication.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ScrollView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;
import com.mus.myapplication.modules.views.scene.MapScene;
import com.mus.myapplication.modules.views.home.ItemButton;
import com.mus.myapplication.modules.views.setting.SettingUI;
import com.vuforia.engine.ImageTargets.ImageTargets;
public class BathroomScene extends GameScene{
    public BathroomScene(GameView parent){
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
    }
    private void initButtons(){
        final BathroomScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("home"));
                    }
                });

                /*Button window = (Button)getChild("window");
                window.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button drawers = (Button)getChild("drawers");
                drawers.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button mirror = (Button)getChild("mirror");
                mirror.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button picture = (Button)getChild("picture");
                picture.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button cupboard1 = (Button)getChild("cupboard1");
                cupboard1.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button lamp = (Button)getChild("lamp");
                lamp.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button bed = (Button)getChild("bed");
                bed.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button pillow = (Button)getChild("pillow");
                pillow.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button blanket = (Button)getChild("blanket");
                blanket.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button cupboard2 = (Button)getChild("cupboard2");
                cupboard2.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button bonsai_pot = (Button)getChild("bonsai_pot");
                bonsai_pot.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                Button carpet = (Button)getChild("carpet");
                carpet.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                    }
                });*/
            }
        });
    }

    private void initScene(){
        float scaleFactor = 1.7f;
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*scaleFactor, 1080*scaleFactor);
        scroller.setScrollType(ScrollView.ScrollType.SENSOR);
        scroller.setSensorSensitivity(0.125f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.bathroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton window = new ItemButton(bg);
        window.setEnableClickEffect(false);
        window.setSpriteAnimation(R.drawable.bathroom_window);
        window.setSwallowTouches(false);
        window.setDebugMode(false);
        mappingChild(window, "window");
        window.setScale(1.0f);
        window.setPosition(52.04126f*scaleFactor/1.3f, 220.55511f*scaleFactor/1.3f);
        window.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton toilet = new ItemButton(bg);
        toilet.setEnableClickEffect(false);
        toilet.setSpriteAnimation(R.drawable.bathroom_toilet);
        toilet.setSwallowTouches(false);
        toilet.setDebugMode(false);
        mappingChild(toilet, "toilet");
        toilet.setScale(1.0f);
        toilet.setPosition(430.5448f*scaleFactor/1.3f, 703.0516f*scaleFactor/1.3f);
        toilet.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton mirror = new ItemButton(bg);
        mirror.setEnableClickEffect(false);
        mirror.setSpriteAnimation(R.drawable.bathroom_mirror);
        mirror.setSwallowTouches(false);
        mirror.setDebugMode(false);
        mappingChild(mirror, "mirror");
        mirror.setScale(1.0f);
        mirror.setPosition(735.5448f*scaleFactor/1.3f, 288.05157f*scaleFactor/1.3f);
        mirror.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton washbasin = new ItemButton(bg);
        washbasin.setEnableClickEffect(false);
        washbasin.setSpriteAnimation(R.drawable.bathroom_washbasin);
        washbasin.setSwallowTouches(false);
        washbasin.setDebugMode(false);
        mappingChild(washbasin, "washbasin");
        washbasin.setScale(1.0f);
        washbasin.setPosition(738.5448f*scaleFactor/1.3f, 677.5516f*scaleFactor/1.3f);
        washbasin.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bathtub = new ItemButton(bg);
        bathtub.setEnableClickEffect(false);
        bathtub.setSpriteAnimation(R.drawable.bathroom_bathtub);
        bathtub.setSwallowTouches(false);
        bathtub.setDebugMode(false);
        mappingChild(bathtub, "bathtub");
        bathtub.setScale(1.0f);
        bathtub.setPosition(1138.5447f*scaleFactor/1.3f, 859.5516f*scaleFactor/1.3f);
        bathtub.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton shower = new ItemButton(bg);
        shower.setEnableClickEffect(false);
        shower.setSpriteAnimation(R.drawable.bathroom_shower);
        shower.setSwallowTouches(false);
        shower.setDebugMode(false);
        mappingChild(shower, "shower");
        shower.setScale(1.0f);
        shower.setPosition(1425.5447f*scaleFactor/1.3f, 372.55157f*scaleFactor/1.3f);
        shower.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton curtain = new ItemButton(bg);
        curtain.setEnableClickEffect(false);
        curtain.setSpriteAnimation(R.drawable.bathroom_curtain);
        curtain.setSwallowTouches(false);
        curtain.setDebugMode(false);
        mappingChild(curtain, "curtain");
        curtain.setScale(1.0f);
        curtain.setPosition(1605.5447f*scaleFactor/1.3f, 291.55157f*scaleFactor/1.3f);
        curtain.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton door = new ItemButton(bg);
        door.setEnableClickEffect(false);
        door.setSpriteAnimation(R.drawable.bathroom_door);
        door.setSwallowTouches(false);
        door.setDebugMode(false);
        mappingChild(door, "door");
        door.setScale(1.0f);
        door.setPosition(2217.545f*scaleFactor/1.3f, 199.55157f*scaleFactor/1.3f);
        door.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }
}
