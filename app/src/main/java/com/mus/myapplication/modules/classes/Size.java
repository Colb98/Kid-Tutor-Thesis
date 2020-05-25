package com.mus.myapplication.modules.classes;

import androidx.annotation.NonNull;

public class Size {
    public float width;
    public float height;

    public Size(){
        this.width = 0;
        this.height = 0;
    }
    public Size(float width, float height){
        this.width = width;
        this.height = height;
    }

    public Size multiply(float x){
        return new Size(x * width, x * height);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(width);
        sb.append(", ");
        sb.append(height);
        sb.append(")");
        return sb.toString();
    }
}