package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.XpGem;

import java.util.HashMap;
import java.util.Random;

public class DropTable {

    static HashMap<Pickup, Integer> weightTable;
    int totalWeight;
    GameView gameView;


    public DropTable(GameView gameView){
        this.gameView = gameView;

        if(weightTable == null){
            weightTable = new HashMap<>();
            weightTable.put(new XpGem(0,0, gameView), 100);

            totalWeight = 100; //the weight for nothing.
            for(Pickup pickup : weightTable.keySet()){
                totalWeight += weightTable.get(pickup);
            }
        }

    }

    public Pickup getDrop(){
        Random rnd = new Random();
        int pick = rnd.nextInt(totalWeight);
        for(Pickup pickup : weightTable.keySet()){
            pick -= weightTable.get(pickup);
            if(pick < 0){
                return pickup.clone();
            }
        }

        return null; //if none were selected...
    }
}