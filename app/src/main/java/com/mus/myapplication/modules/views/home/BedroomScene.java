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
public class BedroomScene extends GameScene {
    public BedroomScene(GameView parent){
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
        final BedroomScene that = this;
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

                Button bonsaiPot = (Button)getChild("bonsai_pot");
                bonsaiPot.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
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
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*1.3f, 1080*1.3f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.bedroom_background);

        bg.setScale(1.3f);
        bg.setSwallowTouches(false);

        final ItemButton drawers = new ItemButton(bg);
        drawers.setEnableClickEffect(false);
        drawers.setSpriteAnimation(R.drawable.bedroom_drawers);
        drawers.setSwallowTouches(true);
        drawers.setDebugMode(false);
        mappingChild(drawers, "drawers");
        drawers.setScale(1.0f);
        drawers.setPosition(114.54144f, 915.41235f);
        drawers.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final Sprite cupboard1 = new Sprite(bg);
//        cupboard1.setEnableClickEffect(false);
        cupboard1.setSpriteAnimation(R.drawable.bedroom_cupboard);
        cupboard1.setSwallowTouches(true);
        cupboard1.setDebugMode(false);
        mappingChild(cupboard1, "cupboard1");
        cupboard1.setScale(1.0f);
        cupboard1.setPosition(1269.4756f, 768.1133f);
//        cupboard1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bed = new ItemButton(bg);
        bed.setEnableClickEffect(false);
        bed.setSpriteAnimation(R.drawable.bedroom_bed);
        bed.setSwallowTouches(true);
        bed.setDebugMode(false);
        mappingChild(bed, "bed");
        bed.setScale(1.0f);
        bed.setPosition(876.4136f, 632.28534f);
        bed.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton window = new ItemButton(bg);
        window.setEnableClickEffect(false);
        window.setSpriteAnimation(R.drawable.bedroom_window);
        window.setSwallowTouches(true);
        window.setDebugMode(false);
        mappingChild(window, "window");
        window.setScale(1.0f);
        window.setPosition(503.04144f, 293.5551f);
        window.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton mirror = new ItemButton(bg);
        mirror.setEnableClickEffect(false);
        mirror.setSpriteAnimation(R.drawable.bedroom_mirror);
        mirror.setSwallowTouches(true);
        mirror.setDebugMode(false);
        mappingChild(mirror, "mirror");
        mirror.setScale(1.0f);
        mirror.setPosition(133.54144f, 415.41235f);
        mirror.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton picture = new ItemButton(bg);
        picture.setEnableClickEffect(false);
        picture.setSpriteAnimation(R.drawable.bedroom_picture);
        picture.setSwallowTouches(true);
        picture.setDebugMode(false);
        mappingChild(picture, "picture");
        picture.setScale(1.0f);
        picture.setPosition(1671.5415f, 381.41235f);
        picture.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton lamp = new ItemButton(bg);
        lamp.setEnableClickEffect(false);
        lamp.setSpriteAnimation(R.drawable.bedroom_lamp);
        lamp.setSwallowTouches(true);
        lamp.setDebugMode(false);
        mappingChild(lamp, "lamp");
        lamp.setScale(1.0f);
        lamp.setPosition(1315.3386f, 632.34753f);
        lamp.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton pillow = new ItemButton(bg);
        pillow.setEnableClickEffect(false);
        pillow.setSpriteAnimation(R.drawable.bedroom_pillow);
        pillow.setSwallowTouches(true);
        pillow.setDebugMode(false);
        mappingChild(pillow, "pillow");
        pillow.setScale(1.0f);
        pillow.setPosition(1488.4136f, 666.28564f);
        pillow.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton blanket = new ItemButton(bg);
        blanket.setEnableClickEffect(false);
        blanket.setSpriteAnimation(R.drawable.bedroom_blanket);
        blanket.setSwallowTouches(true);
        blanket.setDebugMode(false);
        mappingChild(blanket, "blanket");
        blanket.setScale(1.0f);
        blanket.setPosition(1022.07544f, 854.28564f);
        blanket.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard2 = new ItemButton(bg);
        cupboard2.setEnableClickEffect(false);
        cupboard2.setSpriteAnimation(R.drawable.bedroom_cupboard);
        cupboard2.setSwallowTouches(true);
        cupboard2.setDebugMode(false);
        mappingChild(cupboard2, "cupboard2");
        cupboard2.setScale(1.0f);
        cupboard2.setPosition(2053.413f, 856.28564f);
        cupboard2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bonsaiPot = new ItemButton(bg);
        bonsaiPot.setEnableClickEffect(false);
        bonsaiPot.setSpriteAnimation(R.drawable.bedroom_bonsai_pot);
        bonsaiPot.setSwallowTouches(true);
        bonsaiPot.setDebugMode(false);
        mappingChild(bonsaiPot, "bonsai_pot");
        bonsaiPot.setScale(1.0f);
        bonsaiPot.setPosition(2164.413f, 683.28564f);
        bonsaiPot.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton carpet = new ItemButton(bg);
        carpet.setEnableClickEffect(false);
        carpet.setSpriteAnimation(R.drawable.bedroom_carpet);
        carpet.setSwallowTouches(true);
        carpet.setDebugMode(false);
        mappingChild(carpet, "carpet");
        carpet.setScale(1.0f);
        carpet.setPosition(1576.4135f, 1174.2854f);
        carpet.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }
}
