package com.example.projectcyber.GameActivity.Equipment.Weapons;

import android.graphics.BitmapFactory;

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
import java.util.Random;

public class ViolentStar extends Weapon{

    public ViolentStar(GameView gameView){
        this.gameView = gameView;

        equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_violent_star);
        projectileBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_violent_star);

        this.name = "Violent Star";
        this.initialDesc = "Bounces around in screen";

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 10.0);
        startingStats.put(WeaponStatsType.Speed, 500.0);
        startingStats.put(WeaponStatsType.Duration, 2250.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.Pierce, 50000.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 200.0);
        startingStats.put(WeaponStatsType.Cooldown, 3000.0);
        startingStats.put(WeaponStatsType.Area, 75.0);
        stats = new WeaponStatsContainer(startingStats, gameView);

        level = 0;
        maxLevel = 8;

        levelEffects = new ArrayList<>();
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 5))
                ,new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Duration, new StatModifier(StatModifier.Type.bonus, 300))
                ,new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 5)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 5))
                ,new LevelUpModifier(WeaponStatsType.Speed, new StatModifier(StatModifier.Type.percentile, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Duration, new StatModifier(StatModifier.Type.bonus, 300))
                ,new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 5)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Duration, new StatModifier(StatModifier.Type.bonus, 5000)))));

        for(int i = 0; i<level-1; i++){
            for(LevelUpModifier modifer : levelEffects.get(i))
                modifer.apply(stats);
        }


        timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Cooldown);
        isActive = false;
        timeSinceLastShot = 0;
        amountShot = 0;
    }

    @Override
    public Projectile createProjectile() {
        Player player = gameView.getPlayer();
        return new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView, (int)stats.getStatValue(WeaponStatsType.Pierce), stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),
                (int) stats.getStatValue(WeaponStatsType.Area), projectileBitmap,new ProjectileMovement(){

            boolean first = true;
            double angle;

            long timeAlive = 0;
            @Override
            public void update(long deltaTime, GameView gameView, Projectile projectile) {
                if(first) {
                    Random random = new Random();
                    angle = 2*Math.PI * random.nextDouble();
                    projectile.setVelX(projectile.getSpeed() * Math.cos(angle));
                    projectile.setVelY(- projectile.getSpeed() * Math.sin(angle));
                    first = false;
                }

                if(projectile.getPositionX() - player.getPositionX() > gameView.getScreenWidth()/2) {
                    projectile.setPosX(gameView.getScreenWidth()/2 + player.getPositionX());
                    projectile.setVelX(-projectile.getVelX());
                }
                if(projectile.getPositionX() - player.getPositionX() < -gameView.getScreenWidth()/2){
                    projectile.setPosX(-gameView.getScreenWidth()/2 + player.getPositionX());
                    projectile.setVelX(-projectile.getVelX());
                }
                if(projectile.getPositionY() - player.getPositionY() > gameView.getScreenHeight()/2){
                    projectile.setPosY(gameView.getScreenHeight()/2 + player.getPositionY());
                    projectile.setVelY(-projectile.getVelY());
                }
                if(projectile.getPositionY() - player.getPositionY() < -gameView.getScreenHeight()/2){
                    projectile.setPosY(gameView.getScreenHeight()/2 + player.getPositionY());
                    projectile.setVelY(-projectile.getVelY());
                }

                timeAlive += deltaTime;
                if(timeAlive > stats.getStatValue(WeaponStatsType.Duration)){
                    projectile.destroy();
                }

            }
        });
    }
}
