package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.projectcyber.GameView;
import com.example.projectcyber.Joystick;

public class Player extends Entity{
    private Bitmap bitmap;
    private double movementSpeed = 200;
    private int maxHP = 100;
    private int currHP = 100;
    public Player(GameView gameView){
        super(0, 0, gameView);
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

    @Override
    public void update(long deltaTime) {
        Joystick joystick = gameView.getJoystick();
        velX = joystick.getDirX() * movementSpeed;
        velY = joystick.getDirY() * movementSpeed;
        posX += velX * deltaTime/1000;
        posY += velY * deltaTime/1000;
    }


}
