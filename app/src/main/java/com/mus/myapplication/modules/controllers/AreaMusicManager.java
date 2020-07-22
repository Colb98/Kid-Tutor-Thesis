package com.mus.myapplication.modules.controllers;

import android.media.MediaPlayer;

import com.mus.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AreaMusicManager {
    private static AreaMusicManager instance;
    private MediaPlayer currentPlayer;
    private String currentArea = "";
    private static final HashMap<String, List<Integer>> MUSIC = new HashMap<String, List<Integer>>(){{
        put("map", new ArrayList<Integer>(){{
            add(R.raw.toy_piano);
            add(R.raw.ukulele_beach);
            add(R.raw.after_school_jamboree);
            add(R.raw.old_macdonald_instrumental);
        }});
        put("test", new ArrayList<Integer>(){{
            add(R.raw.you_so_zany);
            add(R.raw.london_bridge_instrumental);
            add(R.raw.here_come_the_raindrops);
            add(R.raw.my_dog_is_happy);
        }});
        put("school", new ArrayList<Integer>(){{
            add(R.raw.wheels_on_the_bus_instrumental);
        }});
        put("family", new ArrayList<Integer>(){{
            add(R.raw.i_love_my_mom);
        }});
        put("bedroom", new ArrayList<Integer>(){{
            add(R.raw.twinkle_twinkle_little_star_instrumental);
        }});
        put("market", new ArrayList<Integer>(){{
            add(R.raw.twirly_tops);
        }});
        put("zoo", new ArrayList<Integer>(){{
            add(R.raw.twirly_tops);
        }});
        put("restaurant", new ArrayList<Integer>(){{
            add(R.raw.birthday_cake);
        }});
    }};
    private AreaMusicManager(){

    }

    public static AreaMusicManager getInstance(){
        if(instance == null){
            instance = new AreaMusicManager();
        }
        return instance;
    }

    public void pauseMusic(){
        currentPlayer.pause();
    }

    public void resumeMusic(){
        currentPlayer.start();
    }

    public MediaPlayer playAreaMusic(final String area){
        return playAreaMusic(area, false);
    }

    public MediaPlayer playAreaMusic(final String area, boolean force){
        // Đổi nhạc random, 1 số khu vực sẽ có nhạc riêng
        // Đổi từ view bình thường sang test sẽ đổi nhạc
        // Đổi từ test sang view sẽ đổi nhạc
        // Đổi giữa các khu vực thì random
        if(!area.equals("test") && !currentArea.equals("test") && !currentArea.equals("") && !force){
            if(Math.random() > 0.7f)
                return currentPlayer;
        }
        if(MUSIC.containsKey(area)){
            List<Integer> list = MUSIC.get(area);
            if(list == null) return currentPlayer;
            if(currentPlayer != null){
//                if(currentPlayer.isPlaying())
//                    currentPlayer.stop();
//                currentPlayer.release();
                Sounds.removePlayer(currentPlayer);
            }
            currentPlayer = Sounds.playLongSound(list.get((int)(Math.random()*list.size())));
            currentPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playAreaMusic(area, true);
                }
            });
        }
        return currentPlayer;
    }

    public static MediaPlayer playArea(String area){
        return getInstance().playAreaMusic(area);
    }
}
