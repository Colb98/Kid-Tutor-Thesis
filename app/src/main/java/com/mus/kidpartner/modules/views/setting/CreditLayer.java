package com.mus.kidpartner.modules.views.setting;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.Size;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;
import com.mus.kidpartner.modules.views.base.ScrollView;
import com.mus.kidpartner.modules.views.base.Sprite;

public class CreditLayer extends Sprite {
    public CreditLayer(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initLayer();
    }

    private void initLayer(){
        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();
        Sprite bg = new Sprite(this);
        bg.setSpriteAnimation(R.drawable.white_10_10);
        bg.setScaleX(width/10);
        bg.setScaleY(height/10);
        bg.setZOrder(-1);
        mappingChild(bg, "background");

        GameTextView title = new GameTextView(this);
        title.setText("Credit", FontCache.Font.RADENS, 30);
        title.setPositionY(10);
        title.setPositionCenterScreen(false, true);

        ScrollView scroller = new ScrollView(this, new Size(width, height));
        scroller.setPositionY(title.getPosition().y + title.getContentSize().height + 30);

        float lastY = 0;
        float lastHeight = 0;

        String[] mapList = new String[]{
                "Toy Piano - Wayne Jones",
                "Ukulele Beach - Doug Maxwell",
                "After School Jamboree - The Green Orbs",
                "Old MacDonald"
        };

        GameTextView map = getTitle("Map Music", scroller, lastY + lastHeight);
        lastHeight = map.getContentSize().height;
        lastY = map.getPosition().y;

        for(int i=0;i<mapList.length;i++){
            GameTextView v = getContent(mapList[i], scroller, lastHeight + lastY);
            lastHeight = v.getContentSize().height;
            lastY = v.getPosition().y;
        }

        String[] testList = new String[]{
                "You so Zany - Audionautix",
                "London Bridge",
                "Here Come The Raindrops - Reed Mathis",
                "My Dog Is Happy - Reed Mathis"
        };

        GameTextView test = getTitle("Test Music", scroller, lastY + lastHeight);
        lastHeight = test.getContentSize().height;
        lastY = test.getPosition().y;

        for(int i=0;i<testList.length;i++){
            GameTextView v = getContent(testList[i], scroller, lastHeight + lastY);
            lastHeight = v.getContentSize().height;
            lastY = v.getPosition().y;
        }

        String[] areaList = new String[]{
                "Bedroom: Twinkle Twinkle Little Star",
                "Market, zoo: Twirly Tops - The Green Orbs",
                "School: Wheels on the bus",
                "Restaurant: Birthday Cake - Reed Mathis",
                "Relative: I love my mom"
        };

        GameTextView area = getTitle("Areas Music", scroller, lastY + lastHeight);
        lastHeight = area.getContentSize().height;
        lastY = area.getPosition().y;

        for(int i=0;i<areaList.length;i++){
            GameTextView v = getContent(areaList[i], scroller, lastHeight + lastY);
            lastHeight = v.getContentSize().height;
            lastY = v.getPosition().y;
        }

        scroller.setContentSize(width, lastHeight + lastY + 200);


        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");
        btnBack.addTouchEventListener(CallbackType.ON_CLICK, new Runnable() {
            @Override
            public void run() {
                hide();
            }
        });

    }

    private GameTextView getTitle(String text, GameView view, float yOffset){
        GameTextView title = new GameTextView(view);
        title.setText(text, FontCache.Font.UVNChimBienNang, 18);
        title.setPosition(50, yOffset + 15);
        return title;
    }

    private GameTextView getContent(String text, GameView view, float yOffset){
        GameTextView content = new GameTextView(view);
        content.setText(text, FontCache.Font.UVNChimBienNhe, 16);
        content.setPosition(50, yOffset + 5);
        return content;
    }


}
