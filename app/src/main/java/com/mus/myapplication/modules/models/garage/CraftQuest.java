package com.mus.myapplication.modules.models.garage;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.models.common.QuestionModel;

public class CraftQuest implements QuestionModel {
    // Image of parts, number 0 is the core;
    public int[] resIds;
    public Point[] relativePositions;
    public boolean isFinished;

    public CraftQuest(int[] resIds, Point[] positions){
        this.resIds = resIds;
        this.relativePositions = positions;
        isFinished = false;
    }

    @Override
    public boolean isCorrect() {
        return isFinished;
    }
}
