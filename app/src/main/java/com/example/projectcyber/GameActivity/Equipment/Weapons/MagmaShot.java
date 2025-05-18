package com.example.projectcyber.GameActivity.Equipment.Weapons;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;
import com.example.projectcyber.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MagmaShot extends Weapon{



    public MagmaShot(GameView gameView) {
        this.gameView = gameView;

        this.name = "Magma Shot";
        this.initialDesc = "Shoots an arc of heavy shots at a random enemy";

        equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.weapon_magma_shot);
        projectileBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.weapon_magma_shot);

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Damage, 20.0);
        startingStats.put(WeaponStatsType.Cooldown, 3000.0);
        startingStats.put(WeaponStatsType.Speed, 300.0);
        startingStats.put(WeaponStatsType.Pierce, 1.0);
        startingStats.put(WeaponStatsType.Amount, 3.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 50.0);
        startingStats.put(WeaponStatsType.Area, 100.0);

        stats = new WeaponStatsContainer(startingStats, gameView);

        level = 0;
        maxLevel = 8;

        levelEffects = new ArrayList<>();
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)),
                new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)),
                new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)),
                new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));


        for(int i = 0; i<level-1; i++){
            for(LevelUpModifier modifer : levelEffects.get(i))
                modifer.apply(stats);
        }


        timeLeftInWindow = 0;
        isActive = true;
        timeSinceLastShot = 0;
        amountShot = 0;


    }

    Enemy chosenEnemy;

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        Projectile projectile = new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView, (int)stats.getStatValue(WeaponStatsType.Pierce), stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),(int) stats.getStatValue(WeaponStatsType.Area),projectileBitmap, new ProjectileMovement(amountShot) {
            boolean lockedOn = false;
            @Override
            public void update(long deltaTime, GameView gameView, Projectile projectile) {
                if(!lockedOn){
                    if(this.amountShot == 0){
                        HashSet<Enemy> enemies = gameView.getEnemies();
                        Enemy randomEnemy = null;
                        int size = enemies.size();
                        if(size > 0){
                            int item = new Random().nextInt(size);
                            int i = 0;
                            for(Enemy enemy : enemies){
                                if(i == item){
                                    randomEnemy = enemy;
                                    break;
                                }
                                i++;
                            }
                            chosenEnemy = randomEnemy;
                        }


                    }
                    if(chosenEnemy != null){
                        double deltaAlpha = Math.PI /12;
                        double totalAngle = (stats.getStatValue(WeaponStatsType.Amount)-1) * deltaAlpha;
                        double enemyAngle = Math.atan2(chosenEnemy.getPositionY() - player.getPositionY(), chosenEnemy.getPositionX() - player.getPositionX());
                        double correctAngle = enemyAngle - totalAngle/2 + this.amountShot * deltaAlpha;

                        double dist = chosenEnemy.distance(player);

                        projectile.setVelX(projectile.getSpeed() *Math.cos(correctAngle));
                        projectile.setVelY(projectile.getSpeed() * Math.sin(correctAngle));
                    }else{
                        projectile.destroy();
                    }

                    lockedOn = true;
                }

                double dist = projectile.distance(player);
                if(dist > 10000)
                    projectile.destroy();
            }
        });
        return projectile;
    }
}
