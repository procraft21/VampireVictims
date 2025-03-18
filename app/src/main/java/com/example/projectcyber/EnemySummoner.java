package com.example.projectcyber;

import android.util.Pair;

import com.example.projectcyber.gameObjects.Enemy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class EnemySummoner {

    class SummoningSlot{
        long length;

        long summoningInterval;
        int minimumEnemies;

        Set<Enemy> enemies;

        public SummoningSlot(long length, long summoningInterval, int minimumEnemies, Set<Enemy> enemies){
            this.length = length;
            this.summoningInterval = summoningInterval;
            this.minimumEnemies = minimumEnemies;
            this.enemies = enemies;
        }
    }

    private static Random rnd = new Random();

    private GameView gameView;

    private long timeSinceLastSummon;

    private long timeSinceSlotStarted = 0;
    private int currSlotIndex = 0;
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
        if(timeSinceSlotStarted >= summoningList.get(currSlotIndex).length){
            currSlotIndex++;
            timeSinceSlotStarted = 0;
        }
        ArrayList<Enemy> enemiesToSpawn = new ArrayList<>();
        if(timeSinceLastSummon > summoningList.get(currSlotIndex).summoningInterval){//TODO:add minimum enemies functionality
            enemiesToSpawn.add()
        }
    }

    private Enemy getEnemyFromSlot(){

        Set<Enemy> enemySet = summoningList.get(currSlotIndex).enemies;
        int size = enemySet.size();
        int item = rnd.nextInt(size);
        int i = 0;
        for(Enemy enemy : enemySet){
            if(i == item){
                return new Enemy(enemy, )
            }
        }
        return null;
    }

    private Pair getRandomEnemyPosition(){
        double playerPosX = gameView.getPlayer().getPositionX();
        double playerPosY = gameView.getPlayer().getPositionY();

        //get random radius
        //get random angle
        //compute x and y of enemy

    }
}


