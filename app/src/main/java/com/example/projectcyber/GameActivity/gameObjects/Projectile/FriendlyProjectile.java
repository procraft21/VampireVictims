package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;

public class FriendlyProjectile extends Projectile{

    public FriendlyProjectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView, penetration, damage, speed, projectileMovement);
    }

    @Override
    protected void resolveEntityCollision(Entity other) {
        if(other instanceof Enemy){
            Enemy enemy = (Enemy)other;
            enemy.takeDamage(damage);
            penetration--;
            if(penetration <= 0){
                destroy();
            }
        }
        super.resolveEntityCollision(other);
    }
}
