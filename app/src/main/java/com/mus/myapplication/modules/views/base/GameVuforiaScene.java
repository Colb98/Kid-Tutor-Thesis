package com.mus.myapplication.modules.views.base;

import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.vuforia.engine.ImageTargets.ImageTargets;

public class GameVuforiaScene extends GameScene {
    protected ImageTargets activityRef;
    public GameVuforiaScene(GameView parent){
        super(parent);
    }

    protected void setSceneBackground(int resIdBackground){
        Sprite bg = new Sprite(this);
        bg.setSpriteAnimation(resIdBackground);
        Size bgSize = bg.getContentSize(false);
        float widthRatio = bgSize.width * 2 / Utils.getScreenWidth();
        float heightRatio = bgSize.height / Utils.getScreenHeight();
        float scaleRatio;
//        Log.d("scene", "width ratio: " + widthRatio + " " + heightRatio);
        if(widthRatio < 1 || heightRatio < 1){
            scaleRatio = Math.min(widthRatio, heightRatio);
        }
        else{
            scaleRatio = Math.min(widthRatio, heightRatio);
        }
        bg.setPosition(Utils.getScreenWidth()/2, 0);
        bg.setScale(1/scaleRatio);
        mappingChild(bg, "background");
    }

    public void setActivityRef(ImageTargets ref){
        activityRef = ref;
    }
}
