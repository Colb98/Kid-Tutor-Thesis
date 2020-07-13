package com.mus.myapplication.modules.views.gara;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.AchievementManager;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.controllers.Sounds;
import com.mus.myapplication.modules.models.TestsConfig;
import com.mus.myapplication.modules.models.common.Achievement;
import com.mus.myapplication.modules.models.garage.CraftQuest;
import com.mus.myapplication.modules.models.garage.CraftTest;
import com.mus.myapplication.modules.models.school.IQQuestion;
import com.mus.myapplication.modules.models.school.IQTest;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.TestScene;
import com.mus.myapplication.modules.views.popup.AchievementPopup;
import com.mus.myapplication.modules.views.school.IQTestScene;

import java.util.List;

public class GaraTestScene extends TestScene {

    public GaraTestScene(GameView parent) {
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        timeRemain = TEST_DURATION;
        currentTest = TestsConfig.getCraftTest(0);
        initScene();
        initButtons();
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);

        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        GameView testView = new GameView(this);
        mappingChild(testView, "testing");

        GameView resultView = new GameView(this);
        mappingChild(resultView, "result");
        resultView.setVisible(false);

        Sprite bg2 = new Sprite(testView);
        bg2.setSpriteAnimation(R.drawable.school_iq_quiz_background_2);
        bg2.setScale(0.9f*width/bg2.getContentSize(false).width);
        bg2.setPosition(width/2 - bg2.getContentSize(false).width/2, height/2 - bg2.getContentSize(false).height/2);
        mappingChild(bg2, "questionPanel");

        Button buttonNext = new Button(testView);
        buttonNext.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
//        countDownCircle.setPosition(, height/2 - countDownCircle.getContentSize(false).height/2 + bg2.getContentSize(false).height/2 + 150);
        buttonNext.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 30 + buttonNext.getContentSize(false).height), LayoutPosition.getRule("left", width/2 - buttonNext.getContentSize(false).width/2)));
        mappingChild(buttonNext, "btnNext");

        Sprite countDownBox = new Sprite(bg2);
        countDownBox.setSpriteAnimation(R.drawable.school_iq_quiz_count_down);
        countDownBox.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", countDownBox.getContentSize(false).width), LayoutPosition.getRule("top", -countDownBox.getContentSize(false).height-20)));
        final GameTextView lbCountDown = new GameTextView(countDownBox);
        lbCountDown.setText(Utils.secondToString(timeRemain));
        lbCountDown.setFont(FontCache.Font.UVNNguyenDu);
        lbCountDown.setPositionCenterParent(false, false);
        lbCountDown.addUpdateRunnable(new UpdateRunnable() {
            @Override
            public void run() {
                if(stoppedCountDown) return;
                timeRemain -= dt;
                if(timeRemain < 0){
                    onTimeOut();
                    return;
                }
                lbCountDown.setText(Utils.secondToString(timeRemain));
            }
        });

        GameTextView lbTitle = new GameTextView(testView);
        lbTitle.setPosition(0, 30);
        int questionNumber = 1;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(Utils.getString(R.string.text_question_no) + " ", new RelativeSizeSpan(1.5f), 0).append(String.valueOf(questionNumber), new RelativeSizeSpan(1.5f), 0).setSpan(new StyleSpan(Typeface.BOLD), 0, builder.length(), 0);
        builder.append("/10");

        lbTitle.setText(builder);
        lbTitle.setFont(FontCache.Font.UVNKyThuat);
        lbTitle.setPositionCenterScreen(false, true);
        mappingChild(lbTitle, "lbTitle");

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        GameTextView lbResultTitle = new GameTextView(resultView);
        lbResultTitle.setPosition(0, 50);
        lbResultTitle.setText(Utils.getString(R.string.text_congratulation).toUpperCase());
        lbResultTitle.setFont(FontCache.Font.UVNNguyenDu);
        lbResultTitle.setFontSize(50);
        lbResultTitle.setPositionCenterScreen(false, true);
        mappingChild(lbResultTitle, "lbResultTitle");

        Sprite bg3 = new Sprite(resultView);
        bg3.setSpriteAnimation(R.drawable.school_iq_quiz_background_2);
        bg3.setScale(0.55f*width/bg3.getContentSize(false).width);
        bg3.setPosition(width/2 - bg3.getContentSize(false).width/2, height/2 - bg3.getContentSize(false).height/2);

        GameTextView lbResult = new GameTextView(bg3);
        lbResult.setFontSize(35);
        lbResult.setFont(FontCache.Font.UVNKyThuat);
        mappingChild(lbResult, "lbResult");

        Button btnReplay = new Button(resultView);
        btnReplay.setSpriteAnimation(R.drawable.school_iq_quiz_button_restart);
//        countDownCircle.setPosition(, height/2 - countDownCircle.getContentSize(false).height/2 + bg2.getContentSize(false).height/2 + 150);
        btnReplay.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 30 + btnReplay.getContentSize(false).height), LayoutPosition.getRule("left", width/2 - btnReplay.getContentSize(false).width/2)));
        mappingChild(btnReplay, "btnReplay");

        initQuestion();
        loadQuestion(0);
    }

    private void initQuestion(){
        ItemPartSprite[] sprites = new ItemPartSprite[5];
        CraftQuest quest = TestsConfig.getCraftTest(0).quests.get(0);
        for(int i=0;i<Math.max(sprites.length, quest.resIds.length);i++){
            sprites[i] = new ItemPartSprite(getChild("testing"));
            if(i < quest.resIds.length) {
                sprites[i].setSpriteAnimation(quest.resIds[i]);
                if (i == 0)
                    sprites[i].setCore(true);
                else {
                    sprites[i].debugSubscribeView(sprites[0]);
                    sprites[i].setLinkPos(quest.relativePositions[i - 1]);
                }
            }
            else{
                sprites[i].hide();
            }
            mappingChild(sprites[i],"part" + i);
        }
    }

    public void setLevel(String category){
        int testIndex = 0;
        switch(category){
            case "item": testIndex = 0; break;
            case "vehicle": testIndex = 1; break;
            case "machine": testIndex = 2; break;
            default: testIndex = 0;
        }

        GameView result = getChild("result");
        GameView test = getChild("testing");
        test.show();
        result.hide();
        currentTest = TestsConfig.getCraftTest(testIndex);
        level = testIndex;
        loadQuestion(0);
    }

    protected void loadQuestion(int index){
        super.loadQuestion(index);
        GameTextView lbTitle = (GameTextView) getChild("lbTitle");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Câu số ", new RelativeSizeSpan(1.5f), 0).append(String.valueOf(index + 1), new RelativeSizeSpan(1.5f), 0).setSpan(new StyleSpan(Typeface.BOLD), 0, builder.length(), 0);
        builder.append("/").append(String.valueOf(currentTest.getQuestions().size()));
        lbTitle.setText(builder);

        CraftQuest quest = ((CraftTest)currentTest).quests.get(currentQuestion);
        ItemPartSprite core = null;
        for(int i=0;i<Math.max(5, quest.resIds.length);i++){
            ItemPartSprite sprite = (ItemPartSprite)getChild("part" + (i < quest.orders.length ? quest.orders[i] : i));
            sprite.reset();
            if(i < quest.resIds.length) {
                sprite.show();
                sprite.setSpriteAnimation(quest.resIds[i]);
                int width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
                sprite.setPosition(
                        (float)Math.random()*(width - sprite.getContentSize(false).width),
                        (float)Math.random()*(height - sprite.getContentSize(false).height)
                );
                if (i == 0){
                    sprite.setCore(true);
                    core = sprite;
                }
                else {
                    sprite.setCore(false);
                    sprite.debugSubscribeView(core);
                    sprite.setLinkPos(quest.relativePositions[i - 1]);
                }
            }
            else{
                sprite.hide();
            }
        }
    }

    protected void resetTest(){
        for(CraftQuest q : ((CraftTest) currentTest).getQuestions()){
            q.isFinished = false;
        }
        super.resetTest();
    }

    protected int showResult(){
        int score = super.showResult();
        CraftTest test = ((CraftTest) currentTest);
        GameView result = getChild("result");

        // Call cho Achivement manager
        Achievement a = AchievementManager.getInstance().onFinishedTest("gara", level, score, test.getQuestions().size());
        if(a != null){
            AchievementPopup popup = new AchievementPopup(result);
            popup.loadAchivement(a);
        }

        GameTextView lbResultTitle = (GameTextView)getChild("lbResultTitle");
        if(score * 1f/test.getQuestions().size() >= 0.9f){
            lbResultTitle.setText(Utils.getString(R.string.text_congratulation));
        }
        else{
            lbResultTitle.setText(Utils.getString(R.string.text_result));
        }
        lbResultTitle.setPositionCenterScreen(false, true);

        GameTextView lbResult = (GameTextView)getChild("lbResult");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String strScore = score + "/" + test.getQuestions().size();
        builder.clear();
        builder.append(Utils.getString(R.string.text_result_answer_correct).replace("@", strScore));
        int pos = Utils.getString(R.string.text_result_answer_correct).indexOf('@');
        builder.setSpan(new RelativeSizeSpan(1.5f), pos, pos+strScore.length(), 0);
        lbResult.setText(builder);
        lbResult.setPositionCenterParent(false, false);
        lbResult.move(0, -10);

        return score;
    }

    private void initButtons(){
        final GaraTestScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        onBackButton(new Runnable() {
                            @Override
                            public void run() {
                                Director.getInstance().loadScene(SceneCache.getScene("gara"));
                            }
                        });
                    }
                });

                Button next = (Button)getChild("btnNext");
                next.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        submitAnswer();
                        nextQuestion();
                    }
                });

                Button replay = (Button)getChild("btnReplay");
                replay.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        resetTest();
                    }
                });
            }
        });
    }

    private boolean checkAllPartsAttach(){
        CraftQuest q = ((CraftTest) currentTest).getQuestions().get(currentQuestion);
        ItemPartSprite sprite = (ItemPartSprite)getChild("part" + q.orders[0]);
        return sprite.isAllAttached();
    }

    private void submitAnswer(){
        CraftQuest oldQ = ((CraftTest) currentTest).getQuestions().get(currentQuestion);
        oldQ.isFinished = checkAllPartsAttach();
        if(oldQ.isFinished){
            Sounds.play(R.raw.sound_correct);
        }
        else{
            Sounds.play(R.raw.sound_wrong);
        }
    }

    @Override
    public void onTimeOut() {
        stopCountDown();
        if(!getVisible()) return;
        showResult();
    }
}
