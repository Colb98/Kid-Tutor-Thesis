package com.mus.myapplication.modules.views.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mus.myapplication.modules.classes.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewContainer extends RelativeLayout {
    protected int[] drawOrder = new int[0];
    public ViewContainer(@NonNull Context context) {
        super(context);
        setEnabled(false);
        setChildrenDrawingOrderEnabled(true);
    }

    public ViewContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    public ViewContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    public void addGameView(View child, View container, List<Integer> orders){
        super.addView(child);
        super.addView(container);
//        Log.d("Container", "ADD CHILD" + orders.size());
        this.updateViewOrder(orders);
        invalidate();
    }
//
//    public void addView(View child, List<Integer> orders) {
//    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.d("Container", "has child: " + childCount + " and size: " + drawOrder.length);
        return drawOrder[i];
    }

    public void updateViewOrder(List<Integer> orders){
        if(orders.size() == 0) return;
        HashMap<Integer, Integer> count = new HashMap<>();
        HashMap<Integer, Integer> mapping = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int order : orders){
            if(count.containsKey(order)){
                count.put(order, count.get(order) + 1);
            }
            else{
                count.put(order, 1);
                queue.add(order);
            }
        }
        int i = 0;
        while(queue.size() > 0){
            mapping.put(queue.poll(), i++);
        }

        List<Integer> newOrder = new ArrayList<>(orders);
        for(i = 0;i<newOrder.size();i++){
            newOrder.set(i, mapping.get(newOrder.get(i)));
        }

        int[] a = new int[mapping.size()], s = new int[mapping.size()];
        for(HashMap.Entry<Integer, Integer> entry : mapping.entrySet()){
            a[entry.getValue()] = count.get(entry.getKey());
        }
        s[0] = a[0];
        for(i = 1;i<a.length;i++){
            s[i] = a[i] + s[i-1];
        }


        drawOrder = new int[newOrder.size()*2];
        for(i = 0;i<newOrder.size();i++){
            int val = newOrder.get(i);
            newOrder.set(i, s[val] - a[val]);
            a[val] -= 1;

            drawOrder[i*2] = newOrder.get(i)*2;
            drawOrder[i*2+1] = newOrder.get(i)*2+1;
        }
//        Log.d("ViewContainer", Utils.arrayToString(newOrder));
//        Log.d("ViewContainer", Utils.arrayToString(drawOrder));
    }

//    public int[] getDrawOrder() {
//        return drawOrder;
//    }

    public int[] getDrawOrderIndexing(){
        if(drawOrder == null)
            drawOrder = new int[0];
        int[] ans = new int[drawOrder.length/2];
        for(int i=0;i<drawOrder.length/2;i++){
            ans[drawOrder[i*2]/2] = i;
        }
        return ans;
    }
}
