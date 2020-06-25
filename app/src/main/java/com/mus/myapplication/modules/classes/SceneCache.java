package com.mus.myapplication.modules.classes;

import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.scene.MapScene;
import com.mus.myapplication.modules.views.scene.SchoolScene;

import java.util.HashMap;

public class SceneCache {
    private static HashMap<String, GameScene> map;
    public static GameScene getScene(String name){
        GameScene scene;
        switch(name){
            case "school":
                scene = new SchoolScene(Director.getInstance().getMainView()); break;
            case "map":
                scene = new MapScene(Director.getInstance().getMainView()); break;
            default: return null;
        }
        return scene;
    }
}
