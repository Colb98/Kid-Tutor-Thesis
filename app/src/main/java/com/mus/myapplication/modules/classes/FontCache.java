package com.mus.myapplication.modules.classes;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCache {
    public static class Font{
        public static String RADENS = "fonts/Radens.ttf";
        public static String UVNNguyenDu = "fonts/UVNNguyenDu.ttf";
        public static String UVNKyThuat = "fonts/UVNKyThuat.ttf";
        public static String UVNChimBien = "fonts/UVNChimBien.ttf";
        public static String UVNChimBienR = "fonts/UVNChimBien_R.ttf";
        public static String UVNChimBienNang = "fonts/UVNChimBienNang.ttf";
        public static String UVNChimBienNhe = "fonts/UVNChimBienNhe.ttf";
    }

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if(tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}