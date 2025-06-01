package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Utils;

import java.util.HashSet;

public abstract class Entity {

    // Previous position (used for updating grid placement)
    protected double prevX = 0;
    protected double prevY = 0;

    // Current position
    protected double posX;
    protected double posY;

    // Current velocity
    protected double velX;
    protected double velY;

    // Reference to the GameView for access to global systems and entities
    protected GameView gameView;

    // Tracks entities temporarily immune to interaction (e.g., damage)
    protected ImmunityList immunityList;

    // Image used for rendering the entity
    protected Bitmap bitmap;

    /**
     * Constructs an Entity at the specified position with a link to the game environment.
     *
     * @param posX      Initial X position.
     * @param posY      Initial Y position.
     * @param gameView  Reference to the GameView for system integration.
     */
    public Entity(double posX, double posY, GameView gameView) {
        this.posX = posX;
        this.posY = posY;
        this.gameView = gameView;
        this.immunityList = new ImmunityList(200); // Default immunity of 200ms
    }

    /**
     * Saves the current position as the previous position.
     */
    protected void savePrevPos() {
        prevX = posX;
        prevY = posY;
    }

    public double getPositionX() {
        return posX;
    }

    public double getPositionY() {
        return posY;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Draws the entity relative to the player on the provided canvas.
     *
     * @param canvas The canvas to draw on.
     */
    public void draw(Canvas canvas) {
        double playerPosX = gameView.getPlayer().getPositionX();
        double playerPosY = gameView.getPlayer().getPositionY();
        double relX = posX - playerPosX + canvas.getWidth() / 2.0;
        double relY = posY - playerPosY + canvas.getHeight() / 2.0;
        drawRelative(canvas, relX, relY);
    }

    /**
     * Draws the bitmap relative to a position on the screen.
     *
     * @param canvas Canvas to draw on.
     * @param relX   X coordinate relative to center.
     * @param relY   Y coordinate relative to center.
     */
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        if (bitmap == null) return;
        canvas.drawBitmap(bitmap,
                (int) (relX - bitmap.getWidth() / 2),
                (int) (relY - bitmap.getHeight() / 2), null);
    }

    /**
     * Updates the entity's position, handles collisions, and updates immunity.
     *
     * @param deltaTime Time since last update (in ms).
     */
    public void update(long deltaTime) {
        posX += velX * deltaTime / 1000.0;
        posY += velY * deltaTime / 1000.0;

        HashSet<Entity> closeEntities = getCollisionList();
        for (Entity entity : closeEntities) {
            if (entity != this && this.hasCollision(entity)) {
                resolveEntityCollision(entity);
                if (!immunityList.inList(entity)) {
                    immunityList.add(entity);
                }
            }
        }

        gameView.updateGridPlacement(this, prevX, prevY);
        immunityList.update(deltaTime);
    }

    /**
     * Gets a list of nearby entities for potential collision detection.
     *
     * @return A HashSet of nearby entities.
     */
    protected HashSet<Entity> getCollisionList() {
        return gameView.getEntitiesNear(this);
    }

    /**
     * Checks whether this entity is currently colliding with another.
     *
     * @param other The other entity to check against.
     * @return True if they are colliding, false otherwise.
     */
    protected boolean hasCollision(Entity other) {
        return distance(other) <= this.getCollisionRadius() + other.getCollisionRadius();
    }

    /**
     * Gets the distance between this entity and another.
     *
     * @param other The other entity.
     * @return The Euclidean distance between entities.
     */
    public double distance(Entity other) {
        return Utils.distance(this.posX, this.posY, other.posX, other.posY);
    }

    /**
     * Returns the collision radius of the entity. Subclasses should override this.
     *
     * @return The radius (default 0).
     */
    public double getCollisionRadius() {
        return 0;
    }

    /**
     * Handles the response when this entity collides with another.
     *
     * @param other The other entity.
     */
    protected abstract void resolveEntityCollision(Entity other);

    /**
     * Removes the entity from the game world.
     */
    public void destroy() {
        gameView.removeEntity(this);
    }
}
