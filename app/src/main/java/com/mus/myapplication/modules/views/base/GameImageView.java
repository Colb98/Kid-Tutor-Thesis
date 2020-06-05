package com.mus.myapplication.modules.views.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Point;
import com.mus.myapplication.modules.classes.Size;

public class GameImageView extends Sprite {
    private Bitmap image;
    private Size imageSize;
    public GameImageView(GameView parent){
        super(parent);
    }

    public GameImageView(){
        super();
    }

    public void init(int resId, Size contentSize){
        init(resId, contentSize, null);
    }

    public void init(int resId){
        init(resId, null, null);
    }

    public void init(int resId, Size contentSize, Point position){
        setSprite(resId);

        if(contentSize == null){
            contentSize = imageSize;
        }
        if(position == null){
            position = new Point(0,0);
        }

        setScaleType(ScaleType.FIT_XY);
        setPosition(position);
        setContentSize(contentSize.width, contentSize.height);
        Log.d("ImageView", "resource size: " + imageSize + ", content size: " + contentSize + ", view size: " + realContentSize);

    }

    public void setSprite(int resIds){
        image = BitmapFactory.decodeResource(getContext().getResources(), resIds);
        if(image == null) throw new NullPointerException("Resource id: " + resIds + " cannot be found.");

        imageSize = new Size(image.getWidth(), image.getHeight());
        setImageBitmap(image);
    }

    @Override
    public void setContentSize(float width, float height) {
        float r1 = imageSize.width / imageSize.height;
        float r2 = width / height;

        if(r1 > r2){
            height = imageSize.height*width/imageSize.width;
        }
        else{
            width = imageSize.width*height/imageSize.height;
        }

        super.setContentSize(width, height);
        invalidate();
    }
}
