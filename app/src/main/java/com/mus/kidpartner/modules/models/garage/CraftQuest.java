package com.mus.kidpartner.modules.models.garage;

import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.models.common.QuestionModel;

public class CraftQuest implements QuestionModel {
    // Image of parts, number 0 is the core;
    public int[] resIds;
    public int[] orders;
    public Point[] relativePositions;
    public boolean isFinished;

    public CraftQuest(int[] resIds, Point[] positions, int[] orders){
        this.resIds = resIds;
        this.orders = orders;
        this.relativePositions = positions;
        isFinished = false;
    }

    @Override
    public boolean isCorrect() {
        return isFinished;
    }
}
