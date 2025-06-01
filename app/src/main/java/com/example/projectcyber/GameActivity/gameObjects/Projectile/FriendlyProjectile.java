package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import android.graphics.Bitmap;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;


public class FriendlyProjectile extends Projectile {

    /**
     * Constructor with custom bitmap.
     *
     * @param posX               Initial X position.
     * @param posY               Initial Y position.
     * @param gameView           Game view context.
     * @param penetration        Number of enemies the projectile can hit before destruction.
     * @param damage             Damage dealt to enemies.
     * @param speed              Speed of projectile.
     * @param area               Size of projectile bitmap.
     * @param bitmap             Custom bitmap for the projectile.
     * @param projectileMovement Movement behavior strategy.
     */
    public FriendlyProjectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, int area, Bitmap bitmap, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView, penetration, damage, speed, area, bitmap, projectileMovement);
    }

    /**
     * Constructor with default bitmap.
     */
    public FriendlyProjectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, int area, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView, penetration, damage, speed, area, null, projectileMovement);
    }

    /**
     * Handles collision with other entities.
     * If the entity is an enemy and not currently immune, applies damage and decreases penetration.
     *
     * @param other The entity this projectile collided with.
     */
    @Override
    protected void resolveEntityCollision(Entity other) {
        if (other instanceof Enemy) {
            if (immunityList.inList(other)) return;

            Enemy enemy = (Enemy) other;
            enemy.takeDamage(damage);

            penetration--;
            if (penetration <= 0) {
                destroy();
            }
        }
    }
}
