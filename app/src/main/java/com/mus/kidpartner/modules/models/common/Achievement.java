package com.mus.kidpartner.modules.models.common;

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

    public void setAchieved(long t){
        achievedTimestamp = t;
    }

    public boolean isAchieved(){
        return achievedTimestamp > 0;
    }

    public String getCategory() {
        return category;
    }

    public int getLevel() {
        return level;
    }

    public int getResIcon() {
        return resIcon;
    }

    public long getAchievedTimestamp() {
        return achievedTimestamp;
    }

    public void reset() {
        achievedTimestamp = -1;
    }
}
