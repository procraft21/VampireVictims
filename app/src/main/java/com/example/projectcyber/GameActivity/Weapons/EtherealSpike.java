package com.example.projectcyber.GameActivity.Weapons;

import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;
import com.example.projectcyber.R;

import java.util.HashMap;
import java.util.Random;

public class EtherealSpike extends Weapon{

    public EtherealSpike(GameView gameView){
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 20.0);
        startingStats.put(WeaponStatsType.Speed, 750.0);
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Amount, 3.0);
        startingStats.put(WeaponStatsType.Pierce, 1.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 200.0);
        startingStats.put(WeaponStatsType.Cooldown, 2000.0);
        startingStats.put(WeaponStatsType.Area, 40.0);

        stats = new WeaponStatsContainer(startingStats, gameView);
        timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Cooldown);
        isActive = false;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Random rnd = new Random();
        Player player = gameView.getPlayer();
        return new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView, (int) stats.getStatValue(WeaponStatsType.Pierce),
                stats.getStatValue(WeaponStatsType.Damage), (int)stats.getStatValue(WeaponStatsType.Speed) + rnd.nextInt(200) + 100,(int) stats.getStatValue(WeaponStatsType.Area),
                new ProjectileMovement() {
                    double angle = 2*Math.PI*rnd.nextDouble();
                    long time = rnd.nextInt(301) +100;

                    double accelX = 0;
                    double accelY = 0;
                    boolean first = true;
                    @Override
                    public void update(long deltaTime, GameView gameView, Projectile projectile) {
                        if (first) {

                            double speed = projectile.getSpeed();
                            double vx0 = speed * Math.cos(angle);
                            double vy0 = -speed * Math.sin(angle);  // Y is negative for upward

                            projectile.setVelX(vx0);
                            projectile.setVelY(vy0);

                            first = false;
                        }

                        time -= deltaTime;

                        if(time <= 0){
                            accelY = 0;
                            accelX = 0;
                            projectile.setVelY(0);
                            projectile.setVelX(0);
                        }
                    }
                });
    }
}
