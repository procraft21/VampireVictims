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

public class ManaBlaster extends Weapon{

    public ManaBlaster(GameView gameView){
        this.gameView = gameView;

        this.name = "Mana Blaster";
        this.initialDesc = "Fires up and lets gravity do its thing";

        equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.weapon_mana_blaster);
        projectileBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.projectile_mana_blaster);

        HashMap<WeaponStatsType, Double> startingStats = new HashMap<>();

        startingStats.put(WeaponStatsType.Damage, 20.0);
        startingStats.put(WeaponStatsType.Speed, 1000.0);
        startingStats.put(WeaponStatsType.Duration, 0.0);
        startingStats.put(WeaponStatsType.Amount, 1.0);
        startingStats.put(WeaponStatsType.Pierce, 3.0);
        startingStats.put(WeaponStatsType.ProjectileInterval, 200.0);
        startingStats.put(WeaponStatsType.Cooldown, 4000.0);
        startingStats.put(WeaponStatsType.Area, 150.0);
        stats = new WeaponStatsContainer(startingStats, gameView);

        level = 0;
        maxLevel = 8;

        levelEffects = new ArrayList<>();
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Pierce, new StatModifier(StatModifier.Type.bonus, 2)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Amount, new StatModifier(StatModifier.Type.bonus, 1)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 20)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Pierce, new StatModifier(StatModifier.Type.bonus, 2)))));
        levelEffects.add(new HashSet<>(Arrays.asList(new LevelUpModifier(WeaponStatsType.Damage, new StatModifier(StatModifier.Type.bonus, 20)))));

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
        return new FriendlyProjectile(player.getPositionX(), player.getPositionY(), gameView,
                (int) stats.getStatValue(WeaponStatsType.Pierce), stats.getStatValue(WeaponStatsType.Damage), (int) stats.getStatValue(WeaponStatsType.Speed),
                (int) stats.getStatValue(WeaponStatsType.Area),projectileBitmap,new ProjectileMovement(amountShot) {
                    final double accelY = 1;
                    boolean first = true;
                    @Override
                    public void update(long deltaTime, GameView gameView, Projectile projectile) {
                        if(first){
                            Random rnd = new Random();
                            double angle = (rnd.nextInt(3)-1) * this.amountShot *Math.PI/12 + (2*rnd.nextDouble()-1)/3;
                            projectile.setVelX(-Math.sin(angle) * projectile.getSpeed());
                            projectile.setVelY(-Math.cos(angle) * projectile.getSpeed());
                            first = false;
                        }
                        projectile.setVelY(projectile.getVelY() + accelY * deltaTime);
                    }
                });
    }
}
