package com.example.projectcyber;

import android.graphics.Canvas;
import android.graphics.Point;

public abstract class Entity {
    protected Point pos;
    protected abstract void drawRelative(Canvas canvas, Point center);

    public Entity(Point pos){
        this.pos = new Point(pos);
    }
    public Point getPosition(){
        return pos;
    }

    public abstract void move();
}
