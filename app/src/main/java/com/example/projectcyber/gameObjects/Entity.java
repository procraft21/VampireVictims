package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Pair;

import com.example.projectcyber.GameView;
import com.example.projectcyber.Utils;

import java.util.Set;

public abstract class Entity {

    protected double prevX = 0;
    protected double prevY = 0;
    protected double posX;
    protected double posY;
    protected double velX;
    protected double velY;

    GameView gameView;

    int tag;
    static int counter = 0;

    public Entity(double posX, double posY, GameView gameView){
        this.posX = posX;
        this.posY = posY;
        this.gameView = gameView;

        tag = counter;
        counter++;
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
    public void update(long deltaTime){
        posX += velX * deltaTime/1000;
        posY += velY * deltaTime/1000;
        gameView.updateGridPlacement(this, prevX, prevY);
    };
    public abstract void setBitmap(Bitmap bitmap);

    public double distance(Entity other){
        return Utils.distance(this.posX, this.posY, other.posX, other.posY);
    }

    protected abstract void resolveMobCollision(Mob other);

    protected boolean hasCollision(Entity other){
        boolean coll = distance(other) <= this.getCollisionRadius() + other.getCollisionRadius();
        //if(coll)Log.d("collision", tag + " " + other.tag + " : " + distance(other));
        return coll;
    }
    public double getCollisionRadius() {
        return 0;
    }




}
