package com.mus.kidpartner.modules.controllers;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.models.common.Achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AchievementManager {
    public static class CategoryInfo{
        public String destination;
        public String name;
        public int[] resIds;

        public CategoryInfo(String d, String n, int... ids){
            destination = d;
            name = n;
            resIds = ids;
        }
    }

    // TODO: dùng chuỗi localize tuỳ theo ngôn ngữ
    public final static HashMap<String, CategoryInfo> categoryMap = new HashMap<String, CategoryInfo>(){{
        put("iq", new CategoryInfo("school", "Trí tuệ", R.drawable.iq_level_1, R.drawable.iq_level_2, R.drawable.iq_level_3));
        put("math", new CategoryInfo("school", "Toán học", R.drawable.mathematician_level_1, R.drawable.mathematician_level_2, R.drawable.mathematician_level_3));
//        put("alphabet", "school");
        put("gara", new CategoryInfo("gara", "Kỹ sư", R.drawable.engineer_level_1, R.drawable.engineer_level_2, R.drawable.engineer_level_3));
//        put("family", "home");
        put("food", new CategoryInfo("kitchen", "Đầu bếp", R.drawable.chef_level_1, R.drawable.chef_level_2, R.drawable.chef_level_3));
        put("language", new CategoryInfo("language", "Ngôn ngữ", R.drawable.language_specialist_level_1, R.drawable.language_specialist_level_2, R.drawable.language_specialist_level_3));
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
                hm.put(i, new Achievement(category, i, categoryMap.get(category).resIds[i], categoryMap.get(category).destination));
            }
        }
    }

    public Achievement getAchievement(String category, int level){
        return allAchievements.get(category).get(level);
    }

    public Achievement onFinishedTest(String category, int level, int score, int maxScore){
        Achievement a = getAchievement(category, level);
        if(a.isAchieved())
            return null;
        if(score == maxScore){
            a.setAchieved();
            achievements.add(a);
            return a;
        }
        return null;
    }



    // Load and Save achievement
    // Reset achievement
}
