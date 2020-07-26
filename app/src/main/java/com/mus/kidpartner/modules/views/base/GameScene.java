package com.mus.kidpartner.modules.views.base;

import android.widget.RelativeLayout;

import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;

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
        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        float widthRatio = bgSize.width / width;
        float heightRatio = bgSize.height / height;
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

    public void onUserPressBack(){
        Sprite btnBack = (Sprite) getChild("btnBack");
        if(btnBack != null)
            btnBack.performClick();
    }
}
