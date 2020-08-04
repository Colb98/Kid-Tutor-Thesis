package com.mus.kidpartner.modules.controllers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.Point;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class Sounds {
    private static SoundPool pool;
    private static HashMap<Integer, Integer> soundMap;
    private static HashSet<MediaPlayer> playingPlayers;
    private static float soundVolumeL = 1;
    private static float soundVolumeR = 1;
    private static float musicVolumeL = 0.7f;
    private static float musicVolumeR = 0.7f;


    public static void init(final Context context) throws ExecutionException, InterruptedException {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundMap = new HashMap<>();
        playingPlayers = new HashSet<>();

        pool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(audioAttributes).build();

        AsyncTask<Object, Integer, HashMap<Integer, Integer>> loadSounds = new AsyncTask<Object, Integer, HashMap<Integer, Integer>>() {
            @Override
            protected HashMap<Integer, Integer> doInBackground(Object... objects){
                Field[] raws = R.raw.class.getFields();
                ArrayList<Integer> resourceIds = new ArrayList<>();
                for(Field raw : raws){
                    try{
                        if(raw.getName().contains("sound")){
                            resourceIds.add(raw.getInt(null));
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                HashMap<Integer, Integer> map = new HashMap<>();
                for(int rId :  resourceIds){
                    int soundId = pool.load(context, rId, 0);
                    map.put(rId, soundId);
                }
                return map;
            }
        };

        soundMap = loadSounds.execute().get();
    }

    public static void play(final int resourceId){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
//        thread.start();
        if(soundMap.containsKey(resourceId)){
            pool.play(soundMap.get(resourceId), soundVolumeL, soundVolumeR, 0, 0, 1);
        }
    }

    public static float getSoundVolume(){
        return (soundVolumeL + soundVolumeR)/2;
    }

    public static Point getLRSoundVolume(){
        return new Point(soundVolumeL, soundVolumeR);
    }

    public static void setSoundVolume(float volume){
        soundVolumeL = soundVolumeR = volume;

        for(int streamId : soundMap.values()){
            pool.setVolume(streamId, volume, volume);
        }
    }

    public static void setSoundVolumeL(float volume) {
        Sounds.soundVolumeL = volume;
        for(int streamId : soundMap.values()){
            pool.setVolume(streamId, volume, soundVolumeR);
        }
    }

    public static void setSoundVolumeR(float volume) {
        Sounds.soundVolumeR = volume;
        for(int streamId : soundMap.values()){
            pool.setVolume(streamId, soundVolumeL, volume);
        }
    }

    public static float getMusicVolume(){
        return (musicVolumeL + musicVolumeR)/2;
    }

    public static Point getLRMusicVolume(){
        return new Point(musicVolumeL, musicVolumeR);
    }

    public static void setMusicVolume(float volume){
        musicVolumeL = musicVolumeR = volume;

        for(MediaPlayer mp : playingPlayers){
            mp.setVolume(volume, volume);
        }
    }

    public static void setMusicVolumeL(float volume) {
        Sounds.musicVolumeL = volume;

        for(MediaPlayer mp : playingPlayers){
            mp.setVolume(volume, musicVolumeR);
        }
    }

    public static void setMusicVolumeR(float volume) {
        Sounds.musicVolumeR = volume;

        for(MediaPlayer mp : playingPlayers){
            mp.setVolume(musicVolumeL, volume);
        }
    }


    public static MediaPlayer playLongSound(final int resourceId){
        final MediaPlayer mp = MediaPlayer.create(Director.getInstance().getContext(), resourceId);
        playingPlayers.add(mp);
        mp.start();
        mp.setVolume(musicVolumeL, musicVolumeR);
//        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.seekTo(0);
//                mp.start();
//            }
//        });
        return mp;
    }

    public static void removePlayer(MediaPlayer mp){
        playingPlayers.remove(mp);
        mp.release();
    }
}
