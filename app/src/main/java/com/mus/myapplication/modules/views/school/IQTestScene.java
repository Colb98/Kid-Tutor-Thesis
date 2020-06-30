package com.mus.myapplication.modules.views.school;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.models.school.IQQuestion;
import com.mus.myapplication.modules.models.school.IQTest;
import com.mus.myapplication.modules.models.school.TestsConfig;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.TestScene;
import com.mus.myapplication.modules.views.popup.AchievementPopup;

public class IQTestScene extends TestScene {
    private static final int TEST_DURATION = 15;
    private int currentQuestion;
    private int currentAnswer = -1;
    private int[] currentAnswerMap;
    private IQTest currentTest;

    public IQTestScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        timeRemain = TEST_DURATION;
        currentTest = TestsConfig.getTest(0);
        initScene();
        initButtons();
    }

    public void setTest(int testIndex){
        currentTest = TestsConfig.getTest(testIndex);
        loadQuestion(0);
    }

    private void initButtons(){
        final IQTestScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("school"));
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

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);
        Sprite bg = (Sprite) getChild("background");

        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();

        GameView testView = new GameView(this);
        mappingChild(testView, "testing");

        GameView resultView = new GameView(this);
        mappingChild(resultView, "result");
        resultView.setVisible(false);

        Sprite bg2 = new Sprite(testView);
        bg2.setSpriteAnimation(R.drawable.school_iq_quiz_background_2);
        bg2.setScale(0.8f*width/bg2.getContentSize(false).width);
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
                timeRemain -= dt;
                if(timeRemain < 0){
                    onTimeOut();
                    return;
                }
                lbCountDown.setText(Utils.secondToString(timeRemain));
            }
        });

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

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

    private void nextQuestion(){
        if(currentQuestion < currentTest.getQuestions().size() - 1)
            loadQuestion(currentQuestion + 1);
        else{
//            Log.d("Result", "Total true answer: " + score);
            showResult();
        }

    }

    private void showResult(){
        IQTest test = currentTest;
        GameView testView = getChild("testing");
        testView.setVisible(false);

        GameView result = getChild("result");
        result.setVisible(true);

        int score = 0;
        int i = 0;
        for(IQQuestion q : test.getQuestions()){
            i+=1;
            if(q.isCorrect())
                score++;
            else
                Log.d("Wrong:", "wrong at " + i);
        }
        // Call cho Achivement manager
//        AchievementPopup popup = new AchievementPopup(result);

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
    }

    private void resetTest(){
        GameView testView = getChild("testing");
        testView.setVisible(true);

        GameView result = getChild("result");
        result.setVisible(false);

        for(IQQuestion q : currentTest.getQuestions()){
            q.setAnswerIndex(-1);
        }
        currentAnswer = -1;
        timeRemain = TEST_DURATION;
        loadQuestion(0);
    }

    private void loadQuestion(int index){
//        IQQuestion question = currentTest.getQuestions().get(index);
        IQQuestion question = currentTest.getQuestions().get(index);
        currentQuestion = index;
        currentAnswer = -1;
        GameTextView lbTitle = (GameTextView) getChild("lbTitle");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Câu số ", new RelativeSizeSpan(1.5f), 0).append(String.valueOf(index + 1), new RelativeSizeSpan(1.5f), 0).setSpan(new StyleSpan(Typeface.BOLD), 0, builder.length(), 0);
        builder.append("/").append(String.valueOf(currentTest.getQuestions().size()));
        setIndicatorPos(-1);

        lbTitle.setText(builder);
        randomAnswerMap();

        GameImageView ivQuestion = (GameImageView) getChild("question");
        GameImageView a1 = (GameImageView) getChild("a1");
        GameImageView a2 = (GameImageView) getChild("a2");
        GameImageView a3 = (GameImageView) getChild("a3");
        GameImageView a4 = (GameImageView) getChild("a4");
        GameImageView a5 = (GameImageView) getChild("a5");
        GameImageView a6 = (GameImageView) getChild("a6");

        ivQuestion.setSpriteKeepFormat(question.getQuestion());
        a1.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[0]]);
        a2.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[1]]);
        a3.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[2]]);
        a4.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[3]]);
        a5.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[4]]);
        a6.setSpriteKeepFormat(question.getAnswers()[currentAnswerMap[5]]);
    }

    private void submitAnswer() {
        IQQuestion oldQ = currentTest.getQuestions().get(currentQuestion);
        oldQ.setAnswerIndex(currentAnswer);
    }

    private void randomAnswerMap(){
        currentAnswerMap = new int[]{0,1,2,3,4,5};
        for(int i=1;i<6;i++){
            int j = (int)(Math.random()*(i+1));
            if(j != i){
                int t = currentAnswerMap[i];
                currentAnswerMap[i] = currentAnswerMap[j];
                currentAnswerMap[j] = t;
            }
        }
    }

    private void initQuestion(){
        Sprite panel = (Sprite) getChild("questionPanel");
        GameImageView question = new GameImageView(panel);
        question.init(R.drawable.school_iq_test1_q2_ques);
        question.setImageViewBound(panel.getContentSize(false).width*0.45f, panel.getContentSize(false).height * 0.9f);
        question.setPosition(15, (panel.getContentSize(false).height - question.getContentSize(false).height)/2);

        // Distance between answer
        float dis;
        GameImageView a1 = new GameImageView(panel);
        a1.init(R.drawable.school_iq_test1_q2_ans1);
        a1.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        Size size = a1.getContentSize(false);
        dis = (panel.getContentSize(false).width*0.55f - 3*size.width)/5;
        a1.setPositionCenterParent(true, false);
        a1.move(0, -size.height/2-dis/2);
        a1.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 3 + dis*3)));
        GameImageView a2 = new GameImageView(panel);
        a2.init(R.drawable.school_iq_test1_q2_ans2);
        a2.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a2.setPosition(a1.getPosition());
        a2.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 2 + dis * 2)));
        GameImageView a3 = new GameImageView(panel);
        a3.init(R.drawable.school_iq_test1_q2_ans3);
        a3.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a3.setPosition(a1.getPosition());
        a3.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width + dis)));
        GameImageView a4 = new GameImageView(panel);
        a4.init(R.drawable.school_iq_test1_q2_ans4);
        a4.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a4.setPositionCenterParent(true, false);
        a4.move(0, size.height/2+dis/2);
        a4.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 3 + dis*3)));
        GameImageView a5 = new GameImageView(panel);
        a5.init(R.drawable.school_iq_test1_q2_ans5);
        a5.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a5.setPosition(a4.getPosition());
        a5.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 2 + dis*2)));
        GameImageView a6 = new GameImageView(panel);
        a6.init(R.drawable.school_iq_test1_q2_ans6);
        a6.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a6.setPosition(a4.getPosition());
        a6.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width + dis)));

        GameImageView selectIndicator = new GameImageView(panel);
        selectIndicator.init(R.drawable.school_iq_quiz_select_border);
        selectIndicator.setImageViewBound(a1.getContentSize(false).multiply(1.2f));
        selectIndicator.setPosition(a1.getPosition());
        Size diff = selectIndicator.getContentSize(false).minus(a1.getContentSize(false)).multiply(0.5f);
        selectIndicator.move(-diff.width, -diff.height);
        mappingChild(selectIndicator, "indicator");

        a1.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(0);
            }
        });

        a2.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(1);
            }
        });

        a3.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(2);
            }
        });

        a4.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(3);
            }
        });

        a5.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(4);
            }
        });

        a6.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                setCurrentAnswer(5);
            }
        });

        mappingChild(question, "question");
        mappingChild(a1, "a1");
        mappingChild(a2, "a2");
        mappingChild(a3, "a3");
        mappingChild(a4, "a4");
        mappingChild(a5, "a5");
        mappingChild(a6, "a6");
    }

    private void setCurrentAnswer(int ans){
        currentAnswer = currentAnswerMap[ans];
        // Move indicator
        setIndicatorPos(ans);
    }

    private void setIndicatorPos(int ans){
        GameImageView indi = (GameImageView) getChild("indicator");
        if(ans == -1){
            indi.hide();
        }
        else{
            indi.show();
            GameImageView a = (GameImageView) getChild("a" + (ans+1));
            indi.setPosition(a.getPosition());
            Size diff = indi.getContentSize(false).minus(a.getContentSize(false)).multiply(0.5f);
            indi.move(-diff.width, -diff.height);
        }
    }

    @Override
    public void onTimeOut() {
        showResult();
    }
}
