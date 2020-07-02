package com.mus.myapplication.modules.models.common;

import com.mus.myapplication.modules.views.base.GameScene;

public class Achievement {
    String category;
    int level;
    int resIcon;
    // String to open the relative scene
    String destinationName;
    long achievedTimestamp;

    public Achievement(String cate, int lv, int res, String destination){
        category = cate;
        level = lv;
        resIcon = res;
        destinationName = destination;
        achievedTimestamp = -1;
    }

    public void setAchieved(){
        achievedTimestamp = System.currentTimeMillis();
    }

    public boolean isAchieved(){
        return achievedTimestamp > 0;
    }
}
