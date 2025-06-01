package com.example.projectcyber.GameActivity.Equipment.Weapons;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.Stat;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.util.HashMap;

public class WeaponStatsContainer {

    /** Reference to the game environment. Used to access player stats. */
    GameView gameView;

    /** Maps weapon stats to corresponding player stats that can modify them. */
    static HashMap<WeaponStatsType, PlayerStatsType> modifierTable;

    /** Maps weapon stats to the type of modification they receive (bonus or percentile). */
    static HashMap<WeaponStatsType, StatModifier.Type> typeTable;

    /** Holds the base stats for this weapon. */
    HashMap<WeaponStatsType, Stat<WeaponStatsType>> stats;

    /**
     * Constructs a new WeaponStatsContainer with the given base stat values.
     *
     * @param startingStats initial values for all relevant weapon stats
     * @param gameView      reference to the current GameView instance
     */
    public WeaponStatsContainer(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        if(modifierTable == null){
            setModifierTable();
        }
        if(typeTable == null){
            setTypeTable();
        }
        this.gameView = gameView;
        stats = new HashMap<>();
        stats.put(WeaponStatsType.Duration, new Stat<>(WeaponStatsType.Duration, startingStats.get(WeaponStatsType.Duration)));
        stats.put(WeaponStatsType.Damage, new Stat<>(WeaponStatsType.Damage, startingStats.get(WeaponStatsType.Damage)));
        stats.put(WeaponStatsType.Cooldown, new Stat<>(WeaponStatsType.Cooldown, startingStats.get(WeaponStatsType.Cooldown)));
        stats.put(WeaponStatsType.Speed, new Stat<>(WeaponStatsType.Speed, startingStats.get(WeaponStatsType.Speed)));
        stats.put(WeaponStatsType.Amount, new Stat<>(WeaponStatsType.Amount, startingStats.get(WeaponStatsType.Amount)));
        stats.put(WeaponStatsType.Pierce, new Stat<>(WeaponStatsType.Pierce, startingStats.get(WeaponStatsType.Pierce)));
        stats.put(WeaponStatsType.ProjectileInterval, new Stat<>(WeaponStatsType.ProjectileInterval, startingStats.get(WeaponStatsType.ProjectileInterval)));
        stats.put(WeaponStatsType.Area, new Stat<>(WeaponStatsType.Area, startingStats.get(WeaponStatsType.Area)));
    }

    /**
     * Initializes the modifierTable, which links weapon stats to relevant player stats.
     */
    private void setModifierTable(){
        modifierTable = new HashMap<>();
        modifierTable.put(WeaponStatsType.Duration, PlayerStatsType.Duration);
        modifierTable.put(WeaponStatsType.Damage, PlayerStatsType.Might);
        modifierTable.put(WeaponStatsType.Amount, PlayerStatsType.Amount);
        modifierTable.put(WeaponStatsType.Cooldown, PlayerStatsType.Cooldown);
        modifierTable.put(WeaponStatsType.Speed, PlayerStatsType.ProjectileSpd);
    }

    /**
     * Initializes the typeTable, which specifies how each weapon stat is modified (percentile or flat bonus).
     */
    private void setTypeTable(){
        typeTable = new HashMap<>();
        typeTable.put(WeaponStatsType.Duration, StatModifier.Type.percentile);
        typeTable.put(WeaponStatsType.Damage, StatModifier.Type.percentile);
        typeTable.put(WeaponStatsType.Amount, StatModifier.Type.bonus);
        typeTable.put(WeaponStatsType.Cooldown, StatModifier.Type.percentile);
        typeTable.put(WeaponStatsType.Speed, StatModifier.Type.percentile);
    }

    /**
     * Calculates the final value of a given weapon stat, applying player modifiers if applicable.
     *
     * @param type the weapon stat to compute
     * @return the final value after applying player-based modifiers
     */
    public double getStatValue(WeaponStatsType type){
        Player player = gameView.getPlayer();
        if(modifierTable.get(type) != null && player != null){
            switch (typeTable.get(type)){
                case percentile:
                    return stats.get(type).getFinalValue() * player.getStatValue(modifierTable.get(type));
                case bonus:
                    return stats.get(type).getFinalValue() + player.getStatValue(modifierTable.get(type));
            }
        }
        return stats.get(type).getFinalValue();
    }

    /**
     * Returns the raw Stat object for a specific weapon stat type.
     *
     * @param type the type of weapon stat to retrieve
     * @return the Stat object
     */
    public Stat<WeaponStatsType> getStat(WeaponStatsType type){
        return stats.get(type);
    }
}
