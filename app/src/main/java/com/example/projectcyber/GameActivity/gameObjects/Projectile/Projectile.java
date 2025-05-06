package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.R;

public class Projectile extends Entity {

    private static Bitmap bitmap;
    private ProjectileMovement projectileMovement;

    private int penetration;
    private double damage;
    private double speed;

    public Projectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView);
        if(bitmap == null){
            Bitmap enemyBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_img);
            enemyBitmap = Bitmap.createScaledBitmap(enemyBitmap,50,50, false);
            this.bitmap = enemyBitmap;
        }
        this.projectileMovement = projectileMovement;
        this.penetration = penetration;
        this.damage = damage;
        this.speed = speed;
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap,(int)(relX - bitmap.getWidth()/2), (int)(relY - bitmap.getHeight()/2), null );
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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


}
