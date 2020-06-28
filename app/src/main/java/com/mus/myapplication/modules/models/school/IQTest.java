package com.mus.myapplication.modules.models.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IQTest {
    private List<IQQuestion> questions;

    public IQTest(IQQuestion... questions){
        this.questions = new ArrayList<>();
        this.questions.addAll(Arrays.asList(questions));
    }

    public List<IQQuestion> getQuestions() {
        return questions;
    }
}
