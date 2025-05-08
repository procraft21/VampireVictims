package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Pickups.Pickup;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

import java.util.HashMap;
import java.util.Random;

public class ManaBlaster extends Weapon{

    public ManaBlaster(GameView gameView){
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 20.0);
        startingStats.put(WeaponStatsType.Speed, 300.0);
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.Pierce, 3.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 200.0);
        startingStats.put(WeaponStatsType.Cooldown, 4000.0);
        startingStats.put(WeaponStatsType.Area, 150.0);


        stats = new WeaponStatsContainer(startingStats, gameView);
        timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Cooldown);
        isActive = false;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        return new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView,
                (int) stats.getStatValue(WeaponStatsType.Pierce), stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),
                (int) stats.getStatValue(WeaponStatsType.Area),new ProjectileMovement() {
                    final double accelY = 10;
                    boolean first = true;
                    @Override
                    public void update(long deltaTime, GameView gameView, Projectile projectile) {
                        if(first){
                            Random rnd = new Random();
                            double angle = (rnd.nextInt(3)-1) * (amountShot-1) *Math.PI/12 + 2*rnd.nextDouble()-1;
                            projectile.setVelX(-Math.sin(angle) * projectile.getSpeed());
                            projectile.setVelY(-Math.cos(angle) * projectile.getSpeed());
                            first = false;
                        }
                        projectile.setVelY(projectile.getVelY() + accelY * deltaTime);
                    }
                });
    }
}
