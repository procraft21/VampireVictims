package com.example.projectcyber.Menu;

import java.io.Serializable;


public class StatItem implements Serializable {
    public final static int MAX_LEVEL_PRICE = -1;

    private String name;
    private int level;
    private int maxLevel;
    private int initialPrice;

    private final Stat<PlayerStatsType> basicStat;
    private final StatModifier modifier;

    private StatStoreList list;

    /**
     * Constructor for StatItem.
     *
     * @param name          Name of the stat.
     * @param maxLevel      Maximum allowable level.
     * @param initialPrice  Base price for the stat upgrade.
     * @param stat          The underlying stat object.
     * @param modifier      Modifier applied per level.
     * @param list          Reference to the store list for calculating cost.
     */
    public StatItem(String name, int maxLevel, int initialPrice, Stat stat, StatModifier modifier, StatStoreList list){
        this.name = name;
        this.level = 0;
        this.maxLevel = maxLevel;
        this.initialPrice = initialPrice;
        this.basicStat = stat;
        this.modifier = modifier;
        this.list = list;
    }


    /**
     * Sets the level if it's within the allowed max limit and updates totalBought.
     *
     * @param level The new level.
     * @return True if level was set successfully, false otherwise.
     */
    public boolean setLevel(int level) {
        if(level > maxLevel) return false;
        list.setTotalBought(list.getTotalBought() - this.level);
        list.setTotalBought(list.getTotalBought() + level);
        this.level = level;
        return true;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    /**
     * Increases level by one if possible.
     *
     * @return True if level was increased, false otherwise.
     */
    public boolean raiseLevel(){
        return setLevel(level+1);
    }

    public boolean canLevelUp(){
        return level < maxLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Computes the current price of the next upgrade.
     *
     * @return The price or -1 if at max level.
     */
    public int getPrice(){
        if(level == maxLevel) return MAX_LEVEL_PRICE;
        int basePrice = initialPrice * (1 + level);
        int fees = list.getTotalBought() == 0 ? 0 : (int)(20 * Math.pow(1.1, list.getTotalBought()));
        return basePrice + fees;
    }

    /**
     * @return The type of stat this item represents.
     */
    public PlayerStatsType getType(){
        return basicStat.getStatType();
    }

    /**
     * Calculates the final stat value after applying all modifiers for the current level.
     *
     * @return Final stat value.
     */
    public double getFinalValue(){
        Stat<PlayerStatsType> finalStat = new Stat<>(basicStat);
        for(int i = 0; i < level; i++){
            finalStat.applyModifier(modifier);
        }
        return finalStat.getFinalValue();
    }
}
