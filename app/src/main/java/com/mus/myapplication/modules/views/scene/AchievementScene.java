package com.mus.myapplication.modules.views.scene;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.AchievementManager;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.models.common.Achievement;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.ScrollView;
import com.mus.myapplication.modules.views.base.Sprite;

import java.util.ArrayList;
import java.util.List;

public class AchievementScene extends GameScene {
    List<Sprite> achievements;
    String curCategory = "food";
    AchievementTooltip tooltip;

    public AchievementScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        achievements = new ArrayList<>();
        initScene();
        initButtons();
        initInteractions();
        setCategory("food");
    }

    private void initButtons(){
        Achievement a = AchievementManager.getInstance().onFinishedTest("iq", 0, 1, 1);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                         Director.getInstance().loadScene(SceneCache.getScene("map"));
                    }
                });
            }
        });
    }

    private void setCategory(String category){
        AchievementManager.CategoryInfo info = AchievementManager.categoryMap.get(category);
        if(info != null){
            curCategory = category;
            GameTextView title = (GameTextView) getChild("title");
            title.setText(new SpannableStringBuilder().append(info.name, new StyleSpan(Typeface.BOLD), 0), FontCache.Font.UVNKyThuat, 22);
            title.setPositionCenterParent(false, true);

            GameTextView destination = (GameTextView) getChild("destination");
            destination.setText("Nhận được ở khu vực " + info.destination, FontCache.Font.UVNKyThuat, 16);
            destination.setPositionCenterParent(false, true);

            GameTextView desc = (GameTextView) getChild("desc");
            desc.setText("Dummy text " + category, FontCache.Font.UVNKyThuat, 16);

            Sprite bg = (Sprite) getChild("bg2");
            for(int i=0;i<achievements.size();i++){
                Sprite p = achievements.get(i);
                if(AchievementManager.getInstance().getAchievement(category, i).isAchieved()){
                    p.setSpriteAnimation(info.resIds[i]);
                    p.scaleToMaxWidth(bg.getContentSize(false).width*1/3);
                }
                else{
                    p.setSpriteAnimation(R.drawable.achievement_not_unlocked);
                    p.scaleToMaxWidth(bg.getContentSize(false).width*1/3*0.75f);
                }
                p.setPosition((i*2+1)/6f*bg.getContentSize(false).width - p.getContentSize(false).width/2, 0);
                p.setPositionCenterParent(true, false);
            }
        }
    }

    private void initInteractions(){
        tooltip = new AchievementTooltip(this);
        tooltip.hide();
        for(int i=0;i<achievements.size();i++){
            final int level = i;
            final Sprite s = achievements.get(i);
            s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
                @Override
                public void run() {
                    tooltip.loadDataByAchievement(AchievementManager.getInstance().getAchievement(curCategory, level));
                    tooltip.show();
                    tooltip.setPosition(0,5);
                    tooltip.setPositionCenterScreen(false, true);
                }
            });

            s.addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, new Runnable() {
                @Override
                public void run() {
                    tooltip.hide();
                }
            });
        }
    }

    private void initScene(){
        setSceneBackground(R.drawable.achievement_scene_bg);
        Sprite bg1 = new Sprite(this);
        Sprite bg2 = new Sprite(this);
        bg1.setSpriteAnimation(R.drawable.achievement_left_bg);
        bg2.setSpriteAnimation(R.drawable.achievement_right_bg);
        bg1.scaleToMaxHeight(Utils.getScreenHeight()*0.72f);
        bg2.scaleToMaxHeight(Utils.getScreenHeight()*0.72f);

        float margin = (Utils.getScreenWidth()*0.95f - bg1.getContentSize(false).width - bg2.getContentSize(false).width)/2;
        bg1.setPosition(margin,0);
        bg2.setPosition(margin + bg1.getContentSize(false).width + Utils.getScreenWidth()*0.05f, 0);
        bg1.setPositionCenterScreen(true, false);
        bg2.setPositionCenterScreen(true, false);
        bg1.move(0,Utils.getScreenHeight()*0.05f);
        bg2.move(0,Utils.getScreenHeight()*0.05f);
        mappingChild(bg1, "bg1");
        mappingChild(bg2, "bg2");

        initLeftSide(bg1);
        initRightSide(bg2);

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);

        mappingChild(btnBack, "btnBack");
    }

    private void initLeftSide(Sprite bg){
        ScrollView scroll = new ScrollView(bg, bg.getContentSize(false));
        Size cellSize = new Size(bg.getContentSize(false).width, bg.getContentSize(false).height/2.5f);
        scroll.setScrollType(ScrollView.ScrollType.VERTICAL);
        scroll.setName("scroll");
        int cellCount = AchievementManager.categoryMap.size();
        int i = 0;
        scroll.setContentSize(cellSize.width, cellSize.height * cellCount);
        for(final String key : AchievementManager.categoryMap.keySet()){
            Sprite tabCell = new GameImageView(scroll);
            tabCell.setContentSize(cellSize);
            tabCell.setSwallowTouches(false);
            tabCell.setPosition(0, cellSize.height * (cellCount - 1 - i));
            tabCell.setName(key);
            Sprite icon = new Sprite(tabCell);
            setIconAchievement(icon, key);
            icon.scaleToMaxHeight(cellSize.height * 0.85f);
            icon.setPosition(cellSize.height * 0.075f, 0);
            icon.setPositionCenterParent(true, false);
            icon.setSwallowTouches(false);
            i++;
            GameTextView lbTabName = new GameTextView(tabCell);
            lbTabName.setText(new SpannableStringBuilder().append(AchievementManager.categoryMap.get(key).name, new StyleSpan(Typeface.BOLD), 0), FontCache.Font.UVNKyThuat, 20);
            lbTabName.setPosition(icon.getPosition().add(icon.getContentSize(false).width + 20, 0));
            lbTabName.setPositionCenterParent(true, false);
            Sprite line = new Sprite(tabCell);
            line.setSpriteAnimation(R.drawable.separator_line);
            line.setPosition(cellSize.width/2 - line.getContentSize(false).width /2, cellSize.height*0.95f);

            tabCell.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                @Override
                public void run() {
                    setCategory(key);
                }
            });
        }
    }

    private void setIconAchievement(Sprite icon, String key) {
        for(int level = AchievementManager.categoryMap.get(key).resIds.length - 1; level >= 0; level--) {
            Achievement a = AchievementManager.getInstance().getAchievement(key, level);
            if(a.isAchieved()){
                icon.setSpriteAnimation(a.getResIcon());
                return;
            }
        }
        icon.setSpriteAnimation(AchievementManager.categoryMap.get(key).resIds[0]);
    }

    private void initRightSide(Sprite bg){
        GameTextView lbGroupTitle = new GameTextView(bg);
        lbGroupTitle.setPosition(0, 20);
        lbGroupTitle.setText(new SpannableStringBuilder().append("Đầu bếp", new StyleSpan(Typeface.BOLD), 0), FontCache.Font.UVNKyThuat, 22);
        lbGroupTitle.setPositionCenterParent(false, true);
        mappingChild(lbGroupTitle, "title");

        GameTextView lbGroupDestination = new GameTextView(bg);
        lbGroupDestination.setPosition(0, lbGroupTitle.getPosition().y + lbGroupTitle.getContentSize(false).height + 30);
        lbGroupDestination.setText("Nhận được ở khu vực nhà bếp", FontCache.Font.UVNKyThuat, 16);
        lbGroupDestination.setPositionCenterParent(false, true);
        mappingChild(lbGroupDestination, "destination");

        for(int i=0;i<3;i++){
            Sprite sprite = new Sprite(bg);
            sprite.setSpriteAnimation(AchievementManager.categoryMap.get("food").resIds[i]);
            sprite.scaleToMaxWidth(bg.getContentSize(false).width*1/3);
            sprite.setPosition((i*2+1)/6f*bg.getContentSize(false).width - sprite.getContentSize(false).width/2, 0);
            sprite.setPositionCenterParent(true, false);
            achievements.add(sprite);
        }

        GameTextView lbGroupDescription = new GameTextView(bg);
        lbGroupDescription.setPosition(30, achievements.get(0).getPosition().y);
        lbGroupDescription.move(0, achievements.get(0).getContentSize(false).height + 10);
        lbGroupDescription.setText("Ghi nhận năng khiếu nấu ăn siêu đẳng", FontCache.Font.UVNKyThuat, 16);
        mappingChild(lbGroupDescription, "desc");
    }
}
