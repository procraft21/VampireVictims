package com.example.projectcyber.gameObjects;

import android.graphics.Bitmap;

import com.example.projectcyber.GameView;
import com.example.projectcyber.Utils;

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

    /**Detects and changes the velocity of only this mob according to the principles of elastic collision.*/
    protected void detectAndChangeVelocityAfterCollision(){
        HashSet<Entity> closeEntities = getCollisionList();
        assert closeEntities != null;
        for(Entity entity : closeEntities){

            if(entity != this && this.hasCollision(entity)){
                if(entity instanceof Mob)
                    resolveMobCollision((Mob)entity);
            }
        }

        /*for(Mob mob : closeMobs){
            if(mob != this && this.hasCollision(mob)){
                velX =  mob.velX;
                velY = mob.velY;

            }
        }*/

    }

    @Override
    protected  void resolveMobCollision(Mob b) {

        //calculate new velocity according to collision formula

        //velX = (mass * velX +b.mass * b.velX + b.mass*restitutionCoefficent*(b.velX-velX))/(mass+b.mass);
        //velY = (mass * velY +b.mass * b.velY + b.mass*restitutionCoefficent*(b.velY-velY))/(mass+b.mass);


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




    protected HashSet<Entity> getCollisionList(){
        return gameView.getMobsNear(this);
    }

    @Override
    public double getCollisionRadius() {
        if(bitmap == null)
            return 75/2.0;
        return bitmap.getWidth()/2;
    }
}
