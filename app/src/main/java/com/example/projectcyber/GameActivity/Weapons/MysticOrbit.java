package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;

import java.util.HashMap;
import java.util.HashSet;


public class MysticOrbit extends Weapon{

    public MysticOrbit(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        super(startingStats, gameView);
    }

    public MysticOrbit(GameView gameView){
        this.gameView = gameView;

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 10.0);
        startingStats.put(WeaponStatsType.Speed, 500.0);
        startingStats.put(WeaponStatsType.Duration, 3000.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.Pierce, Double.POSITIVE_INFINITY);
        startingStats.put(WeaponStatsType.ProjectileInterval, 0.0);
        startingStats.put(WeaponStatsType.Cooldown, 3000.0);
        startingStats.put(WeaponStatsType.Area, 75.0);


        this.stats = new WeaponStatsContainer(startingStats, gameView);

        timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Duration);
        isActive = true;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        double angle = amountShot * 2*Math.PI / stats.getStatValue(WeaponStatsType.Amount);
        double radius = 300;
        return new FriendlyProjectile(player.getPositionX() + radius * Math.cos(angle), player.getPositionY() + radius * Math.sin(angle), gameView,
                50000, stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),(int) stats.getStatValue(WeaponStatsType.Area), new ProjectileMovement() {
            long timeAlive = 0;

            @Override
            public void update(long deltaTime, GameView gameView, Projectile projectile) {
                projectile.setVelX(player.getVelX() + projectile.getSpeed() * ( -(projectile.getPositionY() - player.getPositionY())/radius));
                projectile.setVelY(player.getVelY() + projectile.getSpeed() * ( (projectile.getPositionX() - player.getPositionX())/radius));

                timeAlive += deltaTime;
                if(timeAlive > stats.getStatValue(WeaponStatsType.Duration)){
                    projectile.destroy();
                }

            }
        });
    }
}
