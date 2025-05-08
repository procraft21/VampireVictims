package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;

import java.util.HashSet;

public abstract class Entity {

    protected double prevX = 0;
    protected double prevY = 0;
    protected double posX;
    protected double posY;
    protected double velX;
    protected double velY;

    protected GameView gameView;

    protected ImmunityList immunityList;

    public Entity(double posX, double posY, GameView gameView){
        this.posX = posX;
        this.posY = posY;
        this.gameView = gameView;

        immunityList = new ImmunityList(200, this);

    }

    protected void savePrevPos(){
        prevX = posX;
        prevY = posY;
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
    }

    public void update(long deltaTime){
        posX += velX * deltaTime/1000.0;
        posY += velY * deltaTime/1000.0;

        HashSet<Entity> closeEntities = getCollisionList();
        assert closeEntities != null;
        for(Entity entity : closeEntities){
            if(entity != this && this.hasCollision(entity)){

                resolveEntityCollision(entity);
                if(!immunityList.inList(entity))
                    immunityList.add(entity);
            }
        }

        gameView.updateGridPlacement(this, prevX, prevY);
        immunityList.update(deltaTime);
    }

    public abstract void setBitmap(Bitmap bitmap);

    public double distance(Entity other){
        return Utils.distance(this.posX, this.posY, other.posX, other.posY);
    }

    protected abstract void resolveEntityCollision(Entity other);

    protected boolean hasCollision(Entity other){
        boolean coll = distance(other) <= this.getCollisionRadius() + other.getCollisionRadius();
        //if(coll)Log.d("collision", tag + " " + other.tag + " : " + distance(other));
        return coll;
    }

    public double getCollisionRadius() {
        return 0;
    }

    protected HashSet<Entity> getCollisionList(){
        return gameView.getEntitiesNear(this);
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void destroy(){
        gameView.removeEntity(this);
    }
}
