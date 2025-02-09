package com.example.projectcyber;

import android.graphics.Canvas;
import android.graphics.Point;

public abstract class Entity {
    protected Point pos;
    protected abstract void drawRelative(Canvas canvas, Point center);
    public void drawRelative(Canvas canvas, Entity entity){
        drawRelative(canvas, entity.pos);
    }

    public abstract void move();
}
