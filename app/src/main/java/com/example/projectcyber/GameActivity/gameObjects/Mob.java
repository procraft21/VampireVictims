package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;

import java.util.HashSet;

public abstract class Mob extends Entity{


    protected Bitmap baseImg; //base image without the rotation according to movement

    protected double mass = 1; //mass used for collision

    protected double currHP;

    protected enum Direction{
        Left, Right
    }

    protected Direction direction; //the direction the mob is currently moving.

    public Mob(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        direction = Direction.Right;

    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if(velX > 0) direction = Direction.Right;
        if(velX < 0) direction = Direction.Left;
    }

    public void addToGrid(){
        gameView.addToGrid(this);
    }

    public void takeDamage(double damage){
        if(damage < 0)
            return;
        currHP -= damage;
        if(currHP <= 0){
            currHP = 0;
            destroy();
        }
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        if(baseImg == null) return;
        if(direction == Direction.Left){
            Matrix matrix = new Matrix();
            matrix.postScale(-1,1);
            bitmap = Bitmap.createBitmap(baseImg, 0, 0, baseImg.getWidth(), baseImg.getHeight(), matrix, true);
        }else{
            bitmap = baseImg;
        }
        super.drawRelative(canvas,relX,relY);
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

    public double getCurrHP(){
        return currHP;
    }
}
