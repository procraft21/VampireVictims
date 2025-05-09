package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MagicWand extends Weapon{

    public MagicWand(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        super(startingStats, gameView);
    }

    public MagicWand(GameView gameView) {
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Damage, 10.0);
        startingStats.put(WeaponStatsType.Cooldown, 1200.0);
        startingStats.put(WeaponStatsType.Speed, 500.0);
        startingStats.put(WeaponStatsType.Pierce, 1.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 150.0);
        startingStats.put(WeaponStatsType.Area, 50.0);

        stats = new WeaponStatsContainer(startingStats, gameView);

        level = 8;
        maxLevel = 8;

        levelEffects = new ArrayList<>();
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Cooldown, new StatModifier(StatModifier.Type.bonus, -200)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Pierce, new StatModifier(StatModifier.Type.bonus, 1)))));
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

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        Projectile projectile = new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView, (int)stats.getStatValue(WeaponStatsType.Pierce), stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),(int) stats.getStatValue(WeaponStatsType.Area), new ProjectileMovement() {
            boolean lockedOn = false;
            @Override
            public void update(long deltaTime, GameView gameView, Projectile projectile) {
                if(!lockedOn){
                    HashSet<Enemy> enemies = gameView.getEnemies();
                    double min = 1000;
                    Enemy closest = null;
                    for(Enemy enemy : enemies){
                        double dist = enemy.distance(player);
                        if(dist < min){
                            closest = enemy;
                            min =dist;
                        }
                    }
                    if(closest != null){
                        projectile.setVelX(projectile.getSpeed() * (closest.getPositionX() - player.getPositionX())/min);
                        projectile.setVelY(projectile.getSpeed() * (closest.getPositionY() - player.getPositionY())/min);
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
