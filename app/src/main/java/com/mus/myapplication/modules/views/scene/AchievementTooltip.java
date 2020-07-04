package com.mus.myapplication.modules.views.scene;

import android.util.Log;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.controllers.AchievementManager;
import com.mus.myapplication.modules.models.common.Achievement;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

import java.text.DateFormat;
import java.util.Date;

public class AchievementTooltip extends Sprite {
    Sprite icon;
    GameTextView name;
    GameTextView date;

    public AchievementTooltip(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initView();
    }

    private void initView(){
        setSpriteAnimation(R.drawable.tooltip_bg);
        icon = new Sprite(this);
        icon.setSpriteAnimation(R.drawable.chef_level_1);
        icon.scaleToMaxHeight(getContentSize(false).height * 0.9f);
        icon.setPosition(10,0);
        icon.setPositionCenterParent(true, false);

        name = new GameTextView(this);
        name.setText("Huy hiệu nấu ăn lv1", FontCache.Font.UVNChimBienNang, 20);
        name.setPosition(icon.getPosition().x + icon.getContentSize(false).width + 10, 10);

        date = new GameTextView(this);
        date.setText("12:37 02.07.2020", FontCache.Font.UVNChimBienNhe, 15);
        date.setPosition(name.getPosition().x, name.getPosition().y + name.getContentSize().height + 10);
    }

    public void loadDataByAchievement(Achievement a){
        if(a.isAchieved()){
            icon.setSpriteAnimation(a.getResIcon());
        }
        else{
            icon.setSpriteAnimation(R.drawable.achievement_not_unlocked);
        }
        icon.scaleToMaxHeight(getContentSize(false).height * 0.8f);
        icon.setPosition(getContentSize(false).height * 0.1f,0);
        icon.setPositionCenterParent(true, false);

        String str = "Huy hiệu cấp " + (a.getLevel()+1);
        name.setText(str, FontCache.Font.UVNChimBienNang, 20);
        name.setPosition(icon.getPosition().x + icon.getContentSize(false).width + 10, getContentSize(false).height/2 - name.getContentSize().height - 5);

        if(!a.isAchieved()){
            date.setVisible(false);
        }
        else{
            date.setVisible(true);
            Date d = new Date(a.getAchievedTimestamp());
            date.setText(d.toString(), FontCache.Font.UVNChimBienNhe, 15);
            date.setPosition(name.getPosition().x, name.getPosition().y + name.getContentSize().height + 10);
        }
    }
}
