package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.R;

public class Projectile extends Entity {

    protected static Bitmap baseBitmap;

    protected ProjectileMovement projectileMovement;

    protected int penetration;
    protected double damage;
    protected double speed;

    public Projectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, int area,Bitmap bitmap, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView);
        if(baseBitmap == null){
            baseBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_img);

        }

        this.bitmap = Bitmap.createScaledBitmap(bitmap == null ? baseBitmap : bitmap,area,area, false);

        this.projectileMovement = projectileMovement;
        this.penetration = penetration;
        this.damage = damage;
        this.speed = speed;
    }


    @Override
    protected void resolveEntityCollision(Entity other) {

    }

    public void setProjectileMovement(ProjectileMovement movement){
        this.projectileMovement = projectileMovement;
    }

    @Override
    public void update(long deltaTime){
        savePrevPos();
        projectileMovement.update(deltaTime,gameView, this);
        super.update(deltaTime);
    }

    @Override
    public double getCollisionRadius(){
        return bitmap.getWidth()/2;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
