package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.projectcyber.GameView;
import com.example.projectcyber.uiObjects.Joystick;

public class Player extends Mob{


    private double movementSpeed = 200;
    private double maxHP = 100;
    private double currHP = 100;


    public Player(GameView gameView){
        super(0, 0, gameView);
        gameView.addToGrid(this);
        mass = Double.POSITIVE_INFINITY;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }


    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        Paint playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, (int)(relX - bitmap.getWidth()/2),(int)(relY - bitmap.getHeight()/2), playerPaint);
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        Joystick joystick = gameView.getJoystick();
        velX = joystick.getDirX() * movementSpeed;
        velY = joystick.getDirY() * movementSpeed;
        super.update(deltaTime);

    }

    @Override
    public double getCollisionRadius() {
        return bitmap.getWidth()/2;
    }

    public void takeDamage(int damage){
        if(damage < 0)
            return;
        currHP -= damage;
        if(currHP < 0)
            currHP = 0;
    }

    public double getMaxHP(){
        return maxHP;
    }

    public double getCurrentHP(){
        return currHP;
    }
}

