package com.mus.kidpartner.modules.views.base;


import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.common.SignInButton;
import com.mus.kidpartner.modules.controllers.Director;

public class GoogleSignInButtonWrapper extends Sprite implements View.OnClickListener {
    SignInButton button;
    View[] subViews;

    public GoogleSignInButtonWrapper(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        initView();
        super.afterAddChild();
    }

    private void initView(){
        button = new SignInButton(getContext());

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);
        if(subViews == null)
            subViews = new View[1];
        subViews[0] = button;

        button.setOnClickListener(this);
    }

    @Override
    public View[] getSubViews() {
        return subViews;
    }

    @Override
    public int getSubViewsCount() {
        return 1;
    }

    @Override
    public void setContentSize(float width, float height) {
        super.setContentSize(width, height);
    }


    @Override
    public void onClick(View v) {
        performClick();
    }

    @Override
    public boolean performClick() {
        boolean result = super.performClick();
        Director.getInstance().signIn();
        return result;
    }
}
