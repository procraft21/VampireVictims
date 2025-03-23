package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.projectcyber.GameView;

import java.util.HashSet;

public abstract class Mob extends Entity{

    protected Bitmap bitmap;

    public Mob(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        gameView.addToGrid(this);
    }

    @Override
    public void update(long deltaTime) {

        double prevX = posX;
        double prevY = posY;
        posX += velX * deltaTime/1000;
        posY += velY * deltaTime/1000;
        gameView.updateGridPlacement(this, prevX, prevY);
    }

    /**Detects and changes the velocity of only this mob according to the principles of elastic collision.*/
    protected void detectAndChangeVelocityAfterCollision(){
        HashSet<Mob> closeMobs = getCollisionList();
        for(Mob mob : closeMobs){

            if(mob != this && this.hasCollision(mob)){
                resolveCollision(mob);
            }
        }
        /*
        for(Mob mob : closeMobs){
            if(mob != this && this.hasCollision(mob)){
                velX = velX*(mass-mob.mass)/(mass+mob.mass) + mob.velX*2*mob.mass/(mass+mob.mass);
                velY = velY*(mass-mob.mass)/(mass+mob.mass) + mob.velY*2*mob.mass/(mass+mob.mass);

            }
        }*/

    }

    private  void resolveCollision(Mob b) {
        double normalX = b.posX - posX;
        double normalY = b.posY - posY;
        double length = Math.sqrt(normalX * normalX + normalY * normalY);
        normalX /= length;
        normalY /= length;

        double relativeVelocityX = b.velX - velX;
        double relativeVelocityY = b.velY - velY;
        double dotProduct = (relativeVelocityX * normalX) + (relativeVelocityY * normalY);

        if (dotProduct > 0) return;

        double impulse = -2 * dotProduct / 2; // Equal mass assumption

        velX -= impulse * normalX;
        velY -= impulse * normalY;

    }


    protected boolean hasCollision(Mob other){
        boolean coll = distance(other) <= this.getCollisionRadius() + other.getCollisionRadius();
        //if(coll)Log.d("collision", tag + " " + other.tag + " : " + distance(other));
        return coll;
    }

    protected HashSet<Mob> getCollisionList(){
        return gameView.getMobsNear(this);
    }

    public double getCollisionRadius() {

        return bitmap.getWidth()/2;
    }
}
