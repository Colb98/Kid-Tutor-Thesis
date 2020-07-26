package com.mus.kidpartner.modules.views.home;

import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.Sprite;
import com.mus.kidpartner.modules.views.base.actions.ScaleTo;

public class ItemButton extends Button {
    private float noTouchTime = 0;
    private float noTouchThreshold;
    private float shakeTimes = 0;
    public boolean isTesting = false;

    public ItemButton(GameView parent){
        super(parent);
        noTouchThreshold = (float)(Math.random()*60) + 30;
    }
    public void touchEventListener(final float duration1, final float scale1, final float duration2, final float scale2)
    {
        addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
            @Override
            public void run() {
                runAction(new ScaleTo(duration1, scale1));
                noTouchTime = 0;
            }
        });
        addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, new Runnable() {
            @Override
            public void run() {
                runAction(new ScaleTo(duration2, scale2));
            }
        });
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(!isTesting){
            noTouchTime += dt;
            if(noTouchTime >= noTouchThreshold){
                shakeTimes ++;
                noTouchTime = 0;
                noTouchThreshold = (float)(Math.random()*60) + 30 * shakeTimes;
                Utils.setSpriteShaking(this, 0.05f, false);
            }
        }
    }
}
