package com.mus.myapplication.modules.views.base;

import java.util.HashMap;

public class ComplexUI extends Sprite {
    protected HashMap<String, GameView> childrenMap = new HashMap<>();
    public ComplexUI(GameView parent){
        super(parent);
    }

    public ComplexUI(){
        super();
    }

    protected void mappingChild(GameView child, String name){
        childrenMap.put(name, child);
        child.setName(name);
    }

    protected GameView getChild(String name){
        return childrenMap.get(name);
    }
}
