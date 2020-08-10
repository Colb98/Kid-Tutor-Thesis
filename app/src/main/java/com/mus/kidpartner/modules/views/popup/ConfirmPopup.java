package com.mus.kidpartner.modules.views.popup;

import android.graphics.Color;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;

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
        mappingChild(normal, "normal");

        GameTextView lbNormal = normal.getLabel();
        lbNormal.setText(Utils.getString(R.string.text_stay).toUpperCase(), FontCache.Font.UVNNguyenDu, 18);
        lbNormal.setFontColor(Color.BLACK);
        lbNormal.setPositionCenterParent(false, false);
        mappingChild(lbNormal, "normalText");

        Button red = new Button(background);
        red.setSpriteAnimation(R.drawable.button_red);
        red.setPosition(background.getContentSize(false).width/2 - red.getContentSize().width - 30, background.getContentSize().height - red.getContentSize().height - 30);
        red.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                onRedClicked();
            }
        });
        mappingChild(red, "red");

        GameTextView lbRed = red.getLabel();
        lbRed.setText(Utils.getString(R.string.text_exit).toUpperCase(), FontCache.Font.UVNNguyenDu, 18);
        lbRed.setFontColor(Color.WHITE);
        lbRed.setPositionCenterParent(false, false);
        mappingChild(lbRed, "redText");

        GameTextView message = new GameTextView(background);
        message.alignCenter();
        message.setText(Utils.getString(R.string.text_confirm_exit), FontCache.Font.UVNChimBienNhe, 18);
        message.setPosition(0, close.getPosition().y + close.getContentSize().height + 5);
        message.setPositionCenterParent(false, true);
        mappingChild(message, "message");
    }
    
    public void setMessage(CharSequence s){
        GameTextView message = (GameTextView) getChild("message");
        message.setText(s);

        // TODO: refactor this to a cleaner solution
        // Currently scale the bg image bigger and move the buttons
        Sprite bg = (Sprite) getChild("background");
        bg.scaleToMaxHeight(message.getContentSize().height * 2.7f);
        bg.setPositionCenterScreen(false, false);
        message.setPositionCenterParent(false, true);

        Button close = (Button) getChild("btnClose");
        Button red = (Button) getChild("red");
        Button normal = (Button) getChild("normal");
        close.setScale(1f/bg.getScale());
        red.setScale(1f/bg.getScale());
        normal.setScale(1f/bg.getScale());

        close.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", close.getContentSize(false).width + 15), LayoutPosition.getRule("top", 15)));
        red.setPosition(bg.getContentSize(false).width/2 - red.getContentSize().width - 30, bg.getContentSize().height - red.getContentSize().height - 30);
        normal.setPosition(bg.getContentSize(false).width/2 + 30, bg.getContentSize().height - normal.getContentSize().height - 30);

    }
    
    public void setTextNormal(CharSequence s){
        Button button = (Button) getChild("normal");
        button.setLabel(s);
    }
    
    public void setTextRed(CharSequence s){
        Button button = (Button) getChild("red");
        button.setLabel(s);
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
