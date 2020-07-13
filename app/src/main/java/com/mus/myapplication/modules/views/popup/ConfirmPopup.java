package com.mus.myapplication.modules.views.popup;

import android.graphics.Color;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ConfirmPopup extends Sprite {
    List<Runnable> onNormalCallbacks;
    List<Runnable> onRedCallbacks;
    public ConfirmPopup(GameView parent){
        super(parent);
        onNormalCallbacks = new ArrayList<>();
        onRedCallbacks = new ArrayList<>();
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initColorLayer();
        initGUI();
    }

    private void initColorLayer(){
        Sprite colorLayer = new Sprite(this);
        colorLayer.setSpriteAnimation(R.drawable.gray_layer);
        int width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        Size size = colorLayer.getContentSize(false);
        if(size.width < width || size.height < height){
            colorLayer.setScale(Math.max(height/size.height, width/size.width));
        }
        colorLayer.setAlpha(0.5f);
        mappingChild(colorLayer, "layer");
    }

    private void initGUI(){
        Sprite background = new Sprite(this);
        background.setSpriteAnimation(R.drawable.exit_popup);
        background.scaleToMaxWidth(Utils.getScreenWidth()/3);
        background.setPositionCenterScreen(false, false);
        mappingChild(background, "background");

        Button close = new Button(background);
        close.setSpriteAnimation(R.drawable.popup_close_button);
        close.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", close.getContentSize(false).width + 15), LayoutPosition.getRule("top", 15)));
        mappingChild(close, "btnClose");
        close.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                hide();
                removeFromParent();
            }
        });

        Button normal = new Button(background);
        normal.setSpriteAnimation(R.drawable.button_normal);
        normal.setPosition(background.getContentSize(false).width/2 + 30, background.getContentSize().height - normal.getContentSize().height - 30);
        normal.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                onNormalClicked();
            }
        });
        GameTextView lbNormal = new GameTextView(normal);
        lbNormal.setText(Utils.getString(R.string.text_stay).toUpperCase(), FontCache.Font.UVNNguyenDu, 18);
        lbNormal.setFontColor(Color.BLACK);
        lbNormal.setPositionCenterParent(false, false);

        Button red = new Button(background);
        red.setSpriteAnimation(R.drawable.button_red);
        red.setPosition(background.getContentSize(false).width/2 - red.getContentSize().width - 30, background.getContentSize().height - red.getContentSize().height - 30);
        red.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                onRedClicked();
            }
        });
        GameTextView lbRed = new GameTextView(red);
        lbRed.setText(Utils.getString(R.string.text_exit).toUpperCase(), FontCache.Font.UVNNguyenDu, 18);
        lbRed.setFontColor(Color.WHITE);
        lbRed.setPositionCenterParent(false, false);

        GameTextView message = new GameTextView(background);
        message.setText(Utils.getString(R.string.text_confirm_exit), FontCache.Font.UVNChimBienNhe, 18);
        message.setPosition(0, close.getPosition().y + close.getContentSize().height + 5);
        message.setPositionCenterParent(false, true);
        mappingChild(message, "message");
    }
    
    public void setMessage(CharSequence s){
        GameTextView message = (GameTextView) getChild("message");
        message.setText(s);
    }
    
    public void setTextNormal(CharSequence s){
        GameTextView normalText = (GameTextView) getChild("normalText");
        normalText.setText(s);
    }
    
    public void setTextRed(CharSequence s){
        GameTextView redText = (GameTextView) getChild("redText");
        redText.setText(s);
    }

    public void onNormalClicked(){
        for(Runnable r : onNormalCallbacks){
            r.run();
        }
        removeFromParent();
    }

    public void onRedClicked(){
        for(Runnable r : onRedCallbacks){
            r.run();
        }
        removeFromParent();
    }

    public void addOnNormalCallback(Runnable r){
        onNormalCallbacks.add(r);
    }

    public void addOnRedCallback(Runnable r){
        onRedCallbacks.add(r);
    }
}
