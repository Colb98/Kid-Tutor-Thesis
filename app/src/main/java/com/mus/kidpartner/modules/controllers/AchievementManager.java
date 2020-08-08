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
        public String desc;
        public int[] resIds;

        public CategoryInfo(String d, String n, String d2, int... ids){
            destination = d;
            name = n;
            desc = d2;
            resIds = ids;
        }
    }

    // TODO: dùng chuỗi localize tuỳ theo ngôn ngữ
    public final static HashMap<String, CategoryInfo> categoryMap = new HashMap<String, CategoryInfo>(){{
        put("iq", new CategoryInfo("Test IQ Trường học", "Trí tuệ", "Bài test IQ với nhiều mức độ khó khác nhau.\nKhó để đạt được lắm đấy nhé!", R.drawable.iq_level_1, R.drawable.iq_level_2, R.drawable.iq_level_3));
        put("math", new CategoryInfo("Toán Trường học", "Toán học", "Tính toán siêu hạng.\nHiện tại chưa mở khoá được bạn ơi.", R.drawable.mathematician_level_1, R.drawable.mathematician_level_2, R.drawable.mathematician_level_3));
//        put("alphabet", "school");
        put("gara", new CategoryInfo("Gara", "Kỹ sư", "Lắp ráp các đồ vật từ thông dụng đến hiếm gặp.\nHãy để trí tưởng tượng phát huy hết khả năng nhé!", R.drawable.engineer_level_1, R.drawable.engineer_level_2, R.drawable.engineer_level_3));
//        put("family", "home");
        put("food", new CategoryInfo("Nhà hàng", "Đầu bếp", "Nhận dạng món ăn trong một nốt nhạc! \nChờ phiên bản sau mới mở khoá nha!", R.drawable.chef_level_1, R.drawable.chef_level_2, R.drawable.chef_level_3));
        put("language", new CategoryInfo("ABC Trường học", "Ngôn ngữ", "Nhiều chữ quá đi! \nHãy cố gắng ghi nhớ để đạt được huy hiệu này!", R.drawable.language_specialist_level_1, R.drawable.language_specialist_level_2, R.drawable.language_specialist_level_3));
        put("zoo", new CategoryInfo("Sở thú", "Nhà ĐV học", "Nhà động vật học tài ba! \nNhận biết các loài động vật trong sở thú cực nhanh!\n Chờ phiên bản sau để mở khoá nha", R.drawable.language_specialist_level_1, R.drawable.language_specialist_level_2, R.drawable.language_specialist_level_3));
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

    public void resetAchiements() {
        for(Achievement a : achievements){
            a.reset();
        }
        achievements.clear();
    }

    public void setAchieved(String category, int level, long time){
        Achievement a = getAchievement(category, level);
        if(a.isAchieved()) return;

        a.setAchieved(time);
        if(!achievements.contains(a))
            achievements.add(a);
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
            Director.getInstance().saveAchievement(achievements.size()-1, a);
            return a;
        }
        return null;
    }


    public List<Achievement> getAchieved(){
        return achievements;
    }

    // Load and Save achievement
    // Reset achievement
}
