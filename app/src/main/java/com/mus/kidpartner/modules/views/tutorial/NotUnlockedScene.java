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
import com.mus.kidpartner.modules.views.base.Sprite;

public class NotUnlockedScene extends ColorLayer {
    private static final CharSequence TEXT = "Ôi! Chỗ này chưa xây xong rồi.\n Quay lại sau nha, tạm thời hãy đến chơi những nơi khác đi bạn ơi!";

    public NotUnlockedScene(GameView parent) {
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
        SpannableString ss = new SpannableString("KHU VỰC ĐANG XÂY DỰNG!");
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

        Sprite crying = new Sprite(this);
        crying.setSpriteAnimation(R.drawable.crying);
        crying.scaleToMaxHeight(Utils.getScreenHeight()*0.5f);
        crying.setPositionY(desc.getPosition().y + desc.getContentSize().height + 15);
        crying.setPositionCenterScreen(false, true);
    }
}
