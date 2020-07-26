package com.mus.kidpartner.modules.views.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mus.kidpartner.modules.classes.Point;
import com.mus.kidpartner.modules.classes.Size;

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

    public void init(int resId, float width, float height) {
        init(resId, new Size(width, height), null);
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
//        Log.d("ImageView", "resource size: " + imageSize + ", content size: " + contentSize + ", view size: " + realContentSize);

    }

    public void setSprite(int resIds){
        image = BitmapFactory.decodeResource(getContext().getResources(), resIds);
        if(image == null) throw new NullPointerException("Resource id: " + resIds + " cannot be found.");

        imageSize = new Size(image.getWidth(), image.getHeight());
        setImageBitmap(image);
    }

    public void setImageViewBound(Size size){
        setImageViewBound(size.width, size.height);
    }

    public void setImageViewBound(float width, float height){
        Size size = getContentSize(false);
        float widthRatio = size.width / width;
        float heightRatio = size.height / height;
        float ratio = 1/Math.max(widthRatio, heightRatio);
        setScale(getScale() * ratio);
    }
}
