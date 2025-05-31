package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

/**
 * Abstract class representing a stat-modifying item.
 * Items apply a {@link StatModifier} to a specific {@link PlayerStatsType} and can level up.
 */
public abstract class Item extends Equipment {

    /** The stat modifier applied by this item. */
    StatModifier modifier;

    /** The type of player stat that this item modifies. */
    PlayerStatsType statType;

    /**
     * Constructs an item with default level 0.
     */
    public Item(){
        level = 0;
    }

    /**
     * Attempts to increase the item's level by 1.
     *
     * @return true if level was successfully raised, false if already at max level
     */
    @Override
    public boolean raiseLevel(){
        if(level < maxLevel){
            level++;
            return true;
        }
        return false;
    }

    /**
     * Returns the stat modifier object associated with this item.
     *
     * @return the item's StatModifier
     */
    public StatModifier getModifier() {
        return modifier;
    }

    /**
     * Calculates the actual effect of the modifier based on current level.
     *
     * @return the effective modifier value, or -1 if modifier type is unrecognized
     */
    public double getAmount(){
        switch (modifier.getType()){
            case bonus:
                return modifier.getAmount() * level;
            case percentile:
                return 1 + modifier.getAmount() * level / 100;
        }
        return -1;
    }

    /**
     * Returns the stat type affected by this item.
     *
     * @return the PlayerStatsType this item modifies
     */
    public PlayerStatsType getStatType(){
        return statType;
    }

    /**
     * Provides a short description of the item's effect at the next level.
     *
     * @return a description string from the modifier
     */
    @Override
    protected String getNextLevelStatsDescription() {
        return modifier.getDesc(statType);
    }
}
