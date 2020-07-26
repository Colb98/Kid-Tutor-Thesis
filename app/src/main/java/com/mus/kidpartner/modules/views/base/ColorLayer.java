package com.mus.kidpartner.modules.views.base;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;

public class ColorLayer extends Sprite {
    public ColorLayer(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initLayer();
    }

    private void initLayer(){
        setSpriteAnimation(R.drawable.gray_layer);
        int width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        Size size = getContentSize(false);
        if(size.width < width || size.height < height){
            setScale(Math.max(height/size.height, width/size.width));
        }
        setAlpha(0.5f);

        GameTextView desc = new GameTextView(this);
        SpannableString string = new SpannableString("Chạm màn hình để tiếp tục");
        string.setSpan(new ForegroundColorSpan(0xffffffff), 0, string.length(), 0);
        desc.setText(string, FontCache.Font.UVNKyThuat, 20);
        desc.setPositionY(height * 0.8f);
        desc.setPositionCenterScreen(false, true);

        addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });
    }
}
