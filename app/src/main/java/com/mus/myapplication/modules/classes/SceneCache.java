package com.mus.myapplication.modules.classes;

import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.home.HomeScene;
import com.mus.myapplication.modules.views.home.BedroomScene;
import com.mus.myapplication.modules.views.home.LivingroomScene;
import com.mus.myapplication.modules.views.home.BathroomScene;
import com.mus.myapplication.modules.views.home.KitchenScene;
import com.mus.myapplication.modules.views.mart.MartScene;
import com.mus.myapplication.modules.views.zoo.ZooScene;
import com.mus.myapplication.modules.views.gara.GaraScene;
import com.mus.myapplication.modules.views.res.ResScene;
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
        scene = (GameScene) Director.getInstance().getMainView().getChild(name);
        if(scene != null) return scene;
        switch(name){
            case "school":
                scene = new SchoolScene(mainView); break;
            case "map":
                scene = new MapScene(mainView); break;
            case "iq":
                scene = new IQTestScene(mainView); break;
            case "achievement":
                scene = new AchievementScene(mainView); break;
            case "home":
                scene = new HomeScene(mainView); break;
            case "mart":
                scene = new MartScene(mainView); break;
            case "zoo":
                scene = new ZooScene(mainView); break;
            case "gara":
                scene = new GaraScene(mainView); break;
            case "res":
                scene = new ResScene(mainView); break;
            case "bedroom":
                scene = new BedroomScene(mainView); break;
            case "livingroom":
                scene = new LivingroomScene(mainView); break;
            case "bathroom":
                scene = new BathroomScene(mainView); break;
            case "kitchen":
                scene = new KitchenScene(mainView); break;
            default: return null;
        }
        mainView.mappingChild(scene, name);
        return scene;
    }
}
