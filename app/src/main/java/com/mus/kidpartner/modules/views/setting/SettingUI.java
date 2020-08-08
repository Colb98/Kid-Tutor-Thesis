package com.mus.kidpartner.modules.views.setting;

import android.provider.Settings;
import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.Director;
import com.mus.kidpartner.modules.controllers.Sounds;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.ComplexUI;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.GoogleSignInButtonWrapper;
import com.mus.kidpartner.modules.views.base.Slider;
import com.mus.kidpartner.modules.views.base.Sprite;
import com.mus.kidpartner.modules.views.base.actions.MoveTo;

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

        updateGoogleSignInState();
        setZOrder(100);
    }

    private void initButtons(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                Director.getInstance().bindUIToUpdate(SettingUI.this);

                Button btnBack = (Button)getChild("btnBack");
                btnBack.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        hide();
                    }
                });

                Button signOut = (Button)getChild("signOut");
                signOut.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().signOut();
                    }
                });

                Button credits = (Button)getChild("credits");
                credits.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        CreditLayer layer = (CreditLayer) getChild("creditLayer");
                        if(layer == null){
                            layer = new CreditLayer(SettingUI.this);
                            mappingChild(layer, "creditLayer");
                        }
                        layer.show();
                    }
                });

                Button reset = (Button)getChild("reset");
                reset.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().resetData();
                    }
                });
            }
        });
    }

    @Override
    public void show() {
        super.show();
        stopAllActions();
        MoveTo action = new MoveTo(0.5f, new Point(0,0));
        runAction(action);

        updateGoogleSignInState();
    }

    @Override
    public void hide() {
        Director.getInstance().saveSetting();

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

        final Slider music = new Slider(this);
        music.setPositionDp(270, 70);
        music.view.setMax(100);
        music.view.setProgress((int)(Sounds.getMusicVolume()*100));
        music.addOnChangeValueCallback(new Runnable() {
            @Override
            public void run() {
                Sounds.setMusicVolume(music.view.getProgress()/100f);
            }
        });

        final Slider sfx = new Slider(this);
        sfx.setPositionDp(270, 120);
        sfx.view.setMax(100);
        sfx.view.setProgress((int)(Sounds.getSoundVolume()*100));
        sfx.addOnChangeValueCallback(new Runnable() {
            @Override
            public void run() {
                Sounds.setSoundVolume(music.view.getProgress()/100f);
            }
        });
//        slider.view.setIndeterminate(true);
//        slider.view.setMinimumWidth(200);
//
//        GameTextView lbVib = new GameTextView(this);
//        lbVib.setText(Utils.getString(R.string.text_vibration));
//        lbVib.setPositionDp(200, 170);
//        lbVib.setFont(FontCache.Font.UVNNguyenDu);
//        lbVib.setFontSize(18);
//
//        Button btnAlarm = new Button(this);
////        btnAlarm.setLabel(Utils.getString(R.string.text_alarm));
//        btnAlarm.setPositionDp(210, 240);
////        btnAlarm.setLabelFont(FontCache.Font.UVNNguyenDu);
//        mappingChild(btnAlarm, "btnAlarm");


//        Button btnLanguage = new Button(this);
//        btnLanguage.setLabel("Debug");
//        btnLanguage.setPositionDp(350, 240);
////        btnLanguage.setLabelFont(FontCache.Font.UVNNguyenDu);
//        mappingChild(btnLanguage, "btnLanguage");
//
//        btnLanguage.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
//            @Override
//            public void run() {
//                Director.getInstance().debugCurrentState();
//            }
//        });

        Button credits = new Button(this);
        credits.setLabel(Utils.getString(R.string.text_about));
        credits.setPositionDp(490, 240);
        credits.setLabelFontSize(18);
//        credits.setLabelFont(FontCache.Font.UVNNguyenDu);
        mappingChild(credits, "credits");

        GoogleSignInButtonWrapper signIn = new GoogleSignInButtonWrapper(this);
        signIn.setContentSize(260, 115);
        signIn.setPositionDp(200, 170);
        mappingChild(signIn, "signIn");

        GameView signedInGroup = new GameView(this);
        mappingChild(signedInGroup, "signedIn");

        Sprite googleIcon = new Sprite(signedInGroup);
        googleIcon.setSpriteAnimation(R.drawable.google_icon);
        googleIcon.scaleToMaxWidth(60);
        googleIcon.setPosition(signIn.getPosition());

        GameTextView accountName = new GameTextView(signedInGroup);
        accountName.setPosition(googleIcon.getPosition().add(googleIcon.getContentSize().width + 10, 0));
        accountName.setPositionYCenterWithView(googleIcon);
        mappingChild(accountName, "name");

        Button signOut = new Button(signedInGroup);
        signOut.setLabel("Sign out");
        mappingChild(signOut, "signOut");

        signedInGroup.setVisible(false);

        Button reset = new Button(this);
        reset.setPositionDp(210, 240);
        reset.setLabel("Reset");
        mappingChild(reset, "reset");
    }

    public void updateGoogleSignInState(){
        boolean isSignedIn = Director.getInstance().isSignedInGoogleAccount();
        GameView signIn = getChild("signIn");
        GameView signedIn = getChild("signedIn");

        signedIn.setVisible(isSignedIn);
        signIn.setVisible(!isSignedIn);

        GameTextView name = (GameTextView) getChild("name");
        if(isSignedIn){
            name.setText(Director.getInstance().getGoogleName(), FontCache.Font.UVNKyThuat, 18);
            Button signOut = (Button) getChild("signOut");
            signOut.setPosition(name.getPosition().add(name.getContentSize().width + 10, 0));
            signOut.setPositionYCenterWithView(name);
        }
    }
}
