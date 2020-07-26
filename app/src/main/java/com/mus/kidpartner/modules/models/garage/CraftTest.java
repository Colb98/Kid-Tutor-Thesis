package com.mus.kidpartner.modules.models.garage;

import com.mus.kidpartner.modules.models.common.TestModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftTest implements TestModel {
    public List<CraftQuest> quests;
    public CraftTest(CraftQuest... quests){
        this.quests = new ArrayList<>();
        this.quests.addAll(Arrays.asList(quests));
    }

    public List<CraftQuest> getQuestions() {
        return quests;
    }
}
