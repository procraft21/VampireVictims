package com.example.projectcyber;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

public class Player extends Entity{
    private static Player instance;
    private Bitmap bitmap;
    private PointF dirVector;
    private int speed = 7;
    private Player(){
        this.pos = new Point();

    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public static Player getInstance(){
        if(instance == null){
            instance = new Player();
        }
        return instance;
    }

    @Override
    protected void drawRelative(Canvas canvas, Point center) {
        canvas.drawBitmap(bitmap, canvas.getWidth()/2 + (pos.x-center.x) - bitmap.getWidth()/2f, canvas.getHeight()/2 + (pos.y-center.y) - bitmap.getHeight()/2f, null);
    }

    public void setDirVector(PointF dirVector){
        this.dirVector = dirVector;
    }

    public void move(){
        pos.x += dirVector.x * speed;
        pos.y += dirVector.y * speed;
    }


}
