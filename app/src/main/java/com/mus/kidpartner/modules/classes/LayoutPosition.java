package com.mus.kidpartner.modules.classes;


import android.util.Log;

public class LayoutPosition {
    public enum Rule {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }
    public static class LayoutRule{
        public Rule rule;
        public float val;
    }

    public static LayoutRule getRule(String type, float val){
        LayoutRule r = new LayoutRule();
        switch(type){
            case "left": r.rule = Rule.LEFT; break;
            case "right": r.rule = Rule.RIGHT; break;
            case "bottom": r.rule = Rule.BOTTOM; break;
            case "top": r.rule = Rule.TOP; break;
            default: return null;
        }
        r.val = val;
        return r;
    }

    private int numRules = 0;
    private LayoutRule[] currentRules = new LayoutRule[2];
    public LayoutPosition(LayoutRule ...rules){
        if(rules.length == 0){
            Log.d("LayoutPos", "Why you ever create this without rule?");
        }
        else if(rules.length == 1){
            currentRules[0] = rules[0];
            numRules = 0;
        }
        // Only take either of TOP-BOTTOM or LEFT-RIGHT
        // PRIO BOTTOM and RIGHT
        else{
            boolean top_bottom = false;
            boolean left_right = false;
            LayoutRule rTopBottom = null;
            LayoutRule rLeftRight = null;
            for(int i=0;i<rules.length;i++){
                Rule rule = rules[i].rule;
                if(rule == Rule.TOP || rule == Rule.BOTTOM){
                    if(!top_bottom || rule == Rule.BOTTOM){
                        top_bottom = true;
                        rTopBottom = rules[i];
                    }
                }
                else{
                    if(!left_right || rule == Rule.RIGHT){
                        left_right = true;
                        rLeftRight = rules[i];
                    }
                }
            }

            currentRules[0] = rTopBottom; currentRules[1] = rLeftRight;
        }
    }

    public LayoutRule[] getRules(){
        return currentRules;
    }
}
