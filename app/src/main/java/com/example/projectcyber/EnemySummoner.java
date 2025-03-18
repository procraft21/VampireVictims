package com.example.projectcyber;

import android.util.Log;
import android.util.Pair;

import com.example.projectcyber.gameObjects.Enemy;
import com.example.projectcyber.gameObjects.FollowerEnemy;

import java.util.ArrayList;
import java.util.HashSet;
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

    private static final double SUMMON_OUTER_RING_MODIFIER = 1.2;
    private static final double SUMMON_INNER_RING_MODIFIER = 1.01;

    private static Random rnd = new Random();

    private GameView gameView;

    private long timeSinceLastSummon;

    private long timeSinceSlotStarted = 0;
    private int currSlotIndex = 0;
    private ArrayList<SummoningSlot> summoningList;

    public EnemySummoner(GameView gameView){
        this.gameView = gameView;

        summoningList = new ArrayList<>();
        HashSet<Enemy> enemies = new HashSet<Enemy>();
        enemies.add(new FollowerEnemy(gameView, 0,0));
        summoningList.add(new SummoningSlot(100000, 500,10, enemies));
    }

    public boolean readyToSpawn(){
        return false;
    }

    public void update(long deltaTime){
        timeSinceSlotStarted += deltaTime;
        timeSinceLastSummon += deltaTime;

        SummoningSlot currentSlot = summoningList.get(currSlotIndex);

        if(timeSinceSlotStarted >= currentSlot.length){
            currSlotIndex++;
            timeSinceSlotStarted = 0;
        }

        ArrayList<Enemy> enemiesToSpawn = new ArrayList<>();
        if(timeSinceLastSummon > currentSlot.summoningInterval){
            enemiesToSpawn.add(getEnemyFromSlot());
            Log.d("addedToSummonList", timeSinceLastSummon + "");
            timeSinceLastSummon = 0;
        }

        while(gameView.getNumberOfEnemies() + enemiesToSpawn.size() < currentSlot.minimumEnemies){
            enemiesToSpawn.add(getEnemyFromSlot());
        }

        gameView.summonEnemies(enemiesToSpawn);
    }

    private Enemy getEnemyFromSlot(){

        Set<Enemy> enemySet = summoningList.get(currSlotIndex).enemies;
        int size = enemySet.size();
        int item = rnd.nextInt(size);
        int i = 0;
        for(Enemy enemy : enemySet){
            if(i == item){
                Pair<Double,Double> newPos = getRandomEnemyPosition();
                try {
                    Enemy newEnemy = (Enemy) enemy.clone();
                    newEnemy.setPosX(newPos.first);
                    newEnemy.setPosY(newPos.second);
                    return newEnemy;
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        Log.d("a","a");
        return null;
    }

    private Pair getRandomEnemyPosition(){
        double playerPosX = gameView.getPlayer().getPositionX();
        double playerPosY = gameView.getPlayer().getPositionY();

        double screenWidth = gameView.getWidth();
        double screenHeight = gameView.getHeight();

        double distToCorner = Utils.distance(0,0, screenWidth/2, screenHeight/2);

        //get random radius
        double radius = rnd.nextInt((int)(distToCorner*(SUMMON_OUTER_RING_MODIFIER- SUMMON_INNER_RING_MODIFIER))) + distToCorner*SUMMON_INNER_RING_MODIFIER;
        //get random angle
        double angle = rnd.nextDouble() * Math.PI * 2;

        //compute x and y of enemy
        double posX = playerPosX + radius*Math.cos(angle);
        double posY = playerPosY + radius*Math.sin(angle);

        return new Pair(posX, posY);
    }
}


