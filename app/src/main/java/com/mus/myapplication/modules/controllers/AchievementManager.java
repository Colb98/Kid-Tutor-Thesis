package com.mus.myapplication.modules.controllers;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.models.common.Achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievementManager {
    private final static HashMap<String, String> categoryMap = new HashMap<String, String>(){{
        put("iq", "school");
        put("math", "school");
//        put("alphabet", "school");
//        put("garage", "garage");
//        put("family", "home");
        put("food", "restaurant");
    }};
    public final static HashMap<String, HashMap<Integer, Integer>> resIdMap = new HashMap<String, HashMap<Integer, Integer>>(){{
        put("iq", new HashMap<Integer, Integer>(){{
            put(0, R.drawable.iq_level_1);
            put(1, R.drawable.iq_level_2);
            put(2, R.drawable.iq_level_3);
        }});
        put("math", new HashMap<Integer, Integer>(){{
            put(0, R.drawable.mathematician_level_1);
            put(1, R.drawable.mathematician_level_2);
            put(2, R.drawable.mathematician_level_3);
        }});
        put("food", new HashMap<Integer, Integer>(){{
            put(0, R.drawable.chef_level_1);
            put(1, R.drawable.chef_level_2);
            put(2, R.drawable.chef_level_3);
        }});
    }};
    private static AchievementManager instance;
    private List<Achievement> achievements;
    private HashMap<String, HashMap<Integer, Achievement>> allAchievements;

    public static AchievementManager getInstance(){
        if(instance == null){
            instance = new AchievementManager();
        }
        return instance;
    }

    private AchievementManager(){
        achievements = new ArrayList<>();
        allAchievements = new HashMap<>();
        initAllAchievements();
    }

    private void initAllAchievements(){
//        String[] categories = new String[]{"iq", "alphabet", "math", "garage", "family", "food"};
        for(String category : categoryMap.keySet()){
            HashMap<Integer, Achievement> hm = new HashMap<>();
            allAchievements.put(category, hm);
            for(int i=0;i<3;i++){
                hm.put(i, new Achievement(category, i, resIdMap.get(category).get(i), categoryMap.get(category)));
            }
        }
    }

    public Achievement getAchievement(String category, int level){
        return allAchievements.get(category).get(level);
    }

    public void onFinishedTest(String category, int level, int score, int maxScore){
        if(score == maxScore){
            Achievement a = getAchievement(category, level);
            a.setAchieved();
            achievements.add(a);
        }
    }

    // Load and Save achievement
    // Reset achievement
}
