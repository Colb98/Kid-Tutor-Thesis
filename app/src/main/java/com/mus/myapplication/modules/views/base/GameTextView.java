package com.mus.myapplication.modules.views.base;


import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;

import androidx.annotation.ColorInt;

public class GameTextView extends Sprite {
    TextView view;
    String text;
    float fontSize;


    public GameTextView(GameView parent){
        super();
        this.text = "";
        this.container = new TextViewContainer(this.getContext());
        parent.addChild(this);
//        initTextView();
//        setFontColor(Color.BLACK);
    }

    public GameTextView(String text){
        super();
        this.container = new TextViewContainer(this.getContext());
        this.text = text;
    }

    public GameTextView(){
        super();
        this.container = new TextViewContainer(this.getContext());
        this.text = "";
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initTextView();
        setFontColor(Color.BLACK);
    }

    private void initTextView(){
        view = new TextView(this.getContext());
        view.setText(text);
        fontSize = view.getTextSize();

        updateBound();
        this.container.addView(view);
    }

    public void setFontColor(@ColorInt int color){
        view.setTextColor(color);
    }

    public void setFont(){
        view.setTypeface(FontCache.get("fonts/Radens.ttf", this.getContext()));
    }

    public void setFontSize(float size){
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        fontSize = size;
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        float recurScale = getRecursiveScale();
//        Log.d("TEXTVIEW", "text view set scale " + scale + " " + fontSize);
        view.setTextSize(fontSize * recurScale);
    }

    @Override
    protected void updateRecurScale() {
        if(view != null){
            float recurScale = getRecursiveScale();
            updateBound();
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize * recurScale);
        }
        super.updateRecurScale();

//        if(view != null){
//
//            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ((RelativeLayout)this.view.getParent()).getLayoutParams();
////            Log.d("GameTextView", "Debug recur scale " + lp.width + " " + lp.height);
//        }
    }

    private void updateBound() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
        if(lp == null){
            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        int width, height;
        if(parent.getViewType() == SPRITE){
            Sprite p = (Sprite) parent;
            width = (int) p.realContentSize.width;
            height = (int) p.realContentSize.height;
        }
        else{
            width = Utils.getScreenWidth();
            height = Utils.getScreenHeight();
        }
        realContentSize.width = lp.width = width;
        realContentSize.height = lp.height = height;

        float recurScale = getRecursiveScale();
        contentSize = realContentSize.multiply(1/recurScale);

        this.container.setLayoutParams(lp);
    }

    public void setText(String text){
        this.text = text;
        view.setText(text);
    }
}
