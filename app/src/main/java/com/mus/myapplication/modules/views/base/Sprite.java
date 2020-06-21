package com.mus.myapplication.modules.views.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.views.base.actions.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// This class stand for almost every visual view
// Can handle Callbacks and add Animations by using a list of drawable resource
public class Sprite extends GameView{
    private static final String LOGTAG = "SpriteBase";

    public enum CallbackType {
        ON_TOUCH_DOWN,
        ON_TOUCH_UP,
        ON_TOUCH_MOVE,
        ON_TOUCH_CANCEL,
        ON_CLICK
    }

    protected Size contentSize = new Size(0, 0);
    protected Size realContentSize = new Size(0, 0);
    protected Size dSize = new Size(0, 0); // Drawable size
    private int animIdx = 0;
    private boolean isPlaying = false;
    private boolean isRepeating = true;
    private boolean debugMode = false;
    private Bitmap[] animSprites = null;
    protected float trueScale = 1f;
    private float oldRecurScale = 1f;
    protected float playTime = 0f;
    private float anchorX = 0.5f;
    private float anchorY = 0.5f;
    private LayoutPosition layoutRule;
    private Point prevTouch;
    private float prevTouchDistance = 0;

    private List<Action> actions;
    private List<Action> toBeRemovedActions;

    private HashMap<CallbackType, List<Runnable>> listenerCallbacks;
    protected boolean swallowTouches = true;
    protected boolean isTouched = false;

    public Sprite(){
        super();
        viewType = SPRITE;
        initListener();

        actions = new ArrayList<>();
        toBeRemovedActions = new ArrayList<>();

        resetViewBound();
        this.setAnchorPoint(0, 0);
        this.setScaleType(ScaleType.MATRIX);
    }

    public Sprite(GameView parent) {
        super();
        viewType = SPRITE;
        initListener();

        actions = new ArrayList<>();
        toBeRemovedActions = new ArrayList<>();
        resetViewBound();
        this.setAnchorPoint(0, 0);
//        this.setScaleType(ScaleType.FIT_CENTER);
        this.setScaleType(ScaleType.MATRIX);
        parent.addChild(this);
    }

    protected void resetViewBound(){
//        Log.d("Sprite", getName() + " reset View Bound" + realContentSize);
        if(realContentSize == null) return;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams((int)realContentSize.width, (int)realContentSize.height);
            this.setLayoutParams(lp);
        }
        else{
            lp.width = (int)realContentSize.width;
            lp.height = (int)realContentSize.height;
            requestLayout();
        }
//        Log.d("resetViewBound", getName() + " lp: " + new Point(lp.width, lp.height) + " " + realContentSize.toString());
//        invalidate();

        Point translateVector = new Point(anchorX * dSize.width * oldRecurScale, anchorY * dSize.height * oldRecurScale);
        float recurScale = getRecursiveScale();

        translateVector = translateVector.product(1-recurScale/oldRecurScale);
//        if(getName().equals("Debug"))
//            Log.d("bug" + getName(), "dSize: " + dSize + " translate vector" + translateVector + " recur scale: " + recurScale + " old: " + oldRecurScale);
        move(translateVector);

        oldRecurScale = recurScale;
        this.resetContainerBound();
    }

    protected void resetContainerBound(){
////        Log.d("Sprite", getName() + " reset resetContainerBound Bound" + realContentSize);
//        if(realContentSize == null) return;
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
//        if(lp == null){
//            lp = new RelativeLayout.LayoutParams((int)realContentSize.width, (int)realContentSize.height);
//            this.container.setLayoutParams(lp);
//        }
//        else{
//            lp.width = (int)realContentSize.width;
//            lp.height = (int)realContentSize.height;
//        }
////        Log.d("resetContainerBound", getName() + " " + new Size(lp.width, lp.height).toString() + " " + realContentSize);
//
//        this.container.requestLayout();
    }

    private void initListener() {
        listenerCallbacks = new HashMap<>();
        listenerCallbacks.put(CallbackType.ON_TOUCH_DOWN, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_UP, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_MOVE, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_CANCEL, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_CLICK, new ArrayList<Runnable>());
    }

    public void setSpriteAnimation(int resId){
        this.animSprites = new Bitmap[1];
        animSprites[0] = BitmapFactory.decodeResource(getContext().getResources(), resId);
        if(animSprites[0] == null) throw new NullPointerException("Resource id: " + resId + " cannot be found.");

        animFrameRate = 0;
        isRepeating = false;
        setImageBitmap(animSprites[0]);
        dSize = new Size(animSprites[0].getWidth(), animSprites[0].getHeight());
        this.setContentSize(animSprites[0].getWidth(), animSprites[0].getHeight());
//        contentSize = new Size(animSprites[0].getWidth(), animSprites[0].getHeight());
//        this.container.setLayoutParams(new RelativeLayout.LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight()));
        animIdx = 0;
        isPlaying = false;
    }

    public void setSpriteAnimation(int[] resIds){
        this.setSpriteAnimation(resIds, 60, true);
    }
    public void setSpriteAnimation(int[] resIds, int frameRate){
        this.setSpriteAnimation(resIds, frameRate, true);
    }

    public void setSpriteAnimation(int[] resIds, int frameRate, boolean repeat){
        this.animSprites = new Bitmap[resIds.length];
        for(int i = 0; i < resIds.length; i++){
            animSprites[i] = BitmapFactory.decodeResource(getContext().getResources(), resIds[i]);
            if(animSprites[i] == null) throw new NullPointerException("Resource id: " + resIds[i] + " cannot be found.");
        }
        animFrameRate = frameRate;
        isRepeating = repeat;
        setImageBitmap(animSprites[0]);
        dSize = new Size(animSprites[0].getWidth(), animSprites[0].getHeight());
        this.setContentSize(animSprites[0].getWidth(), animSprites[0].getHeight());
//        contentSize = new Size(animSprites[0].getWidth(), animSprites[0].getHeight());
//        this.container.setLayoutParams(new RelativeLayout.LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight()));
        animIdx = 0;
        isPlaying = animSprites.length > 1;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(isPlaying){
            playTime += dt;
            if(animIdx + 1 == animSprites.length && !isRepeating) {
                isPlaying = false;
                return;
            }
//            int curIdx = (animIdx + 1)%animSprites.length;
            int curIdx = ((int)Math.floor(playTime*animFrameRate))%animSprites.length;
            if(curIdx != animIdx) {
                setImageBitmap(animSprites[curIdx]);
                dSize.width = animSprites[curIdx].getWidth();
                dSize.height = animSprites[curIdx].getHeight();
            }
            animIdx = curIdx;
        }
        if(actions.size() > 0){
            for(Action a : actions){
                a.updateEveryFrame(dt, this);
            }
        }
        if(toBeRemovedActions.size() > 0){
            for(Action a : toBeRemovedActions){
                actions.remove(a);
            }
            toBeRemovedActions.clear();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

//        Log.d("ONTOUCH", this.getName() + " get the on touch event: " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.d(LOGTAG, "TouchDown");
                onTouchBegan(event);
                isTouched = true;
                return swallowTouches;
            case MotionEvent.ACTION_UP:
//                Log.d(LOGTAG, "TouchRelease");
                performClick();
                onTouchSucceed(event);
                return swallowTouches;
            case MotionEvent.ACTION_MOVE:
//                Log.d(LOGTAG, "TouchMove");
                onTouchMoved(event);
                return swallowTouches;
            case MotionEvent.ACTION_CANCEL:
//                Log.d(LOGTAG, "TouchCancel");
                onTouchCanceled(event);
                return swallowTouches;
            default:
                Log.d(LOGTAG, "Action out of my cases :(" + event.getAction());
        }
        return false;
    }

    protected void onTouchCanceled(MotionEvent event) {
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_TOUCH_CANCEL)){
            r.run();
        }

        int[] viewOrder = container.getDrawOrderIndexing(this);
        for(int i=viewOrder.length - 1;i>=0;i--){
            GameView child = children.get(viewOrder[i]);
            if(child.getViewType() == SPRITE){
                Sprite sprite = (Sprite)child;

                // Only trigger child on touch cancel if it were touched before
                if(!sprite.isTouched)
                    continue;

                sprite.onTouchCanceled(event);
                if(sprite.swallowTouches)
                    break;
            }
        }
    }

    protected void onTouchMoved(MotionEvent event) {
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_TOUCH_MOVE)){
            r.run();
        }
        if(debugMode && isTouched){
            // Move the sprite
            if(event.getPointerCount() == 1){
                Point cur = new Point(event.getRawX(), event.getRawY());
                Point distance = cur.subtract(prevTouch);
                move(distance);
                prevTouch = cur;
            }
            // Scale the sprite
            else{
                Point cur1 = new Point(event.getX(0), event.getY(0));
                Point cur2 = new Point(event.getX(1), event.getY(1));
                float curDistance = cur1.distanceTo(cur2);
                if(prevTouchDistance >= 10){
                    setScale(curDistance/prevTouchDistance*trueScale);
                }
                prevTouchDistance = curDistance;
            }
        }
    }

    protected void onTouchSucceed(MotionEvent event) {
        isTouched = false;
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_TOUCH_UP)){
            r.run();
        }

        if(debugMode){
            Log.d("DEBUG", "object: " + getName() + " pos at " + getPosition() + " scale: " + trueScale);
        }

        int[] viewOrder = container.getDrawOrderIndexing(this);
        for(int i=viewOrder.length - 1;i>=0;i--){
            GameView child = children.get(viewOrder[i]);
            if(child.getViewType() == SPRITE){
                Sprite sprite = (Sprite)child;

                // Only trigger child on touch succeed if it were touched before
                if(!sprite.isTouched)
                    continue;

                sprite.onTouchSucceed(event);
                if(sprite.swallowTouches)
                    break;
            }
        }
    }

    protected void onTouchBegan(MotionEvent event) {
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_TOUCH_DOWN)){
            r.run();
        }

        if(debugMode){
            prevTouch = new Point(event.getRawX(), event.getRawY());
        }
    }

    public void addTouchEventListener(CallbackType type, Runnable listener){
        if(listenerCallbacks.containsKey(type)){
            listenerCallbacks.get(type).add(listener);
        }
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_CLICK)){
            r.run();
        }
        super.performClick();
        return swallowTouches;
    }

    // TODO: SCALE container along with view :D
    public void setScale(float scale){
        oldRecurScale = getRecursiveScale();
        this.trueScale = scale;
        float recurScale = getRecursiveScale();
        realContentSize = contentSize.multiply(recurScale);
        this.resetViewBound();
        this.setAnchorPoint(anchorX, anchorY);
        invalidate();
    }


    protected void updateRecurScale(){
        // Update parent scale
        float recurScale = getRecursiveScale();
        if(dSize.width != 0 && dSize.height != 0){
            Matrix matrix = new Matrix();
            matrix.setScale(recurScale, recurScale);
//        this.setScaleX(recurScale);
//        this.setScaleY(recurScale);
            this.setImageMatrix(matrix);
        }

        realContentSize = contentSize.multiply(recurScale);
        this.resetViewBound();
        this.setAnchorPoint(anchorX, anchorY);

        for(GameView child : children){
            if(child.getViewType() == SPRITE){
                ((Sprite)child).updateRecurScale();
            }
        }
    }

    @Override
    public void invalidate() {
        updateRecurScale();
        super.invalidate();
    }

    public float getRecursiveScale(){
        if(parent != null && parent.getViewType() == SPRITE){
            return ((Sprite)parent).getRecursiveScale() * trueScale;
        }
        return trueScale;
    }

    public float getScale(){
        return trueScale;
    }

    public void removeAction(Action a){
        toBeRemovedActions.add(a);
    }

    public void runAction(Action a){
        actions.add(a);
        a.start();
    }

    @Override
    public void setPosition(float x, float y) {
//        setPivotX(0f);
//        setPivotY(0f);
        super.setPosition(x, y);
    }

    public void setAnchorPoint(float x, float y){
        float recurScale = getRecursiveScale();
        anchorX = x;
        anchorY = y;
        if(realContentSize != null){
            setPivotX(anchorX * realContentSize.width);
            setPivotY(anchorY * realContentSize.height);
        }
    }

    public void setContentSize(Size size){
        setContentSize(size.width, size.height);
    }

    public void setContentSize(float width, float height){
        if(contentSize == null) contentSize = new Size();
        contentSize.width = width;
        contentSize.height = height;
        setAnchorPoint(anchorX, anchorY);
        invalidate();
    }

    public Point getAnchorPoint(){
        return new Point(anchorX, anchorY);
    }

    protected Bitmap[] getAnimSprites(){
        return animSprites;
    }

    public void setSwallowTouches(boolean value){
        swallowTouches = value;
    }

    public void setLayoutAnchor(ImageView.ScaleType type){
        setScaleType(type);
    }

    public void setLayoutRule(LayoutPosition rule){
        layoutRule = rule;
        updateLayoutRule();
    }

    public void updateLayoutRule(){
        final LayoutPosition.LayoutRule[] rules = layoutRule.getRules();
        parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float x = getPosition().x, y = getPosition().y;
                for(LayoutPosition.LayoutRule rule : rules){
                    if(rule == null) continue;
                    // TODO: get parent size
                    float width, height;
                    if(parent.viewType == SPRITE){
                        width = ((Sprite)parent).realContentSize.width;
                        height = ((Sprite)parent).realContentSize.height;
                    }
                    else{
                        width = Utils.getScreenWidth();
                        height = Utils.getScreenHeight();
                    }
                    Size pSize = new Size(width, height);
                    Log.d("updateLayoutRule", "size: " + pSize);
                    switch(rule.rule){
                        case TOP: y = rule.val; break;
                        case BOTTOM: y = pSize.height - rule.val; break;
                        case RIGHT: x = pSize.width - rule.val; break;
                        case LEFT: x = rule.val; break;
                    }
                }
                setPosition(x, y);
            }
        });

    }

    public void setDebugMode(boolean value){
        debugMode = value;
    }

    public void debug(){
////        Log.d("Button", "scale " + getRecursiveScale() + "contentSize " + contentSize + "real " + realContentSize);
////        Log.d("Layout", lp.leftMargin + " " + lp.topMargin + " " + lp.rightMargin + " " + lp.bottomMargin);
//        Log.d("Sprite", "debug()");
//        int n = this.container.getChildCount();
//        for(int i=0;i<n;i++){
//            View v = this.container.getChildAt(i);
//            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
//            Log.d("Debug()", "View class: " + v.getClass().toString() + " " + lp.width + ", " + lp.height);
//        }
    }
}
