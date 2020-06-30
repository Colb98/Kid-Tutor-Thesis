package com.mus.myapplication.modules.views.base;

import android.util.Log;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;

import java.util.HashMap;

public class GameScene extends GameView{

    public GameScene(GameView parent) {
        super();
        this.viewType = SCENE;
        parent.addChild(this);
//        setUpLayoutSize();
    }

    public GameScene(){
        super();
        viewType = SCENE;
//        setUpLayoutWidth();
    }

    @Override
    protected void afterAddChild(){
        super.afterAddChild();
        setUpLayoutSize();
    }

    private void setUpLayoutSize() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        }
        else{
            lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        }
        this.setLayoutParams(lp);
    }

    protected void setSceneBackground(int resIdBackground){
        Sprite bg = new Sprite(this);
        bg.setSpriteAnimation(resIdBackground);
        Size bgSize = bg.getContentSize(false);
        float widthRatio = bgSize.width / Utils.getScreenWidth();
        float heightRatio = bgSize.height / Utils.getScreenHeight();
        float scaleRatio;
//        Log.d("scene", "width ratio: " + widthRatio + " " + heightRatio);
        if(widthRatio < 1 || heightRatio < 1){
            scaleRatio = Math.min(widthRatio, heightRatio);
        }
        else{
            scaleRatio = Math.min(widthRatio, heightRatio);
        }
        bg.setScale(1/scaleRatio);
        mappingChild(bg, "background");
    }
}
