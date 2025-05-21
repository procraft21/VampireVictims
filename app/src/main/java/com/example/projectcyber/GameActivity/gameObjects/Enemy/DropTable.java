package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Coin;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.XpGem;

import java.util.HashMap;
import java.util.Random;

public class DropTable {

    HashMap<Pickup, Integer> weightTable;
    int totalWeight;


    public DropTable(GameView gameView){

        weightTable = new HashMap<>();
        weightTable.put(new XpGem(0,0, gameView), 100);
        weightTable.put(new Coin(0,0,gameView), 50);

        totalWeight = 200; //the weight for nothing.
        for(Pickup pickup : weightTable.keySet()){
            totalWeight += weightTable.get(pickup);
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