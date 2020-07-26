package com.mus.kidpartner.modules.models.common;

import java.util.List;

public interface TestModel {
     <T extends QuestionModel> List<T> getQuestions();
}
