package com.mus.kidpartner.modules.classes;

import androidx.annotation.NonNull;

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

    public Point add(float x, float y){
        return new Point(this.x + x, this.y + y);
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

    public float sqrLength() {
        return x*x + y*y;
    }

    public float distanceTo(Point p){
        return subtract(p).length();
    }

    public float sqrDistanceTo(Point p){
        return subtract(p).sqrLength();
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
