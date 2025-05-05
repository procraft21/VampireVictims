package com.example.projectcyber.GameActivity.Weapons;

import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;

import java.util.HashMap;

public abstract class Weapon {
    WeaponStatsContainer stats;
    GameView gameView;

    protected long timeLeftInWindow;
    protected boolean isActive;

    protected long timeSinceLastShot;

    protected int amountShot;

    public Weapon(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        stats = new WeaponStatsContainer(startingStats, gameView.getPlayer());
        this.gameView = gameView;
    }

    public Weapon(){}

    public void update(long deltaTime){
        timeLeftInWindow -= deltaTime;
        if(timeLeftInWindow < 0){
            if(isActive){
                if(amountShot >= stats.getStatValue(WeaponStatsType.Amount)){
                    //turn off
                    isActive = false;
                    timeLeftInWindow = (long) (stats.getStatValue(WeaponStatsType.Cooldown));
                    amountShot = 0;
                }
            }else{
                isActive = true;
                timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Duration);
                timeSinceLastShot = (long) stats.getStatValue(WeaponStatsType.ProjectileInterval);
            }
        }
        timeSinceLastShot += deltaTime;
        if(isActive){
            if(timeSinceLastShot > stats.getStatValue(WeaponStatsType.ProjectileInterval) && amountShot < stats.getStatValue(WeaponStatsType.Amount)){
                gameView.addProjectile(createProjectile());
                timeSinceLastShot = 0;
            }

        }
    }

    public abstract Projectile createProjectile();

}
