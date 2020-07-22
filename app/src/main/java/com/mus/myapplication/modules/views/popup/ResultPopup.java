package com.mus.myapplication.modules.views.popup;

import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class ResultPopup extends Sprite {
    public ResultPopup(GameView parent){
        super(parent);
    }


    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initPopup();
    }

    private void initPopup(){
        initColorLayer();
        initBackground();
        initButtons();
    }

    private void initButtons(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button close = (Button)getChild("btnClose");
                Runnable runnableClose = new Runnable() {
                    @Override
                    public void run() {
                        hide();
                    }
                };
                close.addTouchEventListener(CallbackType.ON_CLICK, runnableClose);

                Sprite layer = (Sprite)getChild("layer");
//                layer.addTouchEventListener(CallbackType.ON_CLICK, runnableClose);
            }
        });
    }

    private void initBackground(){
        Sprite background = new Sprite(this);
        background.setSpriteAnimation(R.drawable.popup_background);
        background.scaleToMaxWidth(Utils.getScreenWidth()/2);
        background.setPositionCenterScreen(false, false);
        mappingChild(background, "background");

        Button btnClose = new Button(background);
        btnClose.setSpriteAnimation(R.drawable.popup_close_button);
        btnClose.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", btnClose.getContentSize(false).width + 15), LayoutPosition.getRule("top", 15)));
        mappingChild(btnClose, "btnClose");

        GameTextView achievementTitle = new GameTextView(background);
        achievementTitle.setPosition(0, 30);
        achievementTitle.setText(Utils.getString(R.string.text_congratulation), FontCache.Font.UVNNguyenDu, 32);
        achievementTitle.setPositionCenterParent(false, true);
        mappingChild(achievementTitle, "title");

        GameTextView result = new GameTextView(background);
        result.setText("Bạn đã làm đúng a/b câu", FontCache.Font.UVNChimBienNhe, 24);
//        lbUnlocked.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 2f*lbUnlocked.getContentSize().height)));
        result.setPositionCenterParent(false, false);
        mappingChild(result, "lbResult");

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

    public void showResult(int correct, int total){
        show();
        GameTextView result = (GameTextView) getChild("lbResult");
        result.setText("Bạn đã làm đúng " + correct + "/" + total + " câu", FontCache.Font.UVNChimBienNhe, 24);
//        lbUnlocked.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 2f*lbUnlocked.getContentSize().height)));
        result.setPositionCenterParent(false, false);
    }
}
