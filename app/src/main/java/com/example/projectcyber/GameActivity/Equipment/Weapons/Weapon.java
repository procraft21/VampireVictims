package com.example.projectcyber.GameActivity.Equipment.Weapons;

import android.util.Log;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.LevelDesc;
import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Weapon extends Equipment {
    WeaponStatsContainer stats;
    GameView gameView;

    protected long timeLeftInWindow;
    protected boolean isActive;

    protected long timeSinceLastShot;

    protected int amountShot;


    ArrayList<HashSet<LevelUpModifier>> levelEffects;

    public Weapon(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        stats = new WeaponStatsContainer(startingStats, gameView);
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
            while(timeSinceLastShot > stats.getStatValue(WeaponStatsType.ProjectileInterval) && amountShot < stats.getStatValue(WeaponStatsType.Amount)){
                gameView.addProjectile(createProjectile());
                amountShot++;
                timeSinceLastShot -= (long) stats.getStatValue(WeaponStatsType.ProjectileInterval);
                Log.d("shot", amountShot + " " + stats.getStatValue(WeaponStatsType.Amount));
            }

        }
    }

    @Override
    public boolean raiseLevel(){
        if(level >= maxLevel) return false;
        level++;
        for(LevelUpModifier modifier : levelEffects.get(level-1))
            modifier.apply(stats);
        return true;
    }

    public abstract Projectile createProjectile();

    @Override
    protected String getNextLevelStatsDescription() {
        String desc = "";
        for(LevelUpModifier modifier : levelEffects.get(level)){
            desc += modifier.modifier.getDesc(modifier.statType) + "\n";
        }
        return desc;
    }


}
