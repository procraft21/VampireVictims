package com.example.projectcyber.GameActivity.Equipment.Weapons;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.gameObjects.Player;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.FriendlyProjectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.ProjectileMovement;
import com.example.projectcyber.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class MysticOrbit extends Weapon{

    public MysticOrbit(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        super(startingStats, gameView);
    }

    public MysticOrbit(GameView gameView){
        this.gameView = gameView;

        equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.weapon_orbit);
        projectileBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_orbit);

        this.name = "Mystic Orbit";
        this.initialDesc = "Orbits around the player";

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 10.0);
        startingStats.put(WeaponStatsType.Speed, 600.0);
        startingStats.put(WeaponStatsType.Duration, 3000.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.Pierce, Double.POSITIVE_INFINITY);
        startingStats.put(WeaponStatsType.ProjectileInterval, 0.0);
        startingStats.put(WeaponStatsType.Cooldown, 3000.0);
        startingStats.put(WeaponStatsType.Area, 75.0);

        this.stats = new WeaponStatsContainer(startingStats, gameView);

        level = 0;
        maxLevel = 8;

        levelEffects = new ArrayList<>();
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Area, new StatModifier(StatModifier.Type.percentile, 25))
            ,new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 30)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Duration, new StatModifier(StatModifier.Type.bonus, 500)),
                new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Area, new StatModifier(StatModifier.Type.percentile, 25))
                ,new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 30)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Duration, new StatModifier(StatModifier.Type.bonus, 500)),
                new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 10)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));


        timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Cooldown);
        isActive = false;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        double angle = this.amountShot * 2*Math.PI / stats.getStatValue(WeaponStatsType.Amount);
        double radius = 300;
        return new FriendlyProjectile(player.getPositionX() + radius * Math.cos(angle), player.getPositionY() + radius * Math.sin(angle), gameView,
                50000, stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),(int) stats.getStatValue(WeaponStatsType.Area),projectileBitmap, new ProjectileMovement() {
            long timeAlive = 0;

            @Override
            public void update(long deltaTime, GameView gameView, Projectile projectile) {

                double startingTheta = Math.atan2(projectile.getPositionY() - player.getPositionY(), projectile.getPositionX() - player.getPositionX());

                double deltaTheta = projectile.getSpeed()*deltaTime/1000/radius;
                if(timeAlive == 0) Log.d("angle", deltaTheta + "");

                double newTheta = startingTheta + deltaTheta;

                double newX = player.getPositionX() + radius*Math.cos(newTheta);
                double newY = player.getPositionY() + radius*Math.sin(newTheta);

                projectile.setPosX(newX);
                projectile.setPosY(newY);
                projectile.setVelY(player.getVelY());
                projectile.setVelX(player.getVelX());
                timeAlive += deltaTime;
                if(timeAlive > stats.getStatValue(WeaponStatsType.Duration)){
                    projectile.destroy();
                }

            }
        });
    }
}
