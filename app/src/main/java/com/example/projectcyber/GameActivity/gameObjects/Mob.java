package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;


public abstract class Mob extends Entity {

    /** Base image of the Mob facing right */
    protected Bitmap imgRight;

    /** Physical mass of the mob, used for collision resolution */
    protected double mass = 1;

    /** Current HP of the mob */
    protected double currHP;

    /** The direction the mob is currently facing */
    protected enum Direction {
        Left, Right
    }

    protected Direction direction;
    private Direction lastDrawnDirection;

    /**
     * Constructs a Mob instance.
     *
     * @param posX     Initial X position.
     * @param posY     Initial Y position.
     * @param gameView Reference to the game view context.
     */
    public Mob(double posX, double posY, GameView gameView) {
        super(posX, posY, gameView);
        direction = Direction.Right;
        lastDrawnDirection = Direction.Right;
    }

    /**
     * Updates the mob's state each frame.
     * Also updates the direction the mob is facing based on velocity.
     */
    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);
        if (velX > 0) direction = Direction.Right;
        if (velX < 0) direction = Direction.Left;
    }

    /**
     * Applies damage to the mob and destroys it if HP drops to 0 or below.
     *
     * @param damage Amount of damage.
     */
    public void takeDamage(double damage) {
        if (damage < 0) return;
        currHP -= damage;
        if (currHP <= 0) {
            currHP = 0;
            destroy();
        }
    }

    /**
     * Draws the mob relative to the canvas, flipping the sprite if necessary
     * based on movement direction.
     */
    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        if (imgRight == null) return;
        if (bitmap == null) bitmap = imgRight;

        if (direction == Direction.Right) {
            bitmap = imgRight;
        } else if (lastDrawnDirection == Direction.Right) {
            bitmap = getImageHorizontalFlip(bitmap);
        }

        lastDrawnDirection = direction;
        super.drawRelative(canvas, relX, relY);
    }

    /**
     * Resolves collisions between this mob and another entity.
     */
    @Override
    protected void resolveEntityCollision(Entity entity) {
        if (entity instanceof Mob) {
            resolveMovementOfMobCollision((Mob) entity);
        }
    }

    /**
     * Resolves physical collision between two mobs based on position, velocity,
     * and mass, applying simple elastic collision logic.
     *
     * @param b The other Mob.
     */
    protected void resolveMovementOfMobCollision(Mob b) {
        double deltaVx = b.velX - velX;
        double deltaVy = b.velY - velY;
        double deltaPosX = b.posX - posX;
        double deltaPosY = b.posY - posY;

        double distance = distance(b);
        if (distance < getCollisionRadius() + b.getCollisionRadius()) {
            double overlap = getCollisionRadius() + b.getCollisionRadius() - distance;
            double massRatio = mass / (mass + b.mass);
            if (mass == Double.POSITIVE_INFINITY) {
                massRatio = 1;
            }

            posX -= (1 - massRatio) * overlap * deltaPosX / distance;
            posY -= (1 - massRatio) * overlap * deltaPosY / distance;
        }

        double deltasDotProduct = deltaVx * deltaPosX + deltaVy * deltaPosY;
        double deltaPosSize = Utils.distance(0, 0, deltaPosX, deltaPosY);
        double massCoefficient = (2 * b.mass / (mass + b.mass));
        if (b.mass == Double.POSITIVE_INFINITY) {
            massCoefficient = 2;
        }

        velX = velX + massCoefficient * deltasDotProduct / (deltaPosSize * deltaPosSize) * deltaPosX;
        velY = velY + massCoefficient * deltasDotProduct / (deltaPosSize * deltaPosSize) * deltaPosY;
    }

    /**
     * Returns the collision radius used for detecting overlaps with other entities.
     *
     * @return The collision radius.
     */
    @Override
    public double getCollisionRadius() {
        if (bitmap == null) return 75 / 2.0;
        return bitmap.getWidth() / 2;
    }

    /**
     * Flips the given bitmap horizontally.
     *
     * @param bitmap Bitmap to flip.
     * @return Flipped bitmap.
     */
    protected Bitmap getImageHorizontalFlip(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, imgRight.getWidth(), imgRight.getHeight(), matrix, true);
    }
}
