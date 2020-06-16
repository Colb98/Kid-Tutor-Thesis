package com.mus.myapplication.modules.classes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.controllers.Director;

import java.util.Collection;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
