package com.mus.myapplication.modules.views.base;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;

public class TextViewContainer extends ViewContainer {
    public TextViewContainer(@NonNull Context context) {
        super(context);
        drawOrder = new int[]{0};
    }

//    @Override
//    public void updateViewOrder(List<Integer> orders) {
//        super.updateViewOrder(orders);
//        int[] oldOrder = drawOrder;
//        drawOrder = new int[drawOrder.length+1];
//        drawOrder[0] = 0;
//        for(int i=0;i<oldOrder.length;i++){
//            drawOrder[i+1] = oldOrder[i] + 1;
//        }
//    }
//
////    @Override
//    public int[] getDrawOrderIndexing() {
//        int[] ans = new int[drawOrder.length/2];
//        for(int i=1;i<=drawOrder.length/2;i++){
//            ans[drawOrder[i*2]/2 - 1] = i - 1;
//        }
//        return ans;
//    }
}
