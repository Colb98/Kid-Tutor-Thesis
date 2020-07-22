package com.mus.myapplication.modules.views.base;

import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.UIManager;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.classes.WordCache;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.views.base.actions.DelayTime;
import com.mus.myapplication.modules.views.home.ItemButton;
import com.mus.myapplication.modules.views.home.RelativeScene;
import com.mus.myapplication.modules.views.popup.ConfirmPopup;
import com.mus.myapplication.modules.views.popup.ResultPopup;

public class FindWordScene extends TestScene {
    protected int[] questionMap;
    protected String[] word;
    protected int correctAnswer = 0;
    protected boolean disableSubmitAnswer = false;
    protected int questionCount = 0;

    public FindWordScene(GameView parent){
        super(parent);
    }

    @Override
    public void onTimeOut() {
        stopCountDown();
        if(!getVisible()) return;
        showResult();
    }

    public void onBackButton(Runnable onConfirmBack){
        ConfirmPopup popup = new ConfirmPopup(this);
        popup.addOnRedCallback(onConfirmBack);
    }

    @Override
    protected int showResult() {
        openLearnScene();
        ResultPopup popup = (ResultPopup) getChild("resultPopup");
        if(popup == null){
            popup = new ResultPopup(this);
            mappingChild(popup, "resultPopup");
        }
        popup.showResult(correctAnswer, getQuestionCount());

        return correctAnswer;
    }


    protected int getQuestionCount(){
        return questionCount;
    }

    protected void initButtons(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button test = (Button) getChild("testBtn");
                if(test == null) return;
                test.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        openTestScene();
                    }
                });
            }
        });
    }

    protected void loadQuestion(final int idx){
        super.loadQuestion(idx);
        if(idx >= getQuestionCount()){
            showResult();
        }
        else{
            GameTextView question = (GameTextView) getChild("lbQuestion");
            SpannableString string = new SpannableString("  " + WordCache.getWordDesc(word[questionMap[idx]]).word + "  ");
            string.setSpan(new BackgroundColorSpan(0xffffffff), 0, string.length(), 0);
            question.setText(string);
            question.setPositionCenterScreen(true, false);
            Sounds.play(WordCache.getWordDesc(word[questionMap[idx]]).soundId);
            question.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                @Override
                public void run() {
                    Sounds.play(WordCache.getWordDesc(word[questionMap[idx]]).soundId);
                }
            });
        }
    }


    @Override
    public void show() {
        super.show();
        openLearnScene();
    }

    private void randomQuestionMap(){
        questionMap = new int[word.length-1];
        for(int i=0;i<word.length-1;i++){ questionMap[i] = i;}
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

    public void openTestScene(){
        isTesting = true;
        stoppedCountDown = false;
        timeRemain = TEST_DURATION;
        correctAnswer = 0;
        randomQuestionMap();
        GameView test = getChild("testGroup");
        test.show();
        getChild("testBtn").hide();
        loadQuestion(0);
    }

    public void openLearnScene(){
        isTesting = false;
        GameView test = getChild("testGroup");
        test.hide();
        stopCountDown();
        getChild("testBtn").show();
    }


    protected void submitAnswer(Sprite button){
        String w = getWord(button.getName());
        if(w.equals(word[questionMap[currentQuestion]])){
            correctAnswer++;
            Sounds.play(R.raw.sound_correct);
        }
        else{
            Sounds.play(R.raw.sound_wrong);
        }

        disableSubmitAnswer = true;
        DelayTime callback = new DelayTime(1.5f);
        callback.addOnFinishedCallback(new Runnable() {
            @Override
            public void run() {
                disableSubmitAnswer = false;
                loadQuestion(currentQuestion + 1);
            }
        });
        button.runAction(callback);
    }

    protected void initSprite(final Sprite s, int resId, Point pos, float scale){
        final FindWordScene that = this;
        s.setSpriteAnimation(resId);
        s.setScale(scale);
        s.setPosition(pos);
//        s.setDebugMode(true);
        s.setSwallowTouches(true);
        Runnable hideFlashcard = new Runnable() {
            @Override
            public void run() {
                if(!isTesting){
                    UIManager.getInstance().hideFlashcardPopup(that);
                }
            }
        };
        s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
            @Override
            public void run() {
                if(isTesting){
                    if(disableSubmitAnswer) return;
                    submitAnswer(s);
                }
                else{
                    Sprite flashcard = UIManager.getInstance().getFlashcardPopup(getWord(s.getName()), that);
                    flashcard.setPositionX(0);
                    flashcard.setPositionCenterScreen(true, false);
                }
            }
        });
        s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, hideFlashcard);
    }

    public String getWord(String name){
        String word = name;
        char key = word.charAt(word.length() - 1);
        if(key <= '9' && key >= '0')
            word = word.substring(0,word.length() - 1);
        return word;
    }
}
