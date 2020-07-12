package com.mus.myapplication.modules.views.gara;

import android.util.Log;
import android.view.MotionEvent;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

import java.util.ArrayList;
import java.util.List;

public class ItemPartSprite extends Sprite {
    private static final float ATTACH_THRESHOLD = 10;
    private boolean isCore = false;
    private List<ItemPartSprite> attachedPart;
    private List<ItemPartSprite> childPart;
    private Point lastTouchPos;
    private Point linkPos;
    private ItemPartSprite subscribeView;
    private boolean isAttached = false;

    public ItemPartSprite(GameView parent){
        super(parent);
        attachedPart = new ArrayList<>();
        childPart = new ArrayList<>();
    }

    public void setCore(boolean val){
        isCore = val;
    }

    public void setLinkPos(Point pos){
        linkPos = pos;
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
    }

    @Override
    protected void onTouchBegan(MotionEvent event) {
        super.onTouchBegan(event);
        lastTouchPos = new Point(event.getRawX(), event.getRawY());
    }

    @Override
    protected void onTouchMoved(MotionEvent event) {
        super.onTouchMoved(event);
        Point curPos = new Point(event.getRawX(), event.getRawY());
        Point d = curPos.subtract(lastTouchPos);
        move(d);

//        Log.d("itempart", getName() + "has linked part count: " + linkedPart.size());
////        debug1();
        if(isCore){
            for(ItemPartSprite s : attachedPart){
                s.move(d);
            }
        }
        else if(isAttached){
            for(ItemPartSprite s : subscribeView.attachedPart){
                if(s != this)
                    s.move(d);
            }
            subscribeView.move(d);
        }
//        Log.d("itempart", getName() + " move d: " + d);
        lastTouchPos = curPos;
        if(isCore) {
            for(ItemPartSprite child : childPart){
                if(!child.isAttached && child.isNearAttachPoint()){
                    attachChild(child);
                }
            }
        }
        else if(isNearAttachPoint()){
            subscribeView.attachChild(this);
        }
    }

    private void attachChild(ItemPartSprite child){
        if(!attachedPart.contains(child)){
            attachedPart.add(child);
            child.isAttached = true;
            child.setPosition(getPosition().add(child.linkPos));
        }
    }

    public void reset(){
        childPart.clear();
        attachedPart.clear();
        subscribeView = null;
        isAttached = false;
    }

    public boolean isAllAttached(){
        for(ItemPartSprite child : childPart){
            if(!child.isAttached)
                return false;
        }
        return true;
    }

//    private void attach(ItemPartSprite v){
//        addLinkedPart(v);
//        for(ItemPartSprite s : v.linkedPart){
//            addLinkedPart(s);
//            s.addLinkedPart(this);
//        }
//        v.addLinkedPart(this);
//    }
//
//    private void addLinkedPart(ItemPartSprite s){
//        if(linkedPart.contains(s)) return;
//        linkedPart.add(s);
//    }

    public void debugSubscribeView(ItemPartSprite s){
        subscribeView = s;
        s.childPart.add(this);
    }

//    private void debug1(){
//        Log.d(getName(), "Debuging");
//        for(ItemPartSprite s : linkedPart){
//            Log.d("debug",s.getName());
//        }
//    }

    @Override
    protected void onTouchSucceed(MotionEvent event) {
        super.onTouchSucceed(event);
        if(subscribeView != null)
            Log.d("DEBUG ItemPart", getName() + " finished moving. Pos to subscribe view: " + getPosition().subtract(subscribeView.getPosition()));
    }

    private boolean isNearAttachPoint(){
        if(subscribeView == null)
            return false;

        Point d = getPosition().subtract(subscribeView.getPosition()).subtract(linkPos);
//        Log.d("test", getName() + "distance is: " + d + " ans: " +(d.x < ATTACH_THRESHOLD && d.y < ATTACH_THRESHOLD));
        return Math.abs(d.x) < ATTACH_THRESHOLD && Math.abs(d.y) < ATTACH_THRESHOLD;
    }
}
