package com.mus.myapplication.modules.views.school;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.classes.FontCache;
import com.mus.myapplication.modules.classes.LayoutPosition;
import com.mus.myapplication.modules.classes.SceneCache;
import com.mus.myapplication.modules.classes.Size;
import com.mus.myapplication.modules.classes.Utils;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.models.school.IQTest;
import com.mus.myapplication.modules.views.base.Button;
import com.mus.myapplication.modules.views.base.GameImageView;
import com.mus.myapplication.modules.views.base.GameScene;
import com.mus.myapplication.modules.views.base.GameTextView;
import com.mus.myapplication.modules.views.base.GameView;
import com.mus.myapplication.modules.views.base.Sprite;

public class IQTestScene extends GameScene {
    private int currentQuestion;
    private IQTest currentTest;

    public IQTestScene(GameView parent){
        super(parent);
    }

    @Override
    protected void afterAddChild() {
        super.afterAddChild();
        initScene();
        initButtons();
    }

    private void initButtons(){
        final IQTestScene that = this;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Button back = (Button)getChild("btnBack");
                back.addTouchEventListener(Sprite.CallbackType.ON_CLICK, new Runnable() {
                    @Override
                    public void run() {
                        Director.getInstance().loadScene(SceneCache.getScene("school"));
                    }
                });
            }
        });
    }

    private void initScene(){
        setSceneBackground(R.drawable.school_iq_quiz_background);
        Sprite bg = (Sprite) getChild("background");

        float width = Utils.getScreenWidth(), height = Utils.getScreenHeight();

        Sprite bg2 = new Sprite(this);
        bg2.setSpriteAnimation(R.drawable.school_iq_quiz_background_2);
        bg2.setScale(0.8f*width/bg2.getContentSize(false).width);
        bg2.setPosition(width/2 - bg2.getContentSize(false).width/2, height/2 - bg2.getContentSize(false).height/2);
        mappingChild(bg2, "questionPanel");

        Button buttonNext = new Button(this);
        buttonNext.setSpriteAnimation(R.drawable.school_iq_quiz_button_next);
//        countDownCircle.setPosition(, height/2 - countDownCircle.getContentSize(false).height/2 + bg2.getContentSize(false).height/2 + 150);
        buttonNext.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("bottom", 30 + buttonNext.getContentSize(false).height), LayoutPosition.getRule("left", width/2 - buttonNext.getContentSize(false).width/2)));

        Sprite countDownBox = new Sprite(bg2);
        countDownBox.setSpriteAnimation(R.drawable.school_iq_quiz_count_down);
        countDownBox.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", countDownBox.getContentSize(false).width), LayoutPosition.getRule("top", -countDownBox.getContentSize(false).height-20)));
        final GameTextView lbCountDown = new GameTextView(countDownBox);
        lbCountDown.setText("30:00");
        lbCountDown.setFont(FontCache.Font.UVNNguyenDu);
        lbCountDown.setPositionCenterParent(false, false);
        lbCountDown.addUpdateRunnable(new UpdateRunnable() {
            float remainTime = 30*60;
            @Override
            public void run() {
                remainTime -= dt*3;
                if(remainTime < 0)
                    return;
                lbCountDown.setText(Utils.secondToString(remainTime));
            }
        });

        Button btnBack = new Button(this);
        btnBack.setSpriteAnimation(R.drawable.back_button);
        btnBack.setPosition(50, 50);
        mappingChild(btnBack, "btnBack");

        GameTextView lbTitle = new GameTextView(this);
        lbTitle.setPosition(0, 30);
        int questionNumber = 1;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("Câu số ", new RelativeSizeSpan(1.5f), 0).append(String.valueOf(questionNumber), new RelativeSizeSpan(1.5f), 0).setSpan(new StyleSpan(Typeface.BOLD), 0, builder.length(), 0);
        builder.append("/10");

        lbTitle.setText(builder);
        lbTitle.setFont(FontCache.Font.UVNKyThuat);
        lbTitle.setPositionCenterScreen(false, true);

        testQuestion();
    }

    private void testQuestion(){
        Sprite panel = (Sprite) getChild("questionPanel");
        GameImageView question = new GameImageView(panel);
        question.init(R.drawable.school_iq_test1_q2_ques);
        question.setImageViewBound(panel.getContentSize(false).width*0.45f, panel.getContentSize(false).height * 0.9f);
        question.setPosition(15, (panel.getContentSize(false).height - question.getContentSize(false).height)/2);

        // Distance between answer
        float dis;
        GameImageView a1 = new GameImageView(panel);
        a1.init(R.drawable.school_iq_test1_q2_ans1);
        a1.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        Size size = a1.getContentSize(false);
        dis = (panel.getContentSize(false).width*0.55f - 3*size.width)/5;
        a1.setPositionCenterParent(true, false);
        a1.move(0, -size.height/2-dis/2);
        a1.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 3 + dis*3)));
        GameImageView a2 = new GameImageView(panel);
        a2.init(R.drawable.school_iq_test1_q2_ans2);
        a2.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a2.setPosition(a1.getPosition());
        a2.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 2 + dis * 2)));
        GameImageView a3 = new GameImageView(panel);
        a3.init(R.drawable.school_iq_test1_q2_ans3);
        a3.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a3.setPosition(a1.getPosition());
        a3.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width + dis)));
        GameImageView a4 = new GameImageView(panel);
        a4.init(R.drawable.school_iq_test1_q2_ans4);
        a4.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a4.setPositionCenterParent(true, false);
        a4.move(0, size.height/2+dis/2);
        a4.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 3 + dis*3)));
        GameImageView a5 = new GameImageView(panel);
        a5.init(R.drawable.school_iq_test1_q2_ans5);
        a5.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a5.setPosition(a4.getPosition());
        a5.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width * 2 + dis*2)));
        GameImageView a6 = new GameImageView(panel);
        a6.init(R.drawable.school_iq_test1_q2_ans6);
        a6.setImageViewBound(panel.getContentSize(false).width*0.13f, panel.getContentSize(false).height);
        a6.setPosition(a4.getPosition());
        a6.setLayoutRule(new LayoutPosition(LayoutPosition.getRule("right", size.width + dis)));

        mappingChild(question, "question");
        mappingChild(a1, "a1");
        mappingChild(a2, "a2");
        mappingChild(a3, "a3");
        mappingChild(a4, "a4");
        mappingChild(a5, "a5");
        mappingChild(a6, "a6");
    }
}
