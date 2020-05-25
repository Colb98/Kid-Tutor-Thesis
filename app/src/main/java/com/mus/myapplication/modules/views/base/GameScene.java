package com.mus.myapplication.modules.views.base;

import android.widget.RelativeLayout;

public class GameScene extends GameView{

    public GameScene(GameView parent) {
        super(parent);
        this.viewType = SCENE;
        setUpLayoutSize();
    }

    public GameScene(){
        super();
        viewType = SCENE;
//        setUpLayoutWidth();
    }

    @Override
    protected void afterAddChild(){
        setUpLayoutSize();
    }

    private void setUpLayoutSize() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
    }
}
