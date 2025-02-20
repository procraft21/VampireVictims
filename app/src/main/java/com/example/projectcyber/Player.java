package com.example.projectcyber;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

public class Player extends Entity{
    private static Player instance;
    private Bitmap bitmap;
    private PointF dirVector;
    private int movementSpeed = 7;
    private int maxHP = 100;
    private int currHP = 100;
    private Player(){
        super(new Point());

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
        Paint playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, canvas.getWidth()/2 + (pos.x-center.x) - bitmap.getWidth()/2f, canvas.getHeight()/2 + (pos.y-center.y) - bitmap.getHeight()/2f, playerPaint);
    }

    public void setDirVector(PointF dirVector){
        this.dirVector = dirVector;
    }

    public void move(){
        pos.x += dirVector.x * movementSpeed;
        pos.y += dirVector.y * movementSpeed;
    }

    public void takeDamage(int damage){
        if(damage < 0){
            return;
        }
        currHP -= damage;
        if(currHP < 0)
            currHP = 0;
        return;
    }


}
