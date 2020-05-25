package com.mus.myapplication.modules.views.base;


import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mus.myapplication.modules.classes.FontCache;

import androidx.annotation.ColorInt;

public class GameTextView extends Sprite {
    TextView view;
    String text;
    float fontSize;


    public GameTextView(GameView parent){
        super();
        this.text = "";
        initTextView();
        setFontColor(Color.BLACK);
    }

    public GameTextView(String text){
        super();
        this.text = text;
        initTextView();
        setFontColor(Color.BLACK);
    }

    public GameTextView(){
        super();
        this.text = "";
        initTextView();
        setFontColor(Color.BLACK);
    }

    private void initTextView(){
        view = new TextView(this.getContext());
        view.setText(text);
        fontSize = view.getTextSize();
        this.container = new TextViewContainer(this.getContext());
        this.container.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        this.container.addView(view);
    }

    public void setFontColor(@ColorInt int color){
        view.setTextColor(color);
    }

    public void setFont(){
        view.setTypeface(FontCache.get("fonts/Radens.ttf", this.getContext()));
    }

    public void setFontSize(float size){
        view.setTextSize(size);
        fontSize = size;
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        float recurScale = getRecursiveScale();
        Log.d("TEXTVIEW", "text view set scale " + scale + " " + fontSize);
        view.setTextSize(fontSize * recurScale);
    }

    @Override
    protected void updateRecurScale() {
        super.updateRecurScale();
        float recurScale = getRecursiveScale();
        view.setTextSize(fontSize * recurScale);
    }

    public void setText(String text){
        text = text;
        view.setText(text);
    }
}
