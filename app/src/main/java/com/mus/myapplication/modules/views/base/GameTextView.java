package com.mus.myapplication.modules.views.base;


import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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
    CharSequence text;
    float fontSize;
    View[] subViews;


    public GameTextView(GameView parent){
        super();
        this.text = "";
        parent.addChild(this);
//        initTextView();
//        setFontColor(Color.BLACK);
    }

    public GameTextView(CharSequence text){
        super();
        this.text = text;
    }

    public GameTextView(){
        super();
        this.text = "";
    }

    @Override
    public int getSubViewsCount() {
        return 1;
    }

    @Override
    public View[] getSubViews() {
        return subViews;
    }

    @Override
    protected void afterAddChild() {
        initTextView();
        super.afterAddChild();
        setFontColor(Color.BLACK);
    }

    private void initTextView(){
        view = new TextView(this.getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setText(text);
        subViews = new View[]{view};
        fontSize = view.getTextSize();
        setFontSize(14);

        updateBound();
//        this.container.addView(view);
    }

    public void setFontColor(@ColorInt int color){
        view.setTextColor(color);
    }

    private void updateMeasure(){
        view.measure(0, 0);
        contentSize = new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
        realContentSize = contentSize;
    }

    public void setText(CharSequence text, String fontName, float size){
        setText(text);
        setFont(fontName);
        setFontSize(size);
    }

    public void setText(CharSequence text, String fontName){
        setText(text);
        setFont(fontName);
    }

    public void setFont(String fontName){
        view.setTypeface(FontCache.get(fontName, this.getContext()));
        updateMeasure();
    }

    public void setFontSize(float size){
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        fontSize = size;
        updateMeasure();
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
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize * recurScale);
        }
        super.updateRecurScale();

//        if(view != null){
//
//            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ((RelativeLayout)this.view.getParent()).getLayoutParams();
////            Log.d("GameTextView", "Debug recur scale " + lp.width + " " + lp.height);
//        }
    }

    private void updateBound() {
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.container.getLayoutParams();
//        if(lp == null){
//            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }
//
//        int width = Utils.getScreenWidth();
//        int height = Utils.getScreenHeight();
//
////        realContentSize.width = lp.width = width;
////        realContentSize.height = lp.height = height;
//        realContentSize.width = width;
//        realContentSize.height = height;
//
//        float recurScale = getRecursiveScale();
//        contentSize = realContentSize.multiply(1/recurScale);
//
//        this.container.setLayoutParams(lp);
    }

    public void setText(CharSequence text){
        this.text = text;
        view.setText(text);
        updateMeasure();
    }

    public Size getContentSize() {
        return super.getContentSize(true);
    }
}
