package com.mus.myapplication.modules.classes;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.mus.myapplication.R;
import com.mus.myapplication.modules.controllers.Director;
import com.mus.myapplication.modules.views.base.Sprite;
import com.mus.myapplication.modules.views.base.actions.Action;
import com.mus.myapplication.modules.views.base.actions.RepeatForever;
import com.mus.myapplication.modules.views.base.actions.ScaleTo;
import com.mus.myapplication.modules.views.base.actions.Sequence;

import java.util.Collection;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Utils {
    private static float dpToPx = 0f;
    private static int screenHeight;
    private static int screenWidth;
    private static Context context = null;

    public static void init(Activity act){
        float dip = 1f;
        dpToPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                Resources.getSystem().getDisplayMetrics()
        );
        WindowManager windowManager =
                (WindowManager) act.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        android.graphics.Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // include navigation bar
            display.getRealSize(outPoint);
        } else {
            // exclude navigation bar
            display.getSize(outPoint);
        }
        if (outPoint.y < outPoint.x) {
            screenHeight = outPoint.y;
            screenWidth = outPoint.x;
        } else {
            screenHeight = outPoint.x;
            screenWidth = outPoint.y;
        }
//        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        screenHeight = displayMetrics.heightPixels;
//        screenWidth = displayMetrics.widthPixels;
    }
    public static float getPxByDp(float dp){
        return dp * dpToPx;
    }

    public static float getDpByPx(float px){
        return px/dpToPx;
    }

    public static String arrayToString(Collection<Float> arrays){
        StringBuilder sb = new StringBuilder();
        for(float n : arrays){
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
    public static String arrayToString(float[] arrays){
        StringBuilder sb = new StringBuilder();
        for(float n : arrays){
            sb.append(n);
            sb.append(", ");
        }
        return sb.toString();
    }

    public static void setSpriteDancing(Sprite s, float s1, float s2){
        ScaleTo big = new ScaleTo(0.5f, s.getScale()*s1);
        ScaleTo small = new ScaleTo(0.5f, s.getScale()*s2);
        big.setEaseFunction(Action.EaseOut);
        small.setEaseFunction(Action.EaseIn);
        s.runAction(new RepeatForever(new Sequence(big, small)));
    }

    public static int getScreenHeight(){
        return screenHeight;
    }

    public static int getScreenWidth(){
        return screenWidth;
    }

    public static String getString(int resId){
        if(context == null){
            context = Director.getInstance().getContext();
        }
        return context.getString(resId);
    }

    public static String secondToString(float time){
        return secondToString(time, true, true, true, false);
    }

    public static String secondToString(float time, boolean hour, boolean min, boolean sec, boolean milisec){
        return milisecondToString(time*1000, hour, min, sec, milisec);
    }

    public static String milisecondToString(float time, boolean hour, boolean min, boolean sec, boolean milisec){
        int h = -1;
        int m = -1;
        int s = -1;
        float mili = time;

        if(sec){
            s = (int)mili/1000;
            mili = mili - s;
        }
        if(min){
            m = s/60;
            s = s%60;
        }
        if(hour){
            h = m/60;
            m = m%60;
        }

        String H = "", M = "", S = "", MILI = "";
        if(milisec){
            MILI = "." + mili;
        }
        if(sec){
            S = String.valueOf(s);
            if(S.length() < 2)
                S = "0" + S;
        }
        if(min && m > 0){
            M = String.valueOf(m);
            if(M.length() < 2)
                M = "0" + M;
            M += ":";
        }
        if(hour && h > 0){
            H = String.valueOf(h);
            if(H.length() < 2)
                H = "0" + H;
            H += ":";
        }
        return H + M + S + MILI;
    }
}
