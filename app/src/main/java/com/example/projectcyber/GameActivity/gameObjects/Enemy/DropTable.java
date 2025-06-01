package com.example.projectcyber.GameActivity.gameObjects.Enemy;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Coin;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.XpGem;

import java.util.HashMap;
import java.util.Random;

public class DropTable {

    // HashMap storing each pickup type and its associated drop weight
    HashMap<Pickup, Integer> weightTable;

    // Sum of all weights, including the implicit weight for "no drop"
    int totalWeight;

    public DropTable(GameView gameView) {
        // Initialize the weight table
        weightTable = new HashMap<>();

        // Add pickups and their weights
        weightTable.put(new XpGem(0, 0, gameView), 100);  // XP gem has a weight of 100
        weightTable.put(new Coin(0, 0, gameView), 50);    // Coin has a weight of 50

        // Add an implicit weight of 200 for "no drop"
        totalWeight = 200;

        // Sum all individual pickup weights to get the final totalWeight
        for (Pickup pickup : weightTable.keySet()) {
            totalWeight += weightTable.get(pickup);
        }
    }

    // Returns a random pickup based on the defined weights
    public Pickup getDrop() {
        Random rnd = new Random();
        int pick = rnd.nextInt(totalWeight);

        for (Pickup pickup : weightTable.keySet()) {
            pick -= weightTable.get(pickup);

            // When pick becomes negative, the current pickup is selected
            if (pick < 0) {
                return pickup.clone(); // Return a cloned instance of the selected pickup
            }
        }

        // If no pickup was selected (i.e., the result falls into the "no drop" range), return null
        return null;
    }
}
