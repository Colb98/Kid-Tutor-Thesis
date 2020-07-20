package com.mus.myapplication.modules.views.popup;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class FlashcardPopup extends Sprite {
    public static class WordDesc{
        String word;
        String desc;
        String pronounce;
        int resId;
        int soundId;

        public WordDesc(int resId, String word, String desc, String pronounce){
            this.resId = resId;
            this.word = word;
            this.desc = desc;
            this.pronounce = pronounce;
            soundId = -1;
        }
        public WordDesc(int resId, int soundId, String word, String desc, String pronounce){
            this.resId = resId;
            this.word = word;
            this.desc = desc;
            this.pronounce = pronounce;
            this.soundId = soundId;
        }
    }

    float timeRemainAutoHide = 0f;
    boolean keepShowing = true;
    private WordDesc content;

    public FlashcardPopup(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        this.initView();
    }

    private void initView(){
//        Sprite background = new Sprite(this);
        this.setSpriteAnimation(R.drawable.flashcard_background);
        this.scaleToMaxWidth(Utils.getScreenWidth()*0.4f);
//        mappingChild(background, "bg");

        GameTextView word = new GameTextView(this);
        mappingChild(word, "word");
        GameTextView pronounce = new GameTextView(this);
        mappingChild(pronounce, "pronounce");
        GameTextView desc = new GameTextView(this);
        mappingChild(desc, "desc");

        Sprite image = new Sprite(this);
        mappingChild(image, "image");
    }

    @Override
    public void show() {
        super.show();
        //Hẹn giờ ẩn
        keepShowing = true;
        resetTimeRemainAutoHide();
        //Phát âm thanh
        if(content.soundId != -1){
            Sounds.play(content.soundId);
        }
    }

    public void resetTimeRemainAutoHide(){
        timeRemainAutoHide = 3f;
    }

    @Override
    public void hide() {
        keepShowing = false;
        if(timeRemainAutoHide > 0)
            return;
        super.hide();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(getVisible()){
            timeRemainAutoHide -= dt;
            if(timeRemainAutoHide <= 0 && !keepShowing){
                hide();
                timeRemainAutoHide = 0;
            }
        }
    }

    public void loadContent(WordDesc word){
        setContent(word);
    }

    public void setContent(WordDesc w){
        Sprite bg = this;
        Sprite image = (Sprite) getChild("image");
        image.setSpriteAnimation(w.resId);
        image.setPositionX(20);
        image.scaleToMaxSize(bg.getContentSize(false).width * 0.3f, bg.getContentSize(false).height * 0.9f);
        image.setPositionCenterParent(true, false);

        GameTextView word = (GameTextView) getChild("word");
        word.setText(w.word, FontCache.Font.UVNNguyenDu, 30);
        // Set vị trí
        word.setPosition(0, 10);
        word.scaleToMaxWidthNoScaleUp((getContentSize().width*2/3 - 5)*0.7f);
        word.setPositionCenterParent(false, true);
        word.move(bg.getContentSize().width/6, 0);

        GameTextView pronounce = (GameTextView) getChild("pronounce");
        pronounce.setText(w.pronounce, FontCache.Font.TimesNewRoman, 16);
        // Set vị trí
        pronounce.setPosition(0, 20 + word.getContentSize().height);
        pronounce.scaleToMaxWidthNoScaleUp((getContentSize().width*1/3)*0.7f);
        pronounce.setPositionCenterParent(false, true);
        pronounce.move(bg.getContentSize().width/6, 0);

        GameTextView desc = (GameTextView) getChild("desc");
        desc.setText(w.desc, FontCache.Font.UVNKyThuat, 40);
        // Set vị trí
        desc.scaleToMaxWidthNoScaleUp((getContentSize().width*2/3 - 5)*0.7f);
        desc.setPositionY(bg.getContentSize().height - 10 - desc.getContentSize().height);
        desc.setPositionCenterParent(false, true);
        desc.move(bg.getContentSize().width/6, 0);

        content = w;
    }

    public WordDesc getContent(){
        return content;
    }
}
