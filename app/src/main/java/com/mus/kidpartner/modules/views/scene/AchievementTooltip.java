package com.mus.kidpartner.modules.views.scene;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.models.common.Achievement;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            c.setTime(d);
            String[] monString = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            int h = c.get(Calendar.HOUR_OF_DAY);
            int m = c.get(Calendar.MINUTE);
            date.setText(h + ":" + m + " " + day + " " + monString[month] + " " + year, FontCache.Font.UVNChimBienNhe, 15);
            date.setPosition(name.getPosition().x, name.getPosition().y + name.getContentSize().height + 10);
        }
    }
}
