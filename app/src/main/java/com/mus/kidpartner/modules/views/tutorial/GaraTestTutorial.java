package com.mus.kidpartner.modules.views.tutorial;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.mus.kidpartner.R;
import com.mus.kidpartner.modules.classes.FontCache;
import com.mus.kidpartner.modules.classes.Utils;
import com.mus.kidpartner.modules.views.base.Button;
import com.mus.kidpartner.modules.views.base.ColorLayer;
import com.mus.kidpartner.modules.views.base.GameTextView;
import com.mus.kidpartner.modules.views.base.GameView;

public class GaraTestTutorial extends ColorLayer {
    private static final CharSequence TEXT = "Di chuyển các bộ phận đồ vật để ghép chúng lại với nhau nhé!\n ";

    public GaraTestTutorial(GameView parent) {
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();

        initViews();
        hideDesc();
    }

    private void initViews(){
        GameTextView title = new GameTextView(this);
        SpannableString ss = new SpannableString("THỬ TÀI LẮP RÁP");
        ss.setSpan(new ForegroundColorSpan(0xffffffff), 0, ss.length(), 0);
        title.setText(ss, FontCache.Font.UVNNguyenDu, 28);
        title.setPositionY(title.getContentSize().height*0.5f);
        title.setPositionCenterScreen(false, true);

        GameTextView desc = new GameTextView(this);
        SpannableString s = new SpannableString(TEXT);
        s.setSpan(new ForegroundColorSpan(0xffffffff), 0, s.length(), 0);
        desc.setText(s, FontCache.Font.UVNChimBienNhe, 18);
        desc.setPositionY(title.getPosition().y + title.getContentSize().height*1.5f);
        desc.setPositionCenterScreen(false, true);

        Button buttonNext = new Button(this);
        buttonNext.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
//        countDownCircle.setPosition(, height/2 - countDownCircle.getContentSize(false).height/2 + bg2.getContentSize(false).height/2 + 150);
        Log.e("lul", "d: " + (30 + buttonNext.getContentSize().height));
        buttonNext.setPositionY(Utils.getScreenHeight() - (30 + buttonNext.getContentSize().height));
        buttonNext.setPositionCenterScreen(false, true);
        Utils.setSpriteShaking(buttonNext, Utils.degreeToRad(25), true);

        GameTextView btnDesc = new GameTextView(this);
        SpannableString s2 = new SpannableString("Ấn nút này để qua câu tiếp theo nè!");
        s2.setSpan(new ForegroundColorSpan(0xffffffff), 0, s2.length(), 0);
        btnDesc.setText(s2, FontCache.Font.UVNChimBienNhe, 18);
        btnDesc.setPositionY(buttonNext.getPosition().y - btnDesc.getContentSize().height - 10);
        btnDesc.setPositionXCenterWithView(buttonNext);
    }
}
