package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.projectcyber.GameView;

public abstract class Entity {
    protected double posX;
    protected double posY;
    protected double velX;
    protected double velY;

    GameView gameView;

    public Entity(double posX, double posY, GameView gameView){
        this.posX = posX;
        this.posY = posY;
        this.gameView = gameView;
    }
    public double getPositionX(){
        return posX;
    }
    public double getPositionY(){
        return posY;
    }
    public double getVelX() {
        return velX;
    }
    public double getVelY() {
        return velY;
    }

    protected abstract void drawRelative(Canvas canvas, double relX, double relY);

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void draw(Canvas canvas){
        double playerPosX = gameView.getPlayer().getPositionX();
        double playerPosY = gameView.getPlayer().getPositionY();
        double relX = posX - playerPosX + canvas.getWidth()/2;
        double relY = posY - playerPosY + canvas.getHeight()/2;
        drawRelative(canvas, relX, relY);
    };
    public abstract void update(long deltaTime);
    public abstract void setBitmap(Bitmap bitmap);

}
