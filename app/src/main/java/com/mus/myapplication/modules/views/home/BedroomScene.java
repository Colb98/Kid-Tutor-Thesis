package com.mus.myapplication.modules.views.home;

import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.classes.WordCache;
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

                Runnable hideFlashcard = new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().hideFlashcardPopup(that);
                    }
                };

                ItemButton window = (ItemButton)getChild("window");
                window.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.WINDOW, that);
                    }
                });
                window.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton drawers = (ItemButton)getChild("drawers");
                drawers.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.DRAWER, that);
                    }
                });
                drawers.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton mirror = (ItemButton)getChild("mirror");
                mirror.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.MIRROR, that);
                    }
                });
                mirror.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton picture = (ItemButton)getChild("picture");
                picture.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.PICTURE, that);
                    }
                });
                picture.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

//                ItemButton cupboard1 = (ItemButton)getChild("cupboard1");
//                cupboard1.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
//                    @Override
//                    public void run() {
//                        UIManager.getInstance().getFlashcardPopup(WordCache.CUPBOARD, that);
//                    }
//                });

                ItemButton lamp = (ItemButton)getChild("lamp");
                lamp.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.LAMP, that);
                    }
                });
                lamp.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton bed = (ItemButton)getChild("bed");
                bed.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.BED, that);
                    }
                });
                bed.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton pillow = (ItemButton)getChild("pillow");
                pillow.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.PILLOW, that);
                    }
                });
                pillow.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton blanket = (ItemButton)getChild("blanket");
                blanket.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.BLANKET, that);
                    }
                });
                blanket.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton cupboard2 = (ItemButton)getChild("cupboard2");
                cupboard2.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.CUPBOARD, that);
                    }
                });
                cupboard2.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton bonsaiPot = (ItemButton)getChild("bonsai_pot");
                bonsaiPot.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.POT, that);
                    }
                });
                bonsaiPot.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);

                ItemButton carpet = (ItemButton)getChild("carpet");
                carpet.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                    @Override
                    public void run() {
                        UIManager.getInstance().getFlashcardPopup(WordCache.CARPET, that);
                    }
                });
                carpet.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);
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
        bg.setSpriteAnimation(R.drawable.bedroom_background);

        bg.setScale(scaleFactor);
        bg.setSwallowTouches(false);

        final ItemButton drawers = new ItemButton(bg);
        drawers.setEnableClickEffect(false);
        drawers.setSpriteAnimation(R.drawable.bedroom_drawers);
        drawers.setSwallowTouches(true);
        drawers.setDebugMode(false);
        mappingChild(drawers, "drawers");
        drawers.setScale(1.0f);
        drawers.setPosition(114.54144f*scaleFactor/1.3f, 915.41235f*scaleFactor/1.3f);
        drawers.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final Sprite cupboard1 = new Sprite(bg);
//        cupboard1.setEnableClickEffect(false);
        cupboard1.setSpriteAnimation(R.drawable.bedroom_cupboard);
        cupboard1.setSwallowTouches(true);
        cupboard1.setDebugMode(false);
        mappingChild(cupboard1, "cupboard1");
        cupboard1.setScale(1.0f);
        cupboard1.setPosition(1269.4756f*scaleFactor/1.3f, 768.1133f*scaleFactor/1.3f);
//        cupboard1.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bed = new ItemButton(bg);
        bed.setEnableClickEffect(false);
        bed.setSpriteAnimation(R.drawable.bedroom_bed);
        bed.setSwallowTouches(true);
        bed.setDebugMode(false);
        mappingChild(bed, "bed");
        bed.setScale(1.0f);
        bed.setPosition(876.4136f*scaleFactor/1.3f, 632.28534f*scaleFactor/1.3f);
        bed.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton window = new ItemButton(bg);
        window.setEnableClickEffect(false);
        window.setSpriteAnimation(R.drawable.bedroom_window);
        window.setSwallowTouches(true);
        window.setDebugMode(false);
        mappingChild(window, "window");
        window.setScale(1.0f);
        window.setPosition(503.04144f*scaleFactor/1.3f, 293.5551f*scaleFactor/1.3f);
        window.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton mirror = new ItemButton(bg);
        mirror.setEnableClickEffect(false);
        mirror.setSpriteAnimation(R.drawable.bedroom_mirror);
        mirror.setSwallowTouches(true);
        mirror.setDebugMode(false);
        mappingChild(mirror, "mirror");
        mirror.setScale(1.0f);
        mirror.setPosition(133.54144f*scaleFactor/1.3f, 415.41235f*scaleFactor/1.3f);
        mirror.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton picture = new ItemButton(bg);
        picture.setEnableClickEffect(false);
        picture.setSpriteAnimation(R.drawable.bedroom_picture);
        picture.setSwallowTouches(true);
        picture.setDebugMode(false);
        mappingChild(picture, "picture");
        picture.setScale(1.0f);
        picture.setPosition(1671.5415f*scaleFactor/1.3f, 381.41235f*scaleFactor/1.3f);
        picture.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton lamp = new ItemButton(bg);
        lamp.setEnableClickEffect(false);
        lamp.setSpriteAnimation(R.drawable.bedroom_lamp);
        lamp.setSwallowTouches(true);
        lamp.setDebugMode(false);
        mappingChild(lamp, "lamp");
        lamp.setScale(1.0f);
        lamp.setPosition(1315.3386f*scaleFactor/1.3f, 632.34753f*scaleFactor/1.3f);
        lamp.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton pillow = new ItemButton(bg);
        pillow.setEnableClickEffect(false);
        pillow.setSpriteAnimation(R.drawable.bedroom_pillow);
        pillow.setSwallowTouches(true);
        pillow.setDebugMode(false);
        mappingChild(pillow, "pillow");
        pillow.setScale(1.0f);
        pillow.setPosition(1488.4136f*scaleFactor/1.3f, 666.28564f*scaleFactor/1.3f);
        pillow.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton blanket = new ItemButton(bg);
        blanket.setEnableClickEffect(false);
        blanket.setSpriteAnimation(R.drawable.bedroom_blanket);
        blanket.setSwallowTouches(true);
        blanket.setDebugMode(false);
        mappingChild(blanket, "blanket");
        blanket.setScale(1.0f);
        blanket.setPosition(1022.07544f*scaleFactor/1.3f, 854.28564f*scaleFactor/1.3f);
        blanket.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton cupboard2 = new ItemButton(bg);
        cupboard2.setEnableClickEffect(false);
        cupboard2.setSpriteAnimation(R.drawable.bedroom_cupboard);
        cupboard2.setSwallowTouches(true);
        cupboard2.setDebugMode(false);
        mappingChild(cupboard2, "cupboard2");
        cupboard2.setScale(1.0f);
        cupboard2.setPosition(2053.413f*scaleFactor/1.3f, 856.28564f*scaleFactor/1.3f);
        cupboard2.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton bonsaiPot = new ItemButton(bg);
        bonsaiPot.setEnableClickEffect(false);
        bonsaiPot.setSpriteAnimation(R.drawable.bedroom_bonsai_pot);
        bonsaiPot.setSwallowTouches(true);
        bonsaiPot.setDebugMode(false);
        mappingChild(bonsaiPot, "bonsai_pot");
        bonsaiPot.setScale(1.0f);
        bonsaiPot.setPosition(2164.413f*scaleFactor/1.3f, 683.28564f*scaleFactor/1.3f);
        bonsaiPot.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        final ItemButton carpet = new ItemButton(bg);
        carpet.setEnableClickEffect(false);
        carpet.setSpriteAnimation(R.drawable.bedroom_carpet);
        carpet.setSwallowTouches(true);
        carpet.setDebugMode(false);
        mappingChild(carpet, "carpet");
        carpet.setScale(1.0f);
        carpet.setPosition(1576.4135f*scaleFactor/1.3f, 1174.2854f*scaleFactor/1.3f);
        carpet.touchEventListener(0.2f, 1.15f,0.2f, 1.0f);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
    }
}
