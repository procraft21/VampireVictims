package com.example.projectcyber.GameActivity.gameObjects.Projectile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Entity;
import com.example.projectcyber.R;

/**
 * Represents a projectile in the game, such as a bullet or missile.
 * Handles movement, damage, collision radius, and visual representation.
 */
public class Projectile extends Entity {

    /// Default image used if no custom bitmap is provided
    protected static Bitmap baseBitmap;

    /// Handles movement behavior of the projectile
    protected ProjectileMovement projectileMovement;

    /// Number of entities the projectile can hit before being destroyed
    protected int penetration;

    /// Amount of damage the projectile inflicts on impact
    protected double damage;

    /// Movement speed of the projectile
    protected double speed;

    /**
     * Constructs a new projectile entity.
     *
     * @param posX               Initial X position.
     * @param posY               Initial Y position.
     * @param gameView           Reference to the game view.
     * @param penetration        Number of enemies the projectile can pierce.
     * @param damage             Damage value the projectile inflicts.
     * @param speed              Speed of the projectile.
     * @param area               Size (width/height) of the bitmap to render.
     * @param bitmap             Optional bitmap; defaults to base if null.
     * @param projectileMovement Custom movement behavior strategy.
     */
    public Projectile(double posX, double posY, GameView gameView, int penetration, double damage, int speed, int area, Bitmap bitmap, ProjectileMovement projectileMovement) {
        super(posX, posY, gameView);

        if (baseBitmap == null) {
            baseBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_img);
        }

        this.bitmap = Bitmap.createScaledBitmap(bitmap == null ? baseBitmap : bitmap, area, area, false);

        this.projectileMovement = projectileMovement;
        this.penetration = penetration;
        this.damage = damage;
        this.speed = speed;
    }

    /**
     * Placeholder for collision resolution logic.
     * Actual behavior should be implemented (e.g. damaging enemies, reducing penetration).
     *
     * @param other The entity this projectile collided with.
     */
    @Override
    protected void resolveEntityCollision(Entity other) {
    }

    /**
     * Sets the movement behavior of this projectile.
     *
     * @param movement New movement behavior.
     */
    public void setProjectileMovement(ProjectileMovement movement) {
        this.projectileMovement = movement;
    }

    /**
     * Updates the projectile’s position and handles interactions.
     *
     * @param deltaTime Time since last update (in milliseconds).
     */
    @Override
    public void update(long deltaTime) {
        savePrevPos(); // Ensure grid updates can track movement
        projectileMovement.update(deltaTime, gameView, this);
        super.update(deltaTime);
    }

    /**
     * Returns the radius used for collision detection.
     *
     * @return Collision radius, based on the bitmap size.
     */
    @Override
    public double getCollisionRadius() {
        return bitmap.getWidth() / 2.0;
    }

    /**
     * Gets the current speed of the projectile.
     *
     * @return Speed in game units per second.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the projectile's speed.
     *
     * @param speed New speed value.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
