package com.mus.myapplication.modules.views.base;

import java.util.HashMap;

public class ComplexUI extends Sprite {
    protected HashMap<String, GameView> childrenMap;
    public ComplexUI(GameView parent){
        super();
        childrenMap = new HashMap<>();
        parent.addChild(this);
    }

    public ComplexUI(){
        super();
        childrenMap = new HashMap<>();
    }

    protected void mappingChild(GameView child, String name){
        childrenMap.put(name, child);
        child.setName(name);
    }

    protected GameView getChild(String name){
        return childrenMap.get(name);
    }
}
