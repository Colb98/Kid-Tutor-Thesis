package com.mus.myapplication.modules.views.base;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class ScrollView extends Sprite {
    private static final String LOGTAG = "ScrollView";
    public enum ScrollType{
        NONE, BOTH, VERTICAL, HORIZONTAL
    }

    private Point prevTouch;
    private ScrollType scrollType;
    private boolean isTouching = false;
    private boolean floating = false;
    private Point floatVelocity = new Point(0,0);
    private long firstTouchTimestamp;
    private long lastMoveTimestamp;
    private Point floatDrag = new Point(0, 0);

    // Distance to original position
    private float dx = 0;
    private float dy = 0;

    private float minDx = -1000;
    private float minDy = -1000;
    private float maxDx = 1000;
    private float maxDy = 1000;

    public ScrollView(){
        super();
        scrollType = ScrollType.BOTH;
    }

    public ScrollView(GameView parent){
        super(parent);
        scrollType = ScrollType.BOTH;
        setUpLayoutSize();
    }

    @Override
    protected void afterAddChild(){
        setUpLayoutSize();
    }

    private void setUpLayoutSize() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        }
        else{
            lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        }
        this.setLayoutParams(lp);
    }

    @Override
    protected void onTouchBegan(MotionEvent event) {
        super.onTouchBegan(event);
        isTouching = true;
        firstTouchTimestamp = System.currentTimeMillis();
        floating = false;
        prevTouch = new Point(event.getRawX(), event.getRawY());
        Log.d(LOGTAG, "Touch began");
    }

    @Override
    protected void onTouchMoved(MotionEvent event) {
        super.onTouchMoved(event);
        if(isTouching){
            Point cur = new Point(event.getRawX(), event.getRawY());
            Point distance = cur.subtract(prevTouch);
            if(scrollType == ScrollType.HORIZONTAL || scrollType == ScrollType.NONE){
                distance.y = 0;
            }
            if(scrollType == ScrollType.VERTICAL || scrollType == ScrollType.NONE){
                distance.x = 0;
            }

            if(dx + distance.x < minDx){
                distance.x = minDx - dx;
            }
            else if(dx + distance.x > maxDx){
                distance.x = maxDx - dx;
            }
            if(dy + distance.y < minDy){
                distance.y = minDy - dy;
            }
            else if(dy + distance.y > maxDy){
                distance.y = maxDy - dy;
            }

            lastMoveTimestamp = System.currentTimeMillis();
            floatVelocity = distance;

            for(GameView child : getChildren()){
                if(child.getViewType() == GameView.SPRITE){
                    child.move(distance);
                }
            }

            prevTouch = cur;
        }
    }

    public void setMinScroll(Point p){
        minDx = p.x;
        minDy = p.y;
    }

    public void setMinScroll(float x, float y){
        minDx = x;
        minDy = y;
    }

    public void setMaxScroll(Point p){
        maxDx = p.x;
        maxDy = p.y;
    }

    public void setMaxScroll(float x, float y){
        maxDx = x;
        maxDy = y;
    }

    public void setScrollType(ScrollType type){
        scrollType = type;
    }

    public ScrollType getScrollType(){
        return scrollType;
    }

    @Override
    public void addChild(GameView child) {
        super.addChild(child);
        Log.d("Scrollview", "Add child name: "  + child.getName());
        if(child.viewType == SPRITE) {
            ((Sprite)child).setSwallowTouches(false);
        }
    }

    @Override
    protected void onTouchSucceed(MotionEvent event) {
        super.onTouchSucceed(event);
        isTouching = false;
        if(lastMoveTimestamp - firstTouchTimestamp >= 50){
            float time = (System.currentTimeMillis() - lastMoveTimestamp)/1000f;
            floatVelocity.x /= time;
            floatVelocity.y /= time;
            floating = true;
            floatDrag.x = 0; floatDrag.y = 0;
        }
    }

    @Override
    protected void onTouchCanceled(MotionEvent event) {
        super.onTouchCanceled(event);
        isTouching = false;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(floating){
            float t = (System.currentTimeMillis() - lastMoveTimestamp)/1000f;
            if(t >= 3){
                floating = false;
                floatDrag.x = 0; floatDrag.y = 0;
            }
            else{
                if(floatDrag.equals(new Point(0,0))){
                    if(floatVelocity.x > Utils.getScreenWidth()/5){
                        floatVelocity.x = Utils.getScreenWidth()/5;
                    }
                    if(floatVelocity.x < -Utils.getScreenWidth()/5){
                        floatVelocity.x = -Utils.getScreenWidth()/5;
                    }
                    if(floatVelocity.y > Utils.getScreenHeight()/5){
                        floatVelocity.y = Utils.getScreenHeight()/5;
                    }
                    if(floatVelocity.y < -Utils.getScreenHeight()/5){
                        floatVelocity.y = -Utils.getScreenHeight()/5;
                    }
                    floatDrag = floatVelocity.product(-1.0f/2);
                }
                Point distance = floatVelocity.product(dt);
                Point newV = floatVelocity.add(floatDrag.product(2*dt));
                Log.d("Scrolling", "t = " + t + " velocity: " + floatVelocity + " distance: " + distance);

                if(newV.x * floatVelocity.x >= 0 && newV.y * floatVelocity.y >= 0){
                    floatVelocity = newV;
                    for(GameView child : getChildren()){
                        if(child.getViewType() == GameView.SPRITE){
                            child.move(distance);
                        }
                    }
                }
                else{
                    floating = false;
                    floatDrag.x = 0; floatDrag.y = 0;
                }
            }
        }
    }
}
