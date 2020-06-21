package com.mus.myapplication.modules.views.base;

import android.widget.RelativeLayout;

import java.util.HashMap;

public class GameScene extends GameView{
    protected HashMap<String, GameView> childrenMap;

    public GameScene(GameView parent) {
        super();
        childrenMap = new HashMap<>();
        this.viewType = SCENE;
        parent.addChild(this);
//        setUpLayoutSize();
    }

    public GameScene(){
        super();
        childrenMap = new HashMap<>();
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

    protected void mappingChild(GameView child, String name){
        childrenMap.put(name, child);
        child.setName(name);
    }

    protected GameView getChild(String name){
        return childrenMap.get(name);
    }
}
