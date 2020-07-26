package com.mus.kidpartner.modules.models.school;


import com.mus.kidpartner.modules.models.common.QuestionModel;

public class IQQuestion implements QuestionModel {
    private int index;

    // Resource Id
    private int question;
    private int[] answers;

    public int getQuestion() {
        return question;
    }

    public int[] getAnswers() {
        return answers;
    }

    public boolean isCorrect(){
        return 0 == answerIndex;
    }

    private int answerIndex = -1;

    public void setAnswerIndex(int id){
        answerIndex = id;
    }

    public IQQuestion(int idx, int qId, int... aIds){
        index = idx;
        question = qId;
        answers = aIds;
    }
}
