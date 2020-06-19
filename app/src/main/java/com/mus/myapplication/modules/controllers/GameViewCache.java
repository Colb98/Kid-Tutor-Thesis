package com.mus.myapplication.modules.controllers;

import com.mus.myapplication.modules.views.base.GameView;

import java.util.HashMap;

public class GameViewCache {
    private static HashMap<String, GameView> map = new HashMap<>();
    public static String SETTING = "setting";

    public static void putView(String name, GameView view){
        map.put(name, view);
    }

    public static GameView getView(String name){
        return map.get(name);
    }
}
