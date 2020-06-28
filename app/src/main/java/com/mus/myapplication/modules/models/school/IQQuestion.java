package com.mus.myapplication.modules.models.school;


public class IQQuestion {
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
