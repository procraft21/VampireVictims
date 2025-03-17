package com.example.projectcyber;

import com.example.projectcyber.gameObjects.Enemy;

import java.util.ArrayList;

public class EnemySummoner {
    private long currTime = 0;
    private long timeSinceLastSummon;

    private GameView gameView;

    private int currSlot = 0;
    private ArrayList<SummoningSlot> summoningList;

    public EnemySummoner(GameView gameView){
        this.gameView = gameView;
    }

    public boolean readyToSpawn(){
        return false;
    }

    public void update(long deltaTime){

    }
}

class SummoningSlot{

}
