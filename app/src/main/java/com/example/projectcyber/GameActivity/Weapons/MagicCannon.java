package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MagicCannon extends Weapon{
    public MagicCannon(GameView gameView) {
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Damage, 20.0);
        startingStats.put(WeaponStatsType.Cooldown, 3000.0);
        startingStats.put(WeaponStatsType.Speed, 300.0);
        startingStats.put(WeaponStatsType.Pierce, 1.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 150.0);
        startingStats.put(WeaponStatsType.Area, 100.0);


        stats = new WeaponStatsContainer(startingStats, gameView);

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
                    Enemy randomEnemy = null;
                    int size = enemies.size();
                    if(size > 0){
                        int item = new Random().nextInt(size);
                        int i = 0;
                        for(Enemy enemy : enemies){
                            if(i == item){
                                randomEnemy = enemy;
                            }
                            i++;
                        }
                    }

                    double dist = randomEnemy.distance(player);
                    if(randomEnemy != null){
                        projectile.setVelX(projectile.getSpeed() * (randomEnemy.getPositionX() - player.getPositionX())/dist);
                        projectile.setVelY(projectile.getSpeed() * (randomEnemy.getPositionY() - player.getPositionY())/dist);
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
