package com.mus.myapplication.modules.views.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;

public class GameView extends AppCompatImageView {
    private static final String LOGTAG = "GameView";
    public static final int VIEW = 0;
    public static final int SCENE = 1;
    public static final int SPRITE = 2;


    List<GameView> children = new ArrayList<>();
    List<Integer> childrenOrder = new ArrayList<>();
    GameView parent = null;
    boolean isUpdating = false;
    protected int zOrder;
    ViewContainer container = null;
    private String name = "Default Name";
    protected int viewType = GameView.VIEW;
    GameScene curScene = null;

    protected int animFrameRate = 60;
    long lastUpdate = 0;

    private Point position = new Point(0, 0);

    // Default Constructors
    public GameView(Context context) {
        super(context);
        initUpdate();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUpdate();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUpdate();
    }

    public void setViewGroup(ViewContainer view){
        this.container = view;
    }

    public GameView(){
        super(Director.getInstance().getContext());
        Context context = Director.getInstance().getContext();


        this.zOrder = 0;
        this.container = new ViewContainer(context);

        this.container.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ));
        initUpdate();
    }

    // Manual in-code constructor
    public GameView(GameView parent){
        super(parent.getContext());
        this.parent = parent;
        this.zOrder = 0;
        this.container = new ViewContainer(parent.getContext());
        this.container.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ));
        initUpdate();
        parent.addChild(this);
    }

    public GameView(GameView parent, int zOrder){
        super(parent.getContext());
        this.parent = parent;

        this.zOrder = zOrder;
        this.container = new ViewContainer(parent.getContext());
        this.container.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        ));
        initUpdate();
        parent.addChild(this);
    }

    public void addChild(GameView child){
        addChild(child, 0);
    }

    public void addChild(GameView child, int zOrder){
        if(this.container != null){
            children.add(child);
            childrenOrder.add(zOrder);
            container.addGameView(child, child.container, childrenOrder);
        }
        if(child.parent == null){
            child.parent = this;
            child.afterAddChild();
        }
        if(child.viewType == SCENE){
            if(this.curScene != null){
                child.setVisibility(INVISIBLE);
            }
            else{
                this.curScene = (GameScene) child;
            }
        }
    }

    protected void afterAddChild(){
        // Override this function
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    private void initUpdate(){
        isUpdating = true;
        lastUpdate = System.currentTimeMillis();
    }

    public Point getPosition(){
        return position.clone();
    }

    public void setPositionDp(Point p) {
        setPositionDp((int)p.x, (int)p.y);
    }

    public void setPositionDp(int x, int y){
        int dpX = (int)Utils.getPxByDp(x);
        int dpY = (int)Utils.getPxByDp(y);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp.setMargins(dpX, dpY, -dpX, -dpY);
        position.x = dpX;
        position.y = dpY;
        this.setLayoutParams(lp);
        this.container.setLayoutParams(lp);
    }

    public void applyLayoutParam(RelativeLayout.LayoutParams lp){
        this.setLayoutParams(lp);
        this.container.setLayoutParams(lp);
    }

    public void setPosition(Point p){
        setPosition(p.x, p.y);
    }

    public void setPosition(float x, float y){
//        int dpX = (int)Utils.getDpByPx(x);
//        int dpY = (int)Utils.getDpByPx(y);
//        Log.d(LOGTAG, "dpx, dpy: " + dpX + " "  + dpY + "x, y: " + x + " "  + y);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp.setMargins((int)x, (int)y, 0, 0);
        position.x = x;
        position.y = y;
        this.setLayoutParams(lp);

        lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
        lp.setMargins((int)x, (int)y, 0, 0);
        this.container.setLayoutParams(lp);
    }

    public void moveDp(Point d){
        setPosition(position.add(new Point(Utils.getPxByDp(d.x), Utils.getPxByDp(d.y))));
    }

    public void moveDp(int dx, int dy){
        setPosition(position.add(new Point(Utils.getPxByDp(dx), Utils.getPxByDp(dy))));
    }

    public void move(Point d){
        setPosition(position.add(d));
    }

    public void move(int dx, int dy){
        setPosition(position.add(new Point(dx, dy)));
    }

    public void update(float dt){
        if(!isUpdating){
//            Log.d("Not updating", "");
            return;
        }

        long cur = System.currentTimeMillis();
//        Log.d(LOGTAG, "DEBUG:" + (cur - lastUpdate));
        lastUpdate = cur;
        for(GameView child : children){
            child.update(dt);
        }
//        invalidate();
//         Update function below
//        Log.d("Updating", "FPS: " + 1000/dt);
    }

    public void setCurrentScene(GameScene scene){
        if(scene.parent != this){
            if(scene.parent != null){
                scene.parent.removeChild(scene);
            }
            this.addChild(scene);
            scene.parent = this;
        }
        else{
            scene.setVisibility(VISIBLE);
        }
    }

    public void releaseCurrentScene() {
        if(this.curScene != null){
            this.removeChild(this.curScene);
            this.curScene = null;
        }
    }

    // remove a child view and there's chance to be added back
    public void removeChild(GameView view){
        Log.d(LOGTAG, "remove child: " + view.getName());
        container.removeView(view.container);
        container.removeView(view);
    }

    // free all child belonging
    public void removeChildRecursive(GameView view){
        // recursively remove children
        view.removeAllChild(true);
        container.removeView(view.container);
        container.removeView(view);
    }

    public void removeAllChild(boolean recursive){
        for(GameView v : children){
            if(recursive)
                removeChildRecursive(v);
            else{
                removeChild(v);
            }
        }
    }

    public GameView getGameParent(){
        return parent;
    }

    public List<GameView> getChildren() {
        return children;
    }

    public int getViewType(){
        return viewType;
    }

    public void setZOrder(int zOrder){
        this.zOrder = zOrder;
        parent.updateChildZOrder(this);
    }

    private void updateChildZOrder(GameView child){
        childrenOrder.set(children.indexOf(child), child.zOrder);
        Log.d(LOGTAG, Utils.arrayToString(childrenOrder));
        container.updateViewOrder(childrenOrder);
    }
}
