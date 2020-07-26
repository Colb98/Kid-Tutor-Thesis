package com.mus.kidpartner.modules.classes;

import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.views.base.GameScene;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.gara.GaraTestScene;
import com.mus.kidpartner.modules.views.home.HomeScene;
import com.mus.kidpartner.modules.views.home.BedroomScene;
import com.mus.kidpartner.modules.views.home.LivingroomScene;
import com.mus.kidpartner.modules.views.home.BathroomScene;
import com.mus.kidpartner.modules.views.home.KitchenScene;
import com.mus.kidpartner.modules.views.home.RelativeScene;
import com.mus.kidpartner.modules.views.mart.MartScene;
import com.mus.kidpartner.modules.views.school.ABCLearnScene;
import com.mus.kidpartner.modules.views.zoo.ZooScene;
import com.mus.kidpartner.modules.views.gara.GaraScene;
import com.mus.kidpartner.modules.views.res.ResScene;
import com.mus.kidpartner.modules.views.scene.AchievementScene;
import com.mus.kidpartner.modules.views.scene.MapScene;
import com.mus.kidpartner.modules.views.school.IQTestScene;
import com.mus.kidpartner.modules.views.school.SchoolScene;

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
            case "garaTest":
                scene = new GaraTestScene(mainView); break;
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
            case "relative":
                scene = new RelativeScene(mainView); break;
            case "alphabet":
                scene = new ABCLearnScene(mainView); break;
            default: return null;
        }
        mainView.mappingChild(scene, name);
        return scene;
    }
}
