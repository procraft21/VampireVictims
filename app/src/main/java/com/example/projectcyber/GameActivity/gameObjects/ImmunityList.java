package com.example.projectcyber.GameActivity.gameObjects;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

public class ImmunityList {

    //hashmap entity -> time remaining in list.
    ConcurrentHashMap<Entity, Double> timeMap;
    double defaultTime;
    Entity owner;
    public ImmunityList(double defaultTime, Entity owner){
        timeMap = new ConcurrentHashMap<>();
        this.defaultTime = defaultTime;
        this.owner = owner;
    }

    public void update(double deltaTime){
        for(Entity entity : timeMap.keySet()){
            timeMap.put(entity, timeMap.get(entity) - deltaTime);
            if(timeMap.get(entity) <= 0 ){

                timeMap.remove(entity);
            }
        }
    }

    public void add(@NonNull Entity entity){
        timeMap.put(entity, defaultTime);
    }

    public boolean inList(@NonNull Entity entity){
        return timeMap.get(entity) != null;
    }

}
