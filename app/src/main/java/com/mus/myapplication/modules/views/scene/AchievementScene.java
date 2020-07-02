package com.mus.myapplication.modules.views.scene;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.AchievementManager;
import com.mus.myapplication.modules.models.common.Achievement;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

import java.util.ArrayList;
import java.util.List;

public class AchievementScene extends GameScene {
    List<Sprite> achivements = new ArrayList<>();
    public AchievementScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
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

        initRightSide(bg2);
    }

    private void initRightSide(Sprite bg){
        GameTextView lbGroupTitle = new GameTextView(bg);
        lbGroupTitle.setPosition(0, 20);
        lbGroupTitle.setText("Đầu bếp", FontCache.Font.UVNKyThuat, 20);
        lbGroupTitle.setPositionCenterParent(false, true);

        GameTextView lbGroupDestination = new GameTextView(bg);
        lbGroupDestination.setPosition(0, 30);
        lbGroupDestination.setText("Nhận được ở khu vực nhà bếp", FontCache.Font.UVNKyThuat, 15);
        lbGroupDestination.setPositionCenterParent(false, true);

//        for(int i=0;i<3;i++){
//            Sprite sprite = new Sprite(bg);
//            sprite.setSpriteAnimation(AchievementManager.getInstance().resIdMap.get("food").get(i));
//            achivements.add(sprite);
//            sprite.
//        }
    }
}
