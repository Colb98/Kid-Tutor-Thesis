package com.mus.kidpartner.modules.views.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.mus.kidpartner.modules.classes.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewContainer extends RelativeLayout {
    protected int[] drawOrder = new int[0];
    protected int[] invertedDrawOrder = new int[0];
    List<GameView> childrenTemp = new ArrayList<>();
    Node root;
    // Số view con
    private int childCount = 0;
    // Số view con tính cả view native (text, textbox)
    private int realChildCount = 0;
    public boolean isTheRootContainer = false;

    public class Node{
        GameView val;
        int index;
        List<Node> children;
        Node next;

        public Node(GameView v, int index, GameView parent){
            val = v;
            this.index = index;
            children = new ArrayList<>();
            v.setViewTreeNode(this);
            next = null;

            if(parent != null)
                rearrangeParentNode(parent);
        }

        private void rearrangeParentNode(GameView p){
            Node parent = p.getViewTreeNodeAsParent();
            int i=0;
            while (i < parent.children.size() && parent.children.get(i).val.zOrder <= val.zOrder){
                i++;
            }
            parent.children.add(i, this);
            Node previousNode;
            if(i > 0){
                Node mostRightNode = parent.children.get(i-1);
                while (mostRightNode.children.size() > 0){
                    mostRightNode = mostRightNode.children.get(mostRightNode.children.size()-1);
                }
                previousNode = mostRightNode;
            }
            else{
                previousNode = parent;
            }

            next = previousNode.next;
            previousNode.next = this;
        }
    }

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

    @Override
    public void removeView(View view) {
        if(view instanceof GameView){
            // Index trong mảng thứ tự
            GameView gv = (GameView) view;
            Node node = gv.getViewTreeNodeAsChild();

            int subViews = gv.getSubViewsCount();
            if(subViews > 0){
                for(View v : gv.getSubViews()){
                    super.removeView(v);
                }
            }
            Node previousNode;
            int idx = gv.parent.getViewTreeNodeAsParent().children.indexOf(node);
            if(idx == 0){
                previousNode = gv.parent.getViewTreeNodeAsParent();
            }
            else{
                previousNode = gv.parent.getViewTreeNodeAsParent().children.get(idx - 1);
                while(previousNode.next != node){
                    previousNode = previousNode.next;
                }
            }
            previousNode.next = node.next;
//            if(node.index >= drawOrder.length - subViews - 1)
//                Log.d("wrong","node idx: " + node.index + ", length: " + (drawOrder.length - subViews - 1));
            gv.parent.getViewTreeNodeAsParent().children.remove(node);
            int[] nDrawOrder = new int[drawOrder.length - subViews - 1];
            int[] nIDrawOrder = new int[drawOrder.length - subViews - 1];
            for(int i=0,j=0;i<drawOrder.length;i++){
               if(i < node.index || i > node.index + subViews){
                   if(drawOrder[i] > drawOrder[node.index]){
                       nDrawOrder[j] = drawOrder[i] - subViews - 1;
                   }
                   else{
                       nDrawOrder[j] = drawOrder[i];
                   }
                   nIDrawOrder[nDrawOrder[j]] = j;
                   j++;
               }
            }
            Node n = root;
            while(n != null){
                if(n.index > node.index + subViews){
                    n.index -= subViews + 1;
                }
                n = n.next;
            }
            drawOrder = nDrawOrder;
            invertedDrawOrder = nIDrawOrder;

            realChildCount -= subViews + 1;
            childCount -= 1;
            super.removeView(view);
        }
    }

    private void removeAllViewsByNode(Node node, List<Integer> removingIndex){
        for(Node child : node.children){
            removeAllViewsByNode(child, removingIndex);
        }
        super.removeView(node.val);
        node.val.parent.getViewTreeNodeAsParent().children.remove(node);
        removingIndex.add(node.index);
    }

    public void setWorldPosition(Point pos){
        setWorldPosition(pos.x, pos.y);
    }

    public void setWorldPosition(float x, float y){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp.setMargins((int)x, (int)y, 0, 0);
        this.setLayoutParams(lp);
    }

    public Node setRoot(GameView gv){
        return root = new Node(gv, 0, null);
    }

    public void addGameView(View child, View parent){
        if(child instanceof GameView){
            GameView gv = (GameView) child;
            childrenTemp.add(gv);
            super.addView(child);
            if(gv.getSubViewsCount() > 0){
                for(View view : gv.getSubViews())
                    super.addView(view);
            }
            Node node = new Node(gv, realChildCount, (GameView)parent);
            childCount++;
            realChildCount += 1 + gv.getSubViewsCount();
            if(root == null){
                root = node;
            }
            updateViewOrder();
            invalidate();
        }
    }

    // Deprecate
    public void addGameView_old(View child, View container, List<Integer> orders){
        super.addView(child);
        super.addView(container);
//        Log.d("Container", "ADD CHILD" + orders.size());
        this.updateViewOrder_old(orders);
        invalidate();
    }
//
//    public void addView(View child, List<Integer> orders) {
//    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.d("Container", "has child: " + childCount + " and size: " + drawOrder.length);
        return invertedDrawOrder[i];
    }

    public void updateViewOrder(GameView changedView){
        Node node = changedView.getViewTreeNodeAsChild();
        Node parent = changedView.parent.getViewTreeNodeAsParent();
        Node previousNode = changedView.parent.getViewTreeNodeAsParent();
        while(previousNode.next != node){
            previousNode = previousNode.next;
        }
        // Next của node trước đưa vào node con cuối của node mới vào
        Node lastNode = node;
        while(lastNode.children.size() > 0){
            lastNode = lastNode.children.get(lastNode.children.size()-1);
        }

        previousNode.next = lastNode.next;
        lastNode.next = null;

        parent.children.remove(node);
        int i=0;
        while(i < parent.children.size() && parent.children.get(i).val.zOrder <= node.val.zOrder){
            i++;
        }
        parent.children.add(i, node);
        previousNode = parent;
        if(i > 0){
            previousNode = parent.children.get(i-1);
            while(previousNode.children.size() > 0){
                previousNode = previousNode.children.get(previousNode.children.size() - 1);
            }
        }

        lastNode.next = previousNode.next;
        previousNode.next = node;
        updateViewOrder();
    }

    // Nhận 1 list zOrder theo kiểu lộn xộn -> drawOrder có thứ tự
    public void updateViewOrder() {
        Node cur = root;
        if(!isTheRootContainer) cur = root.next;

        drawOrder = new int[realChildCount];
        invertedDrawOrder = new int[realChildCount];
        int i=0;
        while(cur != null){
            invertedDrawOrder[i++] = cur.index;

            for(int j=0;j<cur.val.getSubViewsCount();j++)
                invertedDrawOrder[i++] = cur.index + j + 1;

            cur = cur.next;
        }

        for(i=0;i<realChildCount;i++){
            drawOrder[invertedDrawOrder[i]] = i;
        }
    }


    public void updateViewOrder_old(List<Integer> orders){
        if(orders.size() == 0) return;
        // Đếm mỗi loại zOrder (lộn xộn)
        HashMap<Integer, Integer> count = new HashMap<>();
        // Chuyển giữa loại zOrder lộn xộn và 1 2 3 4
        HashMap<Integer, Integer> mapping = new HashMap<>();
        // Sắp xếp zOrder lộn xộn theo thứ tự tăng dần
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

        // Clone list zOrder cũ
        List<Integer> newOrder = new ArrayList<>(orders);
        for(i = 0;i<newOrder.size();i++){
            newOrder.set(i, mapping.get(newOrder.get(i)));
        }

        // a: đếm từng loại (counting), s: đếm cộng dồn từng loại
        int[] a = new int[mapping.size()], s = new int[mapping.size()];
        for(HashMap.Entry<Integer, Integer> entry : mapping.entrySet()){
            a[entry.getValue()] = count.get(entry.getKey());
        }
        s[0] = a[0];
        for(i = 1;i<a.length;i++){
            s[i] = a[i] + s[i-1];
        }

        // Thứ tự vẽ của child[i] = s[newOrder[i]] - a[newOrder[i]]. Sau đó giảm a[newOrder[i]] đi 1
        drawOrder = new int[newOrder.size()*2];
        for(i = 0;i<newOrder.size();i++){
            int val = newOrder.get(i);
            newOrder.set(i, s[val] - a[val]);
            a[val] -= 1;

            drawOrder[i] = newOrder.get(i)*2;
            drawOrder[i*2+1] = newOrder.get(i)*2+1;
        }
//        Log.d("ViewContainer", Utils.arrayToString(newOrder));
//        Log.d("ViewContainer", Utils.arrayToString(drawOrder));
    }

//    public int[] getDrawOrder() {
//        return drawOrder;
//    }

    // Return mảng nghịch đảo val và idx của mảng drawOrder
    public int[] getDrawOrderIndexing(GameView view){
        Node node = view.getViewTreeNodeAsParent();
        int[] ans = new int[node.children.size()];

        int i=0;
        for(Node child : node.children){
            ans[i] = view.getChildren().indexOf(child.val);
            i++;
        }
        return ans;
    }

    public int[] getDrawOrder(){
        return drawOrder;
    }
}
