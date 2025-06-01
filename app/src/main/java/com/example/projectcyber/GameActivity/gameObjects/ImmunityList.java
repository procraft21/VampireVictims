package com.example.projectcyber.GameActivity.gameObjects;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class ImmunityList {

    private final HashMap<Entity, Double> timeMap;
    private final double defaultTime;


    /**
     * Constructs a new ImmunityList with the specified default immunity duration.
     *
     * @param defaultTime The default time (in milliseconds) that an entity should remain immune.
     */
    public ImmunityList(double defaultTime) {
        this.timeMap = new HashMap<>();
        this.defaultTime = defaultTime;

    }

    /**
     * Updates the immunity timers for all entities in the list.
     * Removes any entity whose immunity duration has expired.
     *
     * @param deltaTime The time (in milliseconds) since the last update.
     */
    public void update(double deltaTime) {
        HashSet<Entity> toBeRemoved = new HashSet<>();
        for (Entity entity : timeMap.keySet()) {
            double newTime = timeMap.get(entity) - deltaTime;
            if (newTime <= 0) {
                toBeRemoved.add(entity);
            } else {
                timeMap.put(entity, newTime);
            }
        }
        for(Entity entity : toBeRemoved){
            timeMap.remove(entity);
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
