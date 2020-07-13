package com.mus.myapplication.modules.models.school;

import com.mus.myapplication.modules.models.common.QuestionModel;
import com.mus.myapplication.modules.models.common.TestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IQTest implements TestModel {
    private List<IQQuestion> questions;

    public IQTest(IQQuestion... questions){
        this.questions = new ArrayList<>();
        this.questions.addAll(Arrays.asList(questions));
    }

    public List<IQQuestion> getQuestions() {
        return questions;
    }
}
