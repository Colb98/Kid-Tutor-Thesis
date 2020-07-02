package com.mus.myapplication.modules.classes;

import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.scene.AchievementScene;
import com.mus.myapplication.modules.views.scene.MapScene;
import com.mus.myapplication.modules.views.school.IQTestScene;
import com.mus.myapplication.modules.views.school.SchoolScene;

import java.util.HashMap;

public class SceneCache {
    private static HashMap<String, GameScene> map;
    public static GameScene getScene(String name){
        GameScene scene;
        GameView mainView = Director.getInstance().getMainView();
        switch(name){
            case "school":
                scene = new SchoolScene(mainView); break;
            case "map":
                scene = new MapScene(mainView); break;
            case "iq":
                scene = new IQTestScene(mainView); break;
            case "achievement":
                scene = new AchievementScene(mainView); break;
            default: return null;
        }
        return scene;
    }
}
