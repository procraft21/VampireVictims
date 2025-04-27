package com.example.projectcyber.GameActivity.gameObjects;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class ImmunityList {
    HashMap<Entity, Double> list;
    double defaultTime;
    public ImmunityList(double defaultTime){
        list = new HashMap<>();
        this.defaultTime = defaultTime;
    }

    public void update(double deltaTime){
        for(Entity entity : list.keySet()){
            //assert list.get(entity) != null;
            list.put(entity, list.get(entity) - deltaTime);
            if(list.get(entity) <= 0 ){
                list.remove(entity);
            }
        }
    }

    public void add(@NonNull Entity entity){
        list.put(entity, defaultTime);
    }

    public boolean inList(@NonNull Entity entity){
        return list.get(entity) != null;
    }

}
