package com.mus.kidpartner.modules.views.base;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.Director;

public class ScrollView extends Sprite implements SensorEventListener {
    private static final String LOGTAG = "ScrollView";

    public enum ScrollType{
        NONE, BOTH, VERTICAL, HORIZONTAL, SENSOR
    }

    private Point prevTouch;
    private ScrollType scrollType;
    private ScrollType oldScrollType;
    private boolean isTouching = false;
    private boolean floating = false;
    private Point floatVelocity = new Point(0,0);
    private long firstTouchTimestamp;
    private long lastMoveTimestamp;
    private Point floatDrag = new Point(0, 0);
    private Size viewSize;

    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    private float[] sensorValues;
    private float sensorSensitivity = 0.3f;
    private boolean canSensorEnable = true;

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

        sensorManager = (SensorManager) Director.getInstance().getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        if(accelerometer == null || magneticField == null){
            canSensorEnable = false;
        }
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
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onTouchBegan(MotionEvent event) {
        super.onTouchBegan(event);
        isTouching = true;
        firstTouchTimestamp = System.currentTimeMillis();
        floating = false;
        prevTouch = new Point(event.getRawX(), event.getRawY());
//        Log.d(LOGTAG, "Touch began");
    }

    @Override
    protected void onTouchMoved(MotionEvent event) {
        super.onTouchMoved(event);
        if(isTouching){
            Point cur = new Point(event.getRawX(), event.getRawY());
            Point distance = cur.subtract(prevTouch);
//            Log.d(LOGTAG, "distance: " + distance + " " + contentPosition);
            if(scrollType == ScrollType.HORIZONTAL || scrollType == ScrollType.NONE || scrollType == ScrollType.SENSOR){
                distance.y = 0;
            }
            if(scrollType == ScrollType.VERTICAL || scrollType == ScrollType.NONE || scrollType == ScrollType.SENSOR){
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
//            contentPosition = contentPosition.add(distance);

            moveAllChild(distance);
//            Log.d("SCR", "contentPos: " + contentPosition);

            prevTouch = cur;
        }
    }

    //TODO: fix bug here
    private void moveAllChild(Point distance) {
//        Log.d(LOGTAG, "content Position " + contentPosition + "limits: " + maxDx + " " + minDx + " " + maxDy + " " + minDy);
        if(contentPosition.x + distance.x > maxDx){
            distance.x = maxDx - contentPosition.x;
        }
        else if(contentPosition.x + distance.x < minDx){
            distance.x = minDx - contentPosition.x;
        }
        if(contentPosition.y + distance.y > maxDy){
            distance.y = maxDy - contentPosition.y;
        }
        else if(contentPosition.y + distance.y < minDy){
            distance.y = minDy - contentPosition.y;
        }

        contentPosition = contentPosition.add(distance);
//        Log.d(LOGTAG, "distance: " + distance);

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
        if(type == ScrollType.SENSOR && !canSensorEnable){
            scrollType = ScrollType.BOTH;
        }
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

    public void moveToCenter(){
        moveToCenterX();
        moveToCenterY();
    }

    public void moveToCenterX(){
        this.moveAllChild(new Point(maxDx/2 - contentPosition.x, 0));
    }

    public void moveToCenterY(){
        this.moveAllChild(new Point(0, maxDy/2 - contentPosition.y));
    }

    public void moveToEndX(){
        this.moveAllChild(new Point(maxDx - contentPosition.x, 0));
    }

    public void moveToEndY(){
        this.moveAllChild(new Point(0, maxDy - contentPosition.y));
    }

    public void moveToStartX(){
        this.moveAllChild(new Point(0 - contentPosition.x, 0));
    }

    public void moveToStartY(){
        this.moveAllChild(new Point(0, 0 - contentPosition.y));
    }

    public void moveToEnd(){
        moveToEndX();
        moveToEndY();
    }

    public void setSensorSensitivity(float s){
        sensorSensitivity = s;
    }

    public float getSensorSensitivity(){
        return sensorSensitivity;
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
//                    contentPosition = contentPosition.add(distance);
//                    Log.d("SCR", "contentPos: " + contentPosition);
                }
                else{
                    floating = false;
                    floatDrag.x = 0; floatDrag.y = 0;
                }
            }
        }

        if(scrollType == ScrollType.SENSOR){
            // Test gyro
            // Update rotation matrix, which is needed to update orientation angles.
            SensorManager.getRotationMatrix(rotationMatrix, null,
                    accelerometerReading, magnetometerReading);

            // "mRotationMatrix" now has up-to-date information.

            SensorManager.getOrientation(rotationMatrix, orientationAngles);

            // Only work if hold the phone with angle PI/2 +- delta

            double delta = 10*Math.PI/180f;
            final float filter = 0.1f;
            if(sensorValues != null){
//            if(Math.abs(orientationAngles[2]) > Math.PI/2 - delta
//                    && Math.abs(orientationAngles[2]) < Math.PI/2 + delta){
                Point distance = new Point(0,0);
                if(Math.abs(sensorValues[1] - orientationAngles[1]) > 0.015){
                    distance.x = (maxDx / (1/sensorSensitivity))*(sensorValues[1] - orientationAngles[1]);
                }
                if(Math.abs(sensorValues[2] - orientationAngles[2]) > 0.015){
                    distance.y = (maxDy / (1/sensorSensitivity))*(sensorValues[2] - orientationAngles[2]);
                }
                sensorValues[1] = orientationAngles[1]*filter + sensorValues[1]*(1-filter);
                sensorValues[2] = orientationAngles[2]*filter + sensorValues[2]*(1-filter);
//                Log.d(LOGTAG, "debug sensor: " + Utils.arrayToString(orientationAngles));
                moveAllChild(distance);
//            }
            }else{
                sensorValues = new float[3];
            }
            sensorValues[0] = orientationAngles[0]*filter + sensorValues[0]*(1-filter);

            // "mOrientationAngles" now has up-to-date information.
        }
    }


    private void pause(){
        oldScrollType = scrollType;
        this.setScrollType(ScrollType.NONE);
    }

    private void resume(){
        this.setScrollType(oldScrollType);
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
