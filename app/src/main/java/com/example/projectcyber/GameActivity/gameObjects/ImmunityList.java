package com.example.projectcyber.GameActivity.gameObjects;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A utility class to manage temporary immunity between an Entity and others.
 * This is typically used to prevent repeated interactions (like damage) with the same entity
 * within a short timeframe.
 */
public class ImmunityList {

    private final ConcurrentHashMap<Entity, Double> timeMap;

    private final double defaultTime;

    private final Entity owner;

    /**
     * Constructs a new ImmunityList with the specified default immunity duration.
     *
     * @param defaultTime The default time (in milliseconds) that an entity should remain immune.
     * @param owner       The owner entity to which this immunity list applies.
     */
    public ImmunityList(double defaultTime, Entity owner) {
        this.timeMap = new ConcurrentHashMap<>();
        this.defaultTime = defaultTime;
        this.owner = owner;
    }

    /**
     * Updates the immunity timers for all entities in the list.
     * Removes any entity whose immunity duration has expired.
     *
     * @param deltaTime The time (in milliseconds) since the last update.
     */
    public void update(double deltaTime) {
        for (Entity entity : timeMap.keySet()) {
            double newTime = timeMap.get(entity) - deltaTime;
            if (newTime <= 0) {
                timeMap.remove(entity);
            } else {
                timeMap.put(entity, newTime);
            }
        }
    }

    /**
     * Adds an entity to the immunity list with the default duration.
     *
     * @param entity The entity to add.
     */
    public void add(@NonNull Entity entity) {
        timeMap.put(entity, defaultTime);
    }

    /**
     * Checks whether a given entity is currently in the immunity list.
     *
     * @param entity The entity to check.
     * @return True if the entity is immune, false otherwise.
     */
    public boolean inList(@NonNull Entity entity) {
        return timeMap.get(entity) != null;
    }
}
