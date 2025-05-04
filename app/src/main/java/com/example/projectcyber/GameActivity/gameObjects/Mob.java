package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;

import java.util.HashSet;

public abstract class Mob extends Entity{


    protected Bitmap bitmap;

    protected double mass = 1;

    public Mob(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);

    }

    public void addToGrid(){
        gameView.addToGrid(this);
    }

    protected void savePrevPos(){
        prevX = posX;
        prevY = posY;
    }

    @Override
    protected void resolveEntityCollision(Entity entity){
        if(entity instanceof Mob){
            resolveMovementOfMobCollision((Mob)entity);
        }
    }

    protected void resolveMovementOfMobCollision(Mob b){
        double deltaVx = b.velX - velX;
        double deltaVy = b.velY - velY;

        double deltaPosX = b.posX - posX;
        double deltaPosY = b.posY - posY;

        double distance = distance(b);
        if(distance < getCollisionRadius() + b.getCollisionRadius()){

            double overlap = getCollisionRadius() + b.getCollisionRadius() - distance;

            double massRatio = mass/(mass+b.mass);
            if(mass == Double.POSITIVE_INFINITY){
                massRatio = 1;
            }

            //b.posX += massRatio * deltaPosX;
            posX -= (1-massRatio) * overlap*deltaPosX/distance;
            //b.velY += massRatio * deltaPosY;
            posY -= (1-massRatio) * overlap * deltaPosY/distance;

        }

        double deltasDotProduct = deltaVx * deltaPosX + deltaVy * deltaPosY;
        double deltaPosSize = Utils.distance(0,0,deltaPosX,deltaPosY);
        double massCoefficient = (2*b.mass/(mass+b.mass));
        if(b.mass == Double.POSITIVE_INFINITY){
            massCoefficient = 2;
        }
        velX = velX + massCoefficient*deltasDotProduct/(deltaPosSize*deltaPosSize) * (deltaPosX);
        velY = velY + massCoefficient*deltasDotProduct/(deltaPosSize*deltaPosSize) * (deltaPosY);
    }




    @Override
    public double getCollisionRadius() {
        if(bitmap == null)
            return 75/2.0;
        return bitmap.getWidth()/2;
    }
}
