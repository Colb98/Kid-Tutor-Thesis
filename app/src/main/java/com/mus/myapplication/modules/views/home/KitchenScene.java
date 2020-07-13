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
public class KitchenScene extends GameScene{
    public KitchenScene(GameView parent){
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
        final KitchenScene that = this;
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
        ScrollView scroller = new ScrollView(this, Utils.getScreenWidth(), Utils.getScreenHeight());
        scroller.setContentSize(1920*1.3f, 1080*1.3f);
        Sprite bg = new Sprite(scroller);
//        scroller.addChild(bg);
        bg.setSpriteAnimation(R.drawable.kitchen_background_1);

        bg.setScale(1.3f);
        bg.setSwallowTouches(false);

        final ItemButton fridge = new ItemButton(bg);
        fridge.setEnableClickEffect(false);
        fridge.setSpriteAnimation(R.drawable.kitchen_fridge);
        fridge.setSwallowTouches(false);
        fridge.setDebugMode(false);
        mappingChild(fridge, "fridge");
        fridge.setScale(1.0f);
        fridge.setPosition(1600.5447f, 153.55157f);
        fridge.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard1 = new ItemButton(bg);
        cupboard1.setEnableClickEffect(false);
        cupboard1.setSpriteAnimation(R.drawable.kitchen_cupboard1);
        cupboard1.setDebugMode(false);
        mappingChild(cupboard1, "cupboard1");
        cupboard1.setScale(1.0f);
        cupboard1.setPosition(196.04468f, 61.051575f);
        cupboard1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard2 = new ItemButton(bg);
        cupboard2.setEnableClickEffect(false);
        cupboard2.setSpriteAnimation(R.drawable.kitchen_cupboard2);
        cupboard2.setSwallowTouches(false);
        cupboard2.setDebugMode(false);
        mappingChild(cupboard2, "cupboard2");
        cupboard2.setScale(1.0f);
        cupboard2.setPosition(189.54468f, 525.0516f);
        cupboard2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard3 = new ItemButton(bg);
        cupboard3.setEnableClickEffect(false);
        cupboard3.setSpriteAnimation(R.drawable.kitchen_cupboard3);
        cupboard3.setDebugMode(false);
        mappingChild(cupboard3, "cupboard3");
        cupboard3.setScale(1.0f);
        cupboard3.setPosition(1491.5447f, 581.0516f);
        cupboard3.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard4 = new ItemButton(bg);
        cupboard4.setEnableClickEffect(false);
        cupboard4.setSpriteAnimation(R.drawable.kitchen_cupboard4);
        cupboard4.setDebugMode(false);
        mappingChild(cupboard4, "cupboard4");
        cupboard4.setScale(1.0f);
        cupboard4.setPosition(1472.0447f, 154.05157f);
        cupboard4.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton ventilation = new ItemButton(bg);
        ventilation.setEnableClickEffect(false);
        ventilation.setSpriteAnimation(R.drawable.kitchen_ventilation);
        ventilation.setDebugMode(false);
        mappingChild(ventilation, "ventilation");
        ventilation.setScale(1.0f);
        ventilation.setPosition(1191.0447f, 153.71707f);
        ventilation.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton stove = new ItemButton(bg);
        stove.setEnableClickEffect(false);
        stove.setSpriteAnimation(R.drawable.kitchen_stove);
        stove.setDebugMode(false);
        mappingChild(stove, "stove");
        stove.setScale(1.0f);
        stove.setPosition(1214.0447f, 523.71704f);
        stove.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton chair1 = new ItemButton(bg);
        chair1.setEnableClickEffect(false);
        chair1.setSpriteAnimation(R.drawable.kitchen_chair1);
        chair1.setDebugMode(false);
        mappingChild(chair1, "chair1");
        chair1.setScale(1.0f);
        chair1.setPosition(798.0446f, 810.71704f);
        chair1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton chair2 = new ItemButton(bg);
        chair2.setEnableClickEffect(false);
        chair2.setSpriteAnimation(R.drawable.kitchen_chair2);
        chair2.setDebugMode(false);
        mappingChild(chair2, "chair2");
        chair2.setScale(1.0f);
        chair2.setPosition(1898.0447f, 806.71704f);
        chair2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton table = new ItemButton(bg);
        table.setEnableClickEffect(false);
        table.setSpriteAnimation(R.drawable.kitchen_table);
        table.setDebugMode(false);
        mappingChild(table, "table");
        table.setScale(1.0f);
        table.setPosition(1100.0447f, 759.71704f);
        table.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }
}
