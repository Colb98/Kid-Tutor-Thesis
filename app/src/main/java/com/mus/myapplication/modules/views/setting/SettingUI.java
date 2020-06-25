package com.mus.myapplication.modules.views.setting;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.GameViewCache;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.ComplexUI;
import com.mus.myapplication.modules.views.base.GameTextEdit;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Slider;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.MoveTo;

public class SettingUI extends ComplexUI {
    public static String UI_TAG = "setting";

    public SettingUI(GameView parent){
        super(parent);
//        afterAddChild();
    }

    public SettingUI(){
        super();
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        setContentSize(Utils.getScreenWidth(), Utils.getScreenHeight());
        initViews();
        initButtons();

        setZOrder(100);
    }

    private void initButtons(){
        Button btnBack = (Button)getChild("btnBack");
        btnBack.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        stopAllActions();
        MoveTo action = new MoveTo(0.5f, new Point(0,0));
        runAction(action);

    }

    @Override
    public void hide() {
        stopAllActions();
        MoveTo moveOutAction = new MoveTo(0.5f, new Point(Utils.getScreenWidth(), 0));
        moveOutAction.addOnFinishedCallback(new Runnable() {
            @Override
            public void run() {
                SettingUI.super.hide();
            }
        }, "hide");
        runAction(moveOutAction);
    }

    private void initViews(){
        setSpriteAnimation(R.drawable.setting_bg);
//        setAlpha(0.5f);
        Sprite topBar = new Sprite(this);

        topBar.setSpriteAnimation(R.drawable.setting_topbar);

        final Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        GameTextView lbBack = new GameTextView(btnBack);
        lbBack.setText(Utils.getString(R.string.text_back));
        lbBack.setPosition(150, 15);
        lbBack.setFont(FontCache.Font.UVNNguyenDu);
        lbBack.setFontSize(18);

        GameTextView lbMusic = new GameTextView(this);
        lbMusic.setText(Utils.getString(R.string.text_music));
        lbMusic.setPositionDp(200, 70);
        lbMusic.setFont(FontCache.Font.UVNNguyenDu);
        lbMusic.setFontSize(18);

        GameTextView lbSfx = new GameTextView(this);
        lbSfx.setText(Utils.getString(R.string.text_sfx));
        lbSfx.setPositionDp(200,120);
        lbSfx.setFont(FontCache.Font.UVNNguyenDu);
        lbSfx.setFontSize(18);

        Slider slider = new Slider(this);
        slider.setPositionDp(250, 70);
//        slider.view.setMax(100);
//        slider.view.setIndeterminate(true);
//        slider.view.setMinimumWidth(200);

        GameTextView lbVib = new GameTextView(this);
        lbVib.setText(Utils.getString(R.string.text_vibration));
        lbVib.setPositionDp(200, 170);
        lbVib.setFont(FontCache.Font.UVNNguyenDu);
        lbVib.setFontSize(18);

        Button btnAlarm = new Button(this);
//        btnAlarm.setLabel(Utils.getString(R.string.text_alarm));
        btnAlarm.setPositionDp(210, 220);
//        btnAlarm.setLabelFont(FontCache.Font.UVNNguyenDu);
        mappingChild(btnAlarm, "btnAlarm");

        btnAlarm.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            private boolean val = false;
            @Override
            public void run() {
                btnBack.setVisible(val);
                val = !val;
            }
        });


        Button btnLanguage = new Button(this);
//        btnLanguage.setLabel(Utils.getString(R.string.text_language));
        btnLanguage.setPositionDp(350, 220);
//        btnLanguage.setLabelFont(FontCache.Font.UVNNguyenDu);
        mappingChild(btnLanguage, "btnLanguage");

        btnLanguage.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            private boolean left = false;
            @Override
            public void run() {
                if(left){
                    btnBack.stopAllActions();
                    btnBack.runAction(new MoveTo(0.5f, 50, 50));
                }
                else{
                    btnBack.stopAllActions();
                    btnBack.runAction(new MoveTo(0.5f, 450, 50));
                }
                left = !left;
            }
        });

        Button btnAbout = new Button(this);
//        btnAbout.setLabel(Utils.getString(R.string.text_about));
        btnAbout.setPositionDp(490, 220);
//        btnAbout.setLabelFont(FontCache.Font.UVNNguyenDu);
        mappingChild(btnAbout, "btnAbout");


    }
}