package com.mus.myapplication.modules.views.base;

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
    private GameTextView label;


    public Button(GameView parent) {
        super(parent);
        setSpriteAnimation(new int[]{R.drawable.button});
        initLabel("");
    }
    public Button(){
        super();
        setSpriteAnimation(new int[]{R.drawable.button});
        initLabel("");
    }

    public Button(String label){
        super();
        setSpriteAnimation(new int[]{R.drawable.button});
        initLabel(label);
    }

    private void initLabel(String s){
        label = new GameTextView(s);
        label.setFontSize(12);
        this.addChild(label);
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

        if(scaleDownAction != null){
            scaleDownAction.forceFinish(this);
        }

        scaleBeforeEffect = trueScale;

        anchorPointBefore = getAnchorPoint();
        this.setAnchorPoint(0.5f, 0.5f);

        if(scaleUpAction == null){
            scaleUpAction = new ScaleTo(0.2f, trueScale * 0.8f);
        }
        else{
            scaleUpAction.reset();
            scaleUpAction.setLastScale(trueScale * 0.8f);
        }
//        scaleUpAction.addOnFinishedCallback(new Runnable() {
//            @Override
//            public void run() {
//                setAnchorPoint(anchor.x, anchor.y);
//            }
//        });

        this.runAction(scaleUpAction);
//        setScaleX(trueScale * 0.8f);
//        setScaleY(trueScale * 0.8f);
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
            scaleDownAction.setLastScale(scaleBeforeEffect);
        }
        scaleDownAction.addOnFinishedCallback(new Runnable() {
            @Override
            public void run() {
                setAnchorPoint(anchorPointBefore.x, anchorPointBefore.y);
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

    public void setLabelFontSize(int size){
        label.setFontSize(size);
    }
}
