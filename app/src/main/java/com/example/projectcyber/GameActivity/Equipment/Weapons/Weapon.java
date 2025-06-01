package com.example.projectcyber.GameActivity.Equipment.Weapons;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.gameObjects.Projectile.Projectile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public abstract class Weapon extends Equipment {

    /** Holds all stat values for this weapon. */
    WeaponStatsContainer stats;

    /** Reference to the game environment. */
    GameView gameView;

    /** Time left in the current active or cooldown window. */
    protected long timeLeftInWindow;

    /** Whether the weapon is currently active (firing projectiles). */
    protected boolean isActive;

    /** Time since the last projectile was fired. */
    protected long timeSinceLastShot;

    /** Number of projectiles shot during the current active window. */
    protected int amountShot;

    /** The bitmap used for the projectile's visual representation. */
    protected Bitmap projectileBitmap;

    /** Modifiers applied at each level of the weapon. */
    ArrayList<HashSet<LevelUpModifier>> levelEffects;

    /**
     * Constructs a weapon with initial stats and a reference to the game.
     *
     * @param startingStats initial stat values
     * @param gameView reference to the GameView context
     */
    public Weapon(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        stats = new WeaponStatsContainer(startingStats, gameView);
        this.gameView = gameView;
    }

    /** Default constructor */
    public Weapon(){}

    /**
     * Updates the internal state of the weapon based on time passed.
     * Manages cooldowns, durations, and projectile firing.
     *
     * @param deltaTime the time since the last update in milliseconds
     */
    public void update(long deltaTime){
        timeLeftInWindow -= deltaTime;
        if(timeLeftInWindow < 0){
            if(isActive){
                if(amountShot >= stats.getStatValue(WeaponStatsType.Amount)){
                    // Transition to cooldown state
                    isActive = false;
                    timeLeftInWindow = (long) (stats.getStatValue(WeaponStatsType.Cooldown));
                    amountShot = 0;
                }
            } else {
                // Transition to active firing state
                isActive = true;
                timeLeftInWindow = (long) stats.getStatValue(WeaponStatsType.Duration);
                timeSinceLastShot = (long) stats.getStatValue(WeaponStatsType.ProjectileInterval);
            }
        }

        timeSinceLastShot += deltaTime;

        if(isActive){
            while(timeSinceLastShot > stats.getStatValue(WeaponStatsType.ProjectileInterval) &&
                    amountShot < stats.getStatValue(WeaponStatsType.Amount)){
                gameView.addProjectile(createProjectile());
                amountShot++;
                timeSinceLastShot -= (long) stats.getStatValue(WeaponStatsType.ProjectileInterval);
                Log.d("shot", amountShot + " " + stats.getStatValue(WeaponStatsType.Amount));
            }
        }
    }

    /**
     * Attempts to raise the weapon's level and apply the corresponding stat modifiers.
     *
     * @return true if the level was raised, false if already at max
     */
    @Override
    public boolean raiseLevel(){
        if(level >= maxLevel) return false;
        level++;
        if(level == 1) return true;
        for(LevelUpModifier modifier : levelEffects.get(level - 2))
            modifier.apply(stats);
        return true;
    }

    /**
     * Creates and returns a new projectile associated with this weapon.
     *
     * @return the projectile object
     */
    public abstract Projectile createProjectile();

    /**
     * Provides a description of stat changes that will occur at the next level.
     *
     * @return formatted string describing next level's stat changes
     */
    @Override
    protected String getNextLevelStatsDescription() {
        String desc = "";
        for(LevelUpModifier modifier : levelEffects.get(level - 1)){
            desc += modifier.getModifier().getDesc(modifier.getStatType()) + "\n";
        }
        return desc;
    }

}
