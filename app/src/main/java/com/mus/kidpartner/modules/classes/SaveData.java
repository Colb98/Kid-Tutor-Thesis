package com.mus.kidpartner.modules.classes;

import com.mus.kidpartner.modules.controllers.AchievementManager;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.controllers.Sounds;
import com.mus.kidpartner.modules.models.common.Achievement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SaveData {
    public String user;
    public String googleId;
    public float volumeM;
    public float volumeS;
    public int achievement;

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("googleId", googleId);
        map.put("volumeS", volumeS);
        map.put("volumeM", volumeM);
        map.put("achievement", achievement);
        for(int i=0;i<achievement;i++){
            AchievementData a = achievements.get(i);
            map.put("cate_"+i, a.cate);
            map.put("level_"+i, a.level);
            map.put("time_"+i, a.time);
        }
        return map;
    }

    public void clearAchievementData() {
        this.achievement = 0;
        this.achievements.clear();
    }

    public static class AchievementData{
        public int idx;
        public String cate;
        public int level;
        public long time;

        @Override
        public String toString() {
            return "AchievementData{" +
                    "idx=" + idx +
                    ", cate='" + cate + '\'' +
                    ", level=" + level +
                    ", time=" + time +
                    '}';
        }
    }
    public List<AchievementData> achievements;

    public SaveData(){
        achievements = new ArrayList<>();
        volumeS = 0.7f;
        volumeM = 0.7f;
    }

    public static SaveData getSaveData(){
        SaveData ans = new SaveData();
        List<Achievement> achievements = AchievementManager.getInstance().getAchieved();
        ans.achievement = achievements.size();
        for(int i=0;i<achievements.size();i++){
            Achievement a = achievements.get(i);
            AchievementData data = new AchievementData();
            data.idx = i;
            data.cate = a.getCategory();
            data.level = a.getLevel();
            data.time = a.getAchievedTimestamp();
            ans.achievements.add(data);
        }
        ans.user = Director.getInstance().getUser();
        ans.googleId = Director.getInstance().getGoogleId();
        ans.volumeM = Sounds.getMusicVolume();
        ans.volumeS = Sounds.getSoundVolume();

        return ans;
    }

    public static void applySaveData(SaveData data){
        Sounds.setSoundVolume(data.volumeS);
        Sounds.setMusicVolume(data.volumeM);

        AchievementManager.getInstance().resetAchiements();

        for(int i=0;i<data.achievement;i++){
            AchievementData d = data.achievements.get(i);
            AchievementManager.getInstance().setAchieved(d.cate, d.level, d.time);
        }
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "user='" + user + '\'' +
                ", googleId='" + googleId + '\'' +
                ", volumeM=" + volumeM +
                ", volumeS=" + volumeS +
                ", achievement=" + achievement +
                ", achievements=" + achievements +
                '}';
    }
}
