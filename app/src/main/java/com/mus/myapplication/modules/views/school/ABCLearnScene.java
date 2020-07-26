package com.mus.myapplication.modules.views.school;

import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.classes.WordCache;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.home.BedroomScene;
import com.mus.myapplication.modules.views.popup.FlashcardPopup;
import com.vuforia.engine.ImageTargets.ImageTargets;

import java.util.HashMap;

public class ABCLearnScene extends GameScene {
    private int level = 0;
    private int curIdx = 0;


    public ABCLearnScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }

    private void initButtons(){
        final ABCLearnScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button) getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("school"));
                    }
                });

                Button test = (Button) getChild("testBtn");
                test.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().runActivity(ImageTargets.class);
                    }
                });

                Button next = (Button) getChild("next");
                next.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        nextLesson();
                    }
                });

                Button pre = (Button) getChild("previous");
                pre.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        previousLesson();
                    }
                });
            }
        });
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);
        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();

        Sprite img = new Sprite(this);
        img.setSpriteAnimation(R.drawable.alphabet_apple);
        img.scaleToMaxSize(width*0.4f, height*0.4f);
        img.setPositionY(height * 0.2f);
        img.setPositionCenterScreen(false, true);
        mappingChild(img, "img");

        GameTextView word = new GameTextView(this);
        word.setText("Apple", FontCache.Font.UVNNguyenDu, 30);
        word.setPositionY(img.getPosition().y + img.getContentSize().height + 20);
        word.setPositionCenterScreen(false, true);
        mappingChild(word, "word");

        GameTextView pronounce = new GameTextView(this);
        pronounce.setText("ép pồ", FontCache.Font.TimesNewRoman, 18);
        pronounce.setPositionY(word.getPosition().y + word.getContentSize().height + 5);
        pronounce.setPositionCenterScreen(false, true);
        mappingChild(pronounce, "pronounce");

        GameTextView meaning = new GameTextView(this);
        meaning.setText("Téo", FontCache.Font.UVNKyThuat, 28);
        meaning.setPositionY(pronounce.getPosition().y + pronounce.getContentSize().height + 5);
        meaning.setPositionCenterScreen(false, true);
        mappingChild(meaning, "meaning");

        Button next = new Button(this);
        next.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
        next.setPositionX(width * 0.8f);
        next.setPositionCenterScreen(true, false);
        mappingChild(next, "next");

        Button pre = new Button(this);
        pre.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
        pre.setScaleX(-1);
        pre.setPositionX(width * 0.2f - pre.getContentSize().width);
        pre.setPositionCenterScreen(true, false);
        mappingChild(pre, "previous");

        Button btnTest = new Button(this);
        btnTest.setSpriteAnimation(R.drawable.button_test);
        btnTest.scaleToMaxWidth(150);
        btnTest.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", btnTest.getContentSize(false).width+width*0.03f) , LayoutPosition.getRule("top", width*0.015f)));
        mappingChild(btnTest, "testBtn");
    }

    public void setLevel(int level){
        this.level = level;
        this.loadLesson(0);
    }

    public void previousLesson(){
        if(curIdx < 0){
            return;
        }
        loadLesson(curIdx - 1);
    }

    public void nextLesson(){
        if(level * 9 + curIdx + 1 >= 26 || (level * 9 + curIdx + 1)/9 > level){
            return;
        }
        loadLesson(curIdx + 1);
    }

    public void loadLesson(int idx){
        curIdx = idx;
        if(level * 9 + idx >= 26) {
            Log.d("abc", "no question" + idx + " " + level);
            return;
        }
        idx = level*9 + idx;
        final FlashcardPopup.WordDesc w = WordCache.getWordDesc(WordCache.listWord[idx]);

        GameTextView word = (GameTextView) getChild("word");
        GameTextView meaning = (GameTextView) getChild("meaning");
        GameTextView pronounce = (GameTextView) getChild("pronounce");
        Sprite img = (Sprite)getChild("img");

        word.setText(w.word);
        word.setPositionCenterScreen(false, true);

        meaning.setText(w.desc);
        meaning.setPositionCenterScreen(false, true);

        pronounce.setText(w.pronounce);
        pronounce.setPositionCenterScreen(false, true);

        img.setSpriteAnimation(w.resId);
        img.scaleToMaxSize(Utils.getScreenWidth()*0.4f, Utils.getScreenHeight()*0.4f);
        img.setPositionCenterScreen(false, true);
        img.removeTouchEventListeners(Sprite.CallbackType.ON_CLICK);
        img.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                Sounds.play(w.soundId);
            }
        });

        Sounds.play(w.soundId);
        Button next = (Button) getChild("next");
        next.setVisible(!(idx + 1 >= 26 || (idx + 1)/9 > level));

        Button pre = (Button) getChild("previous");
        pre.setVisible(!(idx -  1 < 0 || (idx - 1)/9 < level));
    }
}
