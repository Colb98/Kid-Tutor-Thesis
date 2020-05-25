package com.mus.myapplication.modules.views.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Size;
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

    private Size contentSize;
    private Size realContentSize;
    private int animIdx = 0;
    private boolean isPlaying = false;
    private boolean isRepeating = true;
    private Bitmap[] animSprites = null;
    protected float trueScale = 1f;
    protected float playTime = 0f;
    private float anchorX = 0.5f;
    private float anchorY = 0.5f;

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
        this.setScaleType(ScaleType.FIT_CENTER);
    }

    public Sprite(GameView parent) {
        super(parent);
        viewType = SPRITE;
        initListener();

        actions = new ArrayList<>();
        toBeRemovedActions = new ArrayList<>();
        resetViewBound();
        this.setAnchorPoint(0, 0);
        this.setScaleType(ScaleType.FIT_CENTER);
    }

    private void resetViewBound(){
        Log.d("Sprite", getName() + " reset View Bound");
        if(realContentSize == null) return;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams((int)realContentSize.width, (int)realContentSize.height);
        }
        else{
            lp.width = (int)realContentSize.width;
            lp.height = (int)realContentSize.height;
        }
        Log.d("resetViewBound", realContentSize.toString());
        this.setLayoutParams(lp);

        this.resetContainerBound();
    }

    private void resetContainerBound(){
        if(realContentSize == null) return;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams((int)realContentSize.width, (int)realContentSize.height);
        }
        else{
            lp.width = (int)realContentSize.width;
            lp.height = (int)realContentSize.height;
        }
        Log.d("resetContainerBound", new Size(lp.width, lp.height).toString() + " " + contentSize);
        this.container.setLayoutParams(lp);
    }

    private void initListener() {
        listenerCallbacks = new HashMap<>();
        listenerCallbacks.put(CallbackType.ON_TOUCH_DOWN, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_UP, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_MOVE, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_TOUCH_CANCEL, new ArrayList<Runnable>());
        listenerCallbacks.put(CallbackType.ON_CLICK, new ArrayList<Runnable>());
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
        this.setContentSize(animSprites[0].getWidth(), animSprites[0].getHeight());
//        contentSize = new Size(animSprites[0].getWidth(), animSprites[0].getHeight());
        this.container.setLayoutParams(new RelativeLayout.LayoutParams(this.getMeasuredWidth(), this.getMeasuredHeight()));
        animIdx = 0;
        isPlaying = true;
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

        int[] viewOrder = container.getDrawOrderIndexing();
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
    }

    protected void onTouchSucceed(MotionEvent event) {
        isTouched = false;
        for(Runnable r : listenerCallbacks.get(CallbackType.ON_TOUCH_UP)){
            r.run();
        }

        int[] viewOrder = container.getDrawOrderIndexing();
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
        this.trueScale = scale;
        float recurScale = getRecursiveScale();
//        this.setScaleX(recurScale);
//        this.setScaleY(recurScale);
        for(GameView child : children){
            if(child.getViewType() == SPRITE){
                child.invalidate();
            }
        }
        realContentSize = contentSize.multiply(recurScale);
        this.resetViewBound();
        this.setAnchorPoint(anchorX, anchorY);
    }

    protected void updateRecurScale(){
        // Update parent scale
        float recurScale = getRecursiveScale();
        this.setScaleX(recurScale);
        this.setScaleY(recurScale);

        for(GameView child : children){
            if(child.getViewType() == SPRITE){
                child.invalidate();
            }
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        updateRecurScale();
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
    public void setPosition(int x, int y) {
//        setPivotX(0f);
//        setPivotY(0f);
        super.setPosition(x, y);
    }

    public void setAnchorPoint(float x, float y){
        float recurScale = getRecursiveScale();
        anchorX = x;
        anchorY = y;
        if(contentSize != null){
            setPivotX(anchorX * contentSize.width);
            setPivotY(anchorY * contentSize.height);
        }
    }

    public void setContentSize(float width, float height){
        if(contentSize == null) contentSize = new Size();
        contentSize.width = width;
        contentSize.height = height;
        setAnchorPoint(anchorX, anchorY);
    }

    public Point getAnchorPoint(){
        return new Point(anchorX, anchorY);
    }

    public void setSwallowTouches(boolean value){
        swallowTouches = value;
    }

    public void setLayoutAnchor(ImageView.ScaleType type){
        setScaleType(type);
    }
}
