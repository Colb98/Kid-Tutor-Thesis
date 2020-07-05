package com.mus.myapplication.modules.views.base;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
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
    private Size viewSize;

    // Distance to original position
    private Point contentPosition = new Point(0,0);

    private float minDx = -1000;
    private float minDy = -1000;
    private float maxDx = 1000;
    private float maxDy = 1000;

    View[] subView = new View[1];
    ViewContainer.Node containerRootNode;
    ViewContainer childContainer;

    public ScrollView(Size size){
        super();
        scrollType = ScrollType.BOTH;
        viewSize = new Size(size.width, size.height);
        realContentSize = new Size(size.width, size.height);
        setContentSize(size.width, size.height);
    }

    public ScrollView(float width, float height){
        super();
        scrollType = ScrollType.BOTH;
        viewSize = new Size(width, height);
        realContentSize = new Size(width, height);
        setContentSize(width, height);
    }

    public ScrollView(GameView parent, Size size){
        super();
        scrollType = ScrollType.BOTH;
        viewSize = new Size(size.width, size.height);
        realContentSize = new Size(size.width, size.height);
        parent.addChild(this);
        setContentSize(size.width, size.height);
        setUpLayoutSize();
    }

    public ScrollView(GameView parent, float width, float height){
        super();
        scrollType = ScrollType.BOTH;
        viewSize = new Size(width, height);
        realContentSize = new Size(width, height);
        parent.addChild(this);
        setContentSize(width, height);
        setUpLayoutSize();
    }

    @Override
    protected void afterAddChild(){
        initSubView();
        super.afterAddChild();
        setUpLayoutSize();
    }

    private void setUpLayoutSize() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams((int) viewSize.width,(int) viewSize.height);
        }
        else{
            lp.width = (int) viewSize.width;
            lp.height = (int) viewSize.height;
        }
        this.setLayoutParams(lp);
        resetContainerBound();
    }


    protected void resetViewBound(){
//        Log.d("Scroll", getName() + " reset View Bound" + viewSize + realContentSize);
        if(viewSize == null) return;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams((int)viewSize.width, (int)viewSize.height);
            this.setLayoutParams(lp);
        }
        else{
            lp.width = (int)viewSize.width;
            lp.height = (int)viewSize.height;
        }
//        Log.d("resetViewBound", viewSize.toString());
//        invalidate();

        this.resetContainerBound();
    }

    protected void resetContainerBound(){
////        Log.d("Scroll", getName() + " reset resetContainerBound Bound" + viewSize + realContentSize);
//        if(viewSize == null) return;
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
//        if(lp == null){
//            lp = new RelativeLayout.LayoutParams((int)viewSize.width, (int)viewSize.height);
//        }
//        else{
//            lp.width = (int)viewSize.width;
//            lp.height = (int)viewSize.height;
//        }
//        Log.d("resetContainerBound", "view" + viewSize);
//        this.container.setLayoutParams(lp);
//        this.container.invalidate();
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
//            Log.d(LOGTAG, "distance: " + distance + " " + contentPosition);
            if(scrollType == ScrollType.HORIZONTAL || scrollType == ScrollType.NONE){
                distance.y = 0;
            }
            if(scrollType == ScrollType.VERTICAL || scrollType == ScrollType.NONE){
                distance.x = 0;
            }

            if(contentPosition.x + distance.x < minDx){
                distance.x = minDx - contentPosition.x;
            }
            else if(contentPosition.x + distance.x > maxDx){
                distance.x = maxDx - contentPosition.x;
            }
            if(contentPosition.y + distance.y < minDy){
                distance.y = minDy - contentPosition.y;
            }
            else if(contentPosition.y + distance.y > maxDy){
                distance.y = maxDy - contentPosition.y;
            }

            lastMoveTimestamp = System.currentTimeMillis();
            floatVelocity = distance;
            contentPosition = contentPosition.add(distance);

            moveAllChild(distance);
//            Log.d("SCR", "contentPos: " + contentPosition);

            prevTouch = cur;
        }
    }

    private void moveAllChild(Point distance) {
        for (GameView child : getChildren()) {
            if (child.getViewType() == GameView.SPRITE) {
                child.move(distance);
            }
        }
    }

    public void setMinScroll(Point p){
        minDx = p.x;
        minDy = p.y;
    }

    public void setScrollType(ScrollType type){
        scrollType = type;
    }

    public ScrollType getScrollType(){
        return scrollType;
    }

    @Override
    public ViewContainer getContainerForChild() {
        return childContainer;
    }

    public void setContentSize(float width, float height) {
        if(contentSize == null) contentSize = new Size();
        contentSize.width = width;
        contentSize.height = height;

        minDx = 0;
        minDy = 0;
        maxDx = contentSize.width - viewSize.width;
        maxDy = contentSize.height - viewSize.height;
        contentPosition = new Point(maxDx, maxDy);
        invalidate();
    }

    public void setViewSize(float width, float height) {
        if(viewSize == null) viewSize = new Size();
//        Log.d("DEBUG: ", "real: " + viewSize + ", fic: " + contentSize + ", param: " + new Size(width, height));
        viewSize.width = width;
        viewSize.height = height;

        if(childContainer != null){
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) childContainer.getLayoutParams();
            lp.width = (int)width;
            lp.height = (int)height;
            childContainer.requestLayout();
        }
//        if(contentPosition.x < minDx || contentPosition.x > maxDx || contentPosition.y < minDy || contentPosition.y > maxDy){
//            moveAllChild(contentPosition.product(-1));
//            contentPosition = new Point(0,0);
//        }
        minDx = 0;
        minDy = 0;
        maxDx = contentSize.width - viewSize.width;
        maxDy = contentSize.height - viewSize.height;
        invalidate();
    }

    public void debug(){
        RelativeLayout.LayoutParams clp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        Log.d("DEBUG", "mRealContentSize: " + realContentSize + ", mContentSize: " + contentSize + ", viewSize: " + viewSize);
        Log.d("DEBUG", "container width, height: " + clp.width + " " + clp.height);
        Log.d("DEBUG", "object width, height: " + lp.width + " " + lp.height);
    }

    public void setScale(float scale){
    }

    @Override
    public void addChild(GameView child) {
        super.addChild(child);
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
                if(contentPosition.x + distance.x > maxDx ||
                        contentPosition.x + distance.x < minDx ||
                        contentPosition.y + distance.y > maxDy ||
                        contentPosition.y + distance.y < minDy){
                    floating = false;
                    floatDrag.x = floatDrag.y = 0;
                }
//                Log.d("Scrolling", "t = " + t + " velocity: " + floatVelocity + " distance: " + distance);

                // Khi nào vector lực đổi chiều thì dừng
                if(newV.x * floatVelocity.x >= 0
                        && newV.y * floatVelocity.y >= 0
                        && !((newV.x * floatVelocity.x == 0 && newV.y * floatVelocity.y == 0))){
                    floatVelocity = newV;
                    moveAllChild(distance);
                    contentPosition = contentPosition.add(distance);
//                    Log.d("SCR", "contentPos: " + contentPosition);
                }
                else{
                    floating = false;
                    floatDrag.x = 0; floatDrag.y = 0;
                }
            }
        }
    }

    @Override
    public ViewContainer.Node getViewTreeNodeAsParent() {
        return containerRootNode;
    }

    private void initSubView(){
        childContainer = new ViewContainer(Director.getInstance().getContext());
        childContainer.setLayoutParams(new RelativeLayout.LayoutParams((int)viewSize.width, (int)viewSize.height));
        childContainer.setWorldPosition(getWorldPosition());
        containerRootNode = childContainer.setRoot(this);
        subView[0] = childContainer;
        hasOwnViewContainer = true;
    }

    @Override
    public int getSubViewsCount() {
        return 1;
    }

    @Override
    public View[] getSubViews() {
        return subView;
    }
}
