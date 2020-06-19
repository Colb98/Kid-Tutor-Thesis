package com.mus.myapplication.modules.views.base;

import android.widget.RelativeLayout;

import java.util.HashMap;

public class GameScene extends GameView{
    protected HashMap<String, GameView> childrenMap = new HashMap<>();

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
        this.setLayoutParams(lp);
    }

    protected void mappingChild(GameView child, String name){
        childrenMap.put(name, child);
        child.setName(name);
    }

    protected GameView getChild(String name){
        return childrenMap.get(name);
    }
}
