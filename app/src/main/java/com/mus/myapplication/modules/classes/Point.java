package com.mus.myapplication.modules.classes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Point {
    public float x;
    public float y;

    public Point(Point a){
        x = a.x;
        y = a.y;
    }

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Point add(Point p){
        return new Point(x + p.x, y + p.y);
    }

    public float dotProduct(Point p){
        return x*p.x + y*p.y;
    }

    public Point subtract(Point p){
        return new Point(x - p.x, y - p.y);
    }

    public float length(){
        return (float)Math.sqrt(x*x + y*y);
    }

    public float distanceTo(Point p){
        return subtract(p).length();
    }

    public Point product(float a){
        return new Point(x*a, y*a);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(x);
        sb.append(", ");
        sb.append(y);
        sb.append(")");
        return sb.toString();
    }

    public Point clone(){
        return new Point(x, y);
    }

    public boolean equals(Point a){
        return x == a.x && y == a.y;
    }
}
