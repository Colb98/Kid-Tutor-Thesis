package com.mus.myapplication.modules.views.school;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.GameVuforiaScene;

public class ABCTestScene extends GameVuforiaScene {
    int level = 0;
    public ABCTestScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);

        Button back = new Button(this);
        back.setSpriteAnimation(R.drawable.back_button);
        back.setPosition(50, 50);
        mappingChild(back, "btnBack");
    }

    public void setLevel(int level){
        this.level = level;
    }
}
