package com.mus.myapplication.modules.views.base;

import android.util.Log;
import android.view.MotionEvent;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;

public class Button extends Sprite {
    private static String LOGTAG = "Button";
    private boolean bOnClickEffect = true;
    private boolean bOnClickSound = true;
    private float scaleBeforeEffect = 1;
    private ScaleTo scaleUpAction = null;
    private ScaleTo scaleDownAction = null;
    private Point anchorPointBefore;
    private boolean touchAnim = false;
    private GameTextView label;


    public Button(GameView parent) {
        super(parent);
        setSpriteAnimation(R.drawable.button);
        init();
        initLabel("");
    }
    public Button(){
        super();
        setSpriteAnimation(R.drawable.button);
        init();
        initLabel("");
    }

    public Button(String label){
        super();
        setSpriteAnimation(R.drawable.button);
        init();
        initLabel(label);
    }

    private void init(){
        setAnchorPoint(0.5f, 0.5f);
    }

    private void initLabel(String s){
//        label = new GameTextView(s);
//        this.addChild(label);
//        label.setSwallowTouches(false);
//        label.setFontSize(24);
    }

    @Override
    protected void onTouchBegan(MotionEvent event) {
        super.onTouchBegan(event);
        onClickEffect();
        invalidate();
    }

    @Override
    protected void onTouchSucceed(MotionEvent event) {
        super.onTouchSucceed(event);
        onClickEndedEffect();
        onClickSound();
        invalidate();
    }

    protected void onClickEffect(){
        if(!bOnClickEffect)
            return;
        // Scale + glow on Click
        if(scaleDownAction != null) {
            scaleDownAction.addOnFinishedCallback(new Runnable() {
                @Override
                public void run() {
                    touchAnim = true;
                    scaleDownAction.removeOnFinishedCallback("resetAnim");
                }
            }, "resetAnim");
            scaleDownAction.forceFinish(this);
        }

        anchorPointBefore = getAnchorPoint();

        setAnchorPoint(0.5f, 0.5f);

        if(scaleUpAction == null){
            scaleUpAction = new ScaleTo(0.2f, trueScale * 0.8f);
        }
        else{
            scaleUpAction.reset();
            scaleUpAction.setLastScale(trueScale * 0.8f);
        }

        touchAnim = true;
        runAction(scaleUpAction);
    }

    protected void onClickEndedEffect(){
        if(!bOnClickEffect)
            return;

        if(scaleUpAction != null){
            scaleUpAction.forceFinish(this);
        }

//        final Point anchor = getAnchorPoint();
//        this.setAnchorPoint(0.5f, 0.5f);
        // Scale + glow on Click
        if(scaleDownAction == null){
            scaleDownAction = new ScaleTo(0.2f, scaleBeforeEffect);
        }
        else{
            scaleDownAction.reset();
            scaleDownAction.removeAllCallbacks();
            scaleDownAction.setLastScale(scaleBeforeEffect);
        }
        scaleDownAction.addOnFinishedCallback(new Runnable() {
            @Override
            public void run() {
                setAnchorPoint(anchorPointBefore.x, anchorPointBefore.y);
                touchAnim = false;
            }
        });
        this.runAction(scaleDownAction);
    }

    protected void onClickSound(){
        if(!bOnClickSound)
            return;
        // Play sound
    }

    public void setEnableClickEffect(boolean val){
        bOnClickEffect = val;
    }

    public void setEnableClickSound(boolean val){
        bOnClickSound = val;
    }

    public void setLabel(String s){
        label.setText(s);
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        if(!touchAnim){
            scaleBeforeEffect = scale;
        }
    }

    public void setLabelFontSize(int size){
        label.setFontSize(size);
    }

    public void setLabelFont(String fontName){
        label.setFont(fontName);
    }

    public void setLabelPosition(Point p){
        label.setPosition(p);
    }

    public void setLabelPosition(float x, float y){
        label.setPosition(x, y);
    }

    public void setText(String text){
        setLabel(text);
    }

}
