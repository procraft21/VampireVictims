package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

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
        stats = new WeaponStatsContainer(startingStats, gameView);

        timeLeftInWindow = 0;
        isActive = true;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        Projectile projectile = new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView, 1, stats.getStatValue(WeaponStatsType.Damage), 500, new ProjectileMovement() {
            boolean lockedOn = false;
            @Override
            public void update(double deltaTime, GameView gameView, Projectile projectile) {
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
                        projectile.setVelX(stats.getStatValue (WeaponStatsType.Speed) * (closest.getPositionX() - player.getPositionX())/min);
                        projectile.setVelY(stats.getStatValue (WeaponStatsType.Speed) * (closest.getPositionY() - player.getPositionY())/min);
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
