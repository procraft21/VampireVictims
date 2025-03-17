package com.example.projectcyber;

import com.example.projectcyber.gameObjects.Enemy;

import java.util.ArrayList;
import java.util.List;

public class EnemySummoner {

    class SummoningSlot{
        long length;

        long summoningInterval;
        int minimumEnemies;

        List<Enemy> enemies;

        public SummoningSlot(long length, long summoningInterval, int minimumEnemies, List<Enemy> enemies){
            this.length = length;
            this.summoningInterval = summoningInterval;
            this.minimumEnemies = minimumEnemies;
            this.enemies = enemies;
        }
    }

    private GameView gameView;

    private long timeSinceLastSummon;

    private long timeSinceSlotStarted = 0;
    private int currSlot = 0;
    private ArrayList<SummoningSlot> summoningList;

    public EnemySummoner(GameView gameView){
        this.gameView = gameView;

        summoningList = new ArrayList<>();
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(gameView, 0,0));
        summoningList.add(new SummoningSlot(100000, 500,10, enemies));
    }

    public boolean readyToSpawn(){
        return false;
    }

    public void update(long deltaTime){
        timeSinceSlotStarted += deltaTime;
        if(timeSinceSlotStarted >= summoningList.get(currSlot).length){
            currSlot++;
            timeSinceSlotStarted = 0;
        }
    }
}


