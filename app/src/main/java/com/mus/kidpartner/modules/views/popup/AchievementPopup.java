package com.mus.kidpartner.modules.views.popup;

import android.view.ViewTreeObserver;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.LayoutPosition;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.controllers.AchievementManager;
import com.mus.kidpartner.modules.models.common.Achievement;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;

public class AchievementPopup extends Sprite {
    public AchievementPopup(GameView parent){
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
        achievementTitle.setText("Nhà thông thái", FontCache.Font.UVNNguyenDu, 30);
        achievementTitle.setPositionCenterParent(false, true);
        mappingChild(achievementTitle, "title");

        Sprite achievementIcon = new Sprite(background);
        achievementIcon.setSpriteAnimation(R.drawable.iq_level_2);
        achievementIcon.setPositionCenterParent(false, false);
        mappingChild(achievementIcon, "icon");

        GameTextView lbUnlocked = new GameTextView(background);
        lbUnlocked.setText("Bạn đã mở khoá huy hiệu mới", FontCache.Font.UVNChimBienNhe, 18);
        lbUnlocked.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 2f*lbUnlocked.getContentSize().height)));
        lbUnlocked.setPositionCenterParent(false, true);
        mappingChild(lbUnlocked, "lbUnlocked");

        GameTextView lbCongrats = new GameTextView(background);
        lbCongrats.setPosition(lbUnlocked.getPosition().add(0,  - 1.5f * lbCongrats.getContentSize(false).height));
        lbCongrats.setText("Chúc mừng", FontCache.Font.UVNChimBienNang, 20);
        lbCongrats.setPositionCenterParent(false, true);

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

    public void loadAchivement(Achievement achievement){
        Sprite achievementIcon = (Sprite) getChild("icon");
        GameTextView achievementTitle = (GameTextView) getChild("title");

        achievementIcon.setSpriteAnimation(achievement.getResIcon());
        achievementIcon.setPositionCenterParent(false, false);

        achievementTitle.setText(AchievementManager.categoryMap.get(achievement.getCategory()).name + " cấp " + (achievement.getLevel() + 1), FontCache.Font.UVNNguyenDu, 30);
        achievementTitle.setPositionCenterParent(false, true);
    }
}
