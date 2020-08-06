package com.mus.kidpartner.modules.views.school;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.classes.WordCache;
import com.mus.kidpartner.modules.controllers.AchievementManager;
import com.mus.kidpartner.modules.controllers.Sounds;
import com.mus.kidpartner.modules.models.common.Achievement;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.GameVuforiaScene;
import com.mus.kidpartner.modules.views.base.Sprite;
import com.mus.kidpartner.modules.views.base.actions.DelayTime;
import com.mus.kidpartner.modules.views.popup.AchievementPopup;
import com.mus.kidpartner.modules.views.popup.FlashcardPopup;
import com.mus.kidpartner.modules.views.popup.ResultPopup;

public class ABCTestScene extends GameVuforiaScene {
    int level = 0;
    int question = 0;
    int correct = 0;
    int[] questionMap;
    boolean correctCurQuestion = false;
    boolean isShowedResult = false;
    public ABCTestScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
        setLevel(1);
    }

    private void initButtons(){
        final ABCTestScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button) getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        onVuforiaBackBtn();
                    }
                });

                Button next = (Button) getChild("next");
                next.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        nextQuestion();
                    }
                });

                Button replay = (Button) getChild("replay");
                replay.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        setLevel(level);
                    }
                });
            }
        });
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);

        Sprite bg = (Sprite) getChild("background");

        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();

        Button back = new Button(this);
        back.setSpriteAnimation(R.drawable.back_button);
        back.setPosition(50, 50);
        mappingChild(back, "btnBack");

        Sprite img = new Sprite(bg);
        img.setSpriteAnimation(R.drawable.alphabet_apple);
        img.scaleToMaxSize(width * 0.4f, height * 0.4f);
        img.setPosition(width * 0.25f - img.getContentSize().width/2, height * 0.10f);
        mappingChild(img, "img");

        GameTextView text = new GameTextView(bg);
        text.setText("_ pple", FontCache.Font.UVNNguyenDu, 32);
        text.setPosition(width * 0.25f - text.getContentSize().width/2, height * 0.15f + img.getContentSize().height + 10);
        mappingChild(text, "word");

        GameTextView ans = new GameTextView(bg);
        SpannableString s = new SpannableString("A");
        s.setSpan(new ForegroundColorSpan(0xff00ff00), 0, 1, 0);
        ans.setText(s, FontCache.Font.UVNNguyenDu, 32);
        ans.setPosition(text.getPosition());
        mappingChild(ans, "ans");

//        GameTextView debugTrack = new GameTextView(bg);
//        debugTrack.setText(s, FontCache.Font.TimesNewRoman, 32);
//        debugTrack.setPosition(0, 0);
//        mappingChild(debugTrack, "debug");

        Button next = new Button(bg);
        next.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
        next.setPosition(width * 0.25f - next.getContentSize().width/2, text.getPosition().y + text.getContentSize().height + 10);
        mappingChild(next, "next");
        
        Button replay = new Button(bg);
        replay.setSpriteAnimation(R.drawable.school_iq_quiz_button_restart);
        replay.setPosition(width * 0.25f - replay.getContentSize().width/2, text.getPosition().y + text.getContentSize().height + 10);
        mappingChild(replay, "replay");
    }

    public void setLevel(int level){
        isShowedResult = false;
        this.level = level;
        correct = 0;
        randomQuestionMap();
        loadQuestion(0);
    }

    private void randomQuestionMap(){
        questionMap = new int[level < 2?9:8];
        for(int i=0;i<questionMap.length;i++){
            questionMap[i] = i;
        }
        for(int i=1;i<questionMap.length;i++){
            int j = (int)(Math.random()*(i+1));
            if(j != i){
                // swap
                int t = questionMap[i];
                questionMap[i] = questionMap[j];
                questionMap[j] = t;
            }
        }
    }

    protected int showResult() {
        isShowedResult = true;
        ResultPopup popup = (ResultPopup) getChild("resultPopup");
        if(popup == null){
            popup = new ResultPopup(this);
            mappingChild(popup, "resultPopup");
        }
        popup.showResult(correct, getQuestionCount());
        setVisibleButtonNext();
        return correct;
    }

    private boolean isLastQuestion(){
        return question == getQuestionCount() - 1;
    }

    private int getQuestionCount() {
        return level == 2?8:9;
    }

    public void nextQuestion(){
        if(level * 9 + question + 1 >= 26 || (level * 9 + question + 1)/9 > level){
            // show result
            showResult();
            Achievement a = AchievementManager.getInstance().onFinishedTest("language", level, correct, getQuestionCount());
            if(a != null){
                AchievementPopup popup = new AchievementPopup(this);
                popup.loadAchivement(a);
            }
            return;
        }
        correctCurQuestion = false;
        loadQuestion(question + 1);
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if(!correctCurQuestion){
            activityRef.resumeTracking();

            final boolean detect = submitAns(detectedObjectName);
            correctCurQuestion = detect;

            GameTextView v = (GameTextView)getChild("debug");
            if(v != null){
                if(detectedObjectName != null){
                    v.setText(detectedObjectName);
                }
                else{
                    v.setText("Null");
                }
            }
            FlashcardPopup.WordDesc w = WordCache.getWordDesc(WordCache.listWord[questionMap[question]]);
            if(detect) {
                correct++;
                Sounds.play(w.soundId);
                GameTextView ans = (GameTextView) getChild("ans");
                ans.show();
                if(isLastQuestion()){
                    final DelayTime delay = new DelayTime(1);
                    delay.addOnFinishedCallback(new Runnable() {
                        @Override
                        public void run() {
                            delay.removeAllCallbacks();
                            showResult();
                        }
                    });
                    v.runAction(delay);
                }
            }

            // Reset camera when true or false
            if(detect || detectedObjectName != null){
                activityRef.pauseTracking();
            }
        }
    }

    public boolean submitAns(String ans){
        if(ans == null) return false;
        FlashcardPopup.WordDesc w = WordCache.getWordDesc(WordCache.listWord[questionMap[question]]);
        if(ans.toLowerCase().equals(w.word.substring(0,1).toLowerCase()))
            return true;
        return false;
    }

    public void loadQuestion(int idx){
        question = idx;
        GameTextView word = (GameTextView) getChild("word");
        GameTextView ans = (GameTextView) getChild("ans");
        Sprite img = (Sprite) getChild("img");
        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();

        idx = level * 9 + idx;
        ans.hide();
        FlashcardPopup.WordDesc w = WordCache.getWordDesc(WordCache.listWord[questionMap[question]]);
        word.setText("_ " + w.word.substring(1));
        word.setPosition(width * 0.25f - word.getContentSize().width/2, height * 0.10f + img.getContentSize().height + 10);

        SpannableString s = new SpannableString(w.word.substring(0,1));
        s.setSpan(new ForegroundColorSpan(0xff00ff00), 0, 1, 0);
        ans.setText(s);
        ans.setPosition(word.getPosition());

        img.setSpriteAnimation(w.resId);
        img.scaleToMaxSize(width * 0.4f, height * 0.4f);
        img.setPosition(width * 0.25f - img.getContentSize().width/2, height * 0.10f);

        setVisibleButtonNext();
    }
    
    private void setVisibleButtonNext(){
        Button next = (Button) getChild("next");
        next.setVisible(!isShowedResult || !isLastQuestion());

        Button replay = (Button) getChild("replay");
        replay.setVisible(isShowedResult && isLastQuestion());
    }
}
