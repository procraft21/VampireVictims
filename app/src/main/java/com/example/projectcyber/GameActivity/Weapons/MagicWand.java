package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

import java.util.ArrayList;
import java.util.HashMap;

public class MagicWand extends Weapon{

    public MagicWand(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        super(startingStats, gameView);
    }

    public MagicWand( GameView gameView) {
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();
        startingStats.put(WeaponStatsType.Duration, Double.POSITIVE_INFINITY);

        stats = new WeaponStatsContainer(startingStats, gameView.getPlayer());
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        Projectile projectile = new Projectile(player.getPositionX(), player.getPositionY(), gameView, 1, stats.getStatValue(WeaponStatsType.Damage), 500, new ProjectileMovement() {
            boolean lockedOn = false;
            @Override
            public void update(double deltaTime, GameView gameView, Projectile projectile) {
                if(!lockedOn){
                    ArrayList<Enemy> enemies = gameView.getEnemies();
                    double min = 1000;
                    Enemy closest = null;
                    for(Enemy enemy : enemies){
                        double dist = enemy.distance(player);
                        if(dist < min){
                            closest = enemy;
                            min =dist;
                        }
                    }
                    if(closest != null)
                        projectile.setVelX((closest.getPositionX() - player.getPositionX())/min);
                }
            }
        });
        return projectile;
    }
}
