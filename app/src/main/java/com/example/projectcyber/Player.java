package com.example.projectcyber;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class Player extends Entity{
    Bitmap bitmap;
    private int speed = 10;
    public Player(Bitmap bitmap, Point pos){
        this.pos = pos;
        this.bitmap = bitmap;
    }

    @Override
    protected void drawRelative(Canvas canvas, Point center) {
        canvas.drawBitmap(bitmap, canvas.getWidth()/2 + (pos.x-center.x) - bitmap.getWidth()/2f, canvas.getHeight()/2 + (pos.y-center.y) - bitmap.getHeight()/2f, null);
    }

    public void move(PointF dirVector){
        pos.x += dirVector.x * speed;
        pos.y += dirVector.y * speed;
    }


}
