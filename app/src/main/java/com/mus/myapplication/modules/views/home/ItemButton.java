package com.mus.myapplication.modules.views.home;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;

public class ItemButton extends Button {
    public ItemButton(GameView parent){
        super(parent);
    }
    public void touchEventListener(final float duration1, final float scale1, final float duration2, final float scale2)
    {
        addTouchEventListener(Sprite.CallbackType.ON_TOUCH_DOWN, new Runnable() {
            @Override
            public void run() {
                runAction(new ScaleTo(duration1, scale1));
            }
        });
        addTouchEventListener(Sprite.CallbackType.ON_TOUCH_UP, new Runnable() {
            @Override
            public void run() {
                runAction(new ScaleTo(duration2, scale2));
            }
        });
    }
}
