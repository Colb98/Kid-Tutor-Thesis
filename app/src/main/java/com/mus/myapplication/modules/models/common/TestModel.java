package com.mus.myapplication.modules.models.common;

import com.mus.myapplication.modules.models.school.IQQuestion;

import java.util.List;

public interface TestModel {
     <T extends QuestionModel> List<T> getQuestions();
}
