package com.mus.myapplication.modules.classes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.util.Collection;

public class Utils {
    private static float dpToPx = 0f;
    private static int screenHeight;
    private static int screenWidth;

    public static void init(Activity act){
        float dip = 1f;
        dpToPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                Resources.getSystem().getDisplayMetrics()
        );

        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        Log.d("What", screenHeight + " " + screenWidth);
    }
    public static float getPxByDp(float dp){
        return dp * dpToPx;
    }

    public static float getDpByPx(float px){
        return px/dpToPx;
    }

    public static String arrayToString(Collection<Integer> arrays){
        StringBuilder sb = new StringBuilder();
        for(int n : arrays){
            sb.append(n);
            sb.append(", ");
        }
        return sb.toString();
    }
    public static String arrayToString(int[] arrays){
        StringBuilder sb = new StringBuilder();
        for(int n : arrays){
            sb.append(n);
            sb.append(", ");
        }
        return sb.toString();
    }

    public static int getScreenHeight(){
        return screenHeight;
    }

    public static int getScreenWidth(){
        return screenWidth;
    }
}
