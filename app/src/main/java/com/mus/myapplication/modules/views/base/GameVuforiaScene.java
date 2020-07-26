package com.mus.myapplication.modules.views.base;

import android.util.Log;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.vuforia.engine.ImageTargets.ImageTargets;

public class GameVuforiaScene extends GameScene {
    protected ImageTargets activityRef;
    protected String detectedObjectName;
    public GameVuforiaScene(GameView parent){
        super(parent);
    }

    protected void setSceneBackground(int resIdBackground){
        Sprite bg = new Sprite(this);
        bg.setSpriteAnimation(resIdBackground);
        Size bgSize = bg.getContentSize(false);
        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        float widthRatio = bgSize.width * 2 / width;
        float heightRatio = bgSize.height / height;
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

        Sprite barT = new Sprite(this);
        barT.setSpriteAnimation(R.drawable.white_10_10);
        barT.setScaleX(width/2/10);
        barT.setScaleY(height/4/10);
        barT.setZOrder(-1);

        Sprite barB = new Sprite(this);
        barB.setSpriteAnimation(R.drawable.white_10_10);
        barB.setScaleX(width/2/10);
        barB.setScaleY(height/4/10);
        barB.setPositionY(height*3/4);
        barB.setZOrder(-1);
    }

    public String onReceiveMessage(String message){
        return message;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        String name = activityRef.getDetectName();
        if(name != null && name.length() > 0){
            detectedObjectName = name;
        }
        else{
            detectedObjectName = null;
        }
    }

    public void onVuforiaBackBtn(){
        activityRef.onBackPressed();
    }

    public void setActivityRef(ImageTargets ref){
        activityRef = ref;
    }
}
