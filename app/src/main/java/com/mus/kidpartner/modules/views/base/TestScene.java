package com.mus.kidpartner.modules.views.base;

import android.util.Log;

import com.mus.kidpartner.modules.controllers.AreaMusicManager;
import com.mus.kidpartner.modules.models.common.QuestionModel;
import com.mus.kidpartner.modules.models.common.TestModel;
import com.mus.kidpartner.modules.views.popup.ConfirmPopup;

public abstract class TestScene extends GameScene {
    protected float timeRemain;
    public abstract void onTimeOut();
    protected static final int TEST_DURATION = 600;
    protected int currentQuestion;
    protected TestModel currentTest;
    protected int level;
    protected boolean stoppedCountDown = false;
    protected boolean isTesting = true;

    public TestScene(GameView parent){
        super(parent);
    }

    @Override
    public void show() {
        super.show();
        AreaMusicManager.playArea("test");
        isTesting = true;
    }

    @Override
    public void hide() {
        super.hide();
        stopCountDown();
        isTesting = false;
    }

    protected void loadQuestion(int index){
        currentQuestion = index;
    }

    protected void resetTest(){
        GameView testView = getChild("testing");
        testView.setVisible(true);

        GameView result = getChild("result");
        result.setVisible(false);

        stoppedCountDown = false;
        isTesting = true;
        timeRemain = TEST_DURATION;

        loadQuestion(0);
    }

    protected int showResult(){
        GameView testView = getChild("testing");
        testView.setVisible(false);

        GameView result = getChild("result");
        result.setVisible(true);
        TestModel test = currentTest;
        isTesting = false;
        stopCountDown();

        int score = 0;
        int i = 0;
        for(QuestionModel q : test.getQuestions()){
            i+=1;
            if(q.isCorrect())
                score++;
            else
                Log.d("Wrong:", "wrong at " + i);
        }
        return score;
    }


    protected void nextQuestion(){
        if(currentQuestion < currentTest.getQuestions().size() - 1)
            loadQuestion(currentQuestion + 1);
        else{
//            Log.d("Result", "Total true answer: " + score);
            showResult();
        }

    }

    public void onBackButton(Runnable onConfirmBack){
        Log.e("back","onbackbutton");
        if(!isTesting) {
            onConfirmBack.run();
            return;
        }
        Log.e("back", "showPopup");
        ConfirmPopup popup = new ConfirmPopup(this);
        popup.addOnRedCallback(onConfirmBack);
    }

    protected void stopCountDown(){
        stoppedCountDown=true;
    }
}
