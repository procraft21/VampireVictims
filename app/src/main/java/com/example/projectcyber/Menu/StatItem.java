package com.example.projectcyber.Menu;

import android.util.Log;

import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;

public class StatItem {
    private String name;
    private int level;
    private int maxLevel;
    private int initialPrice;

    private final Stat<PlayerStatsType> basicStat;
    private final StatModifier modifier;

    private StatStoreList list;

    public StatItem(String name, int level, int maxLevel, int initialPrice, Stat stat, StatModifier modifier, StatStoreList list){
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.initialPrice = initialPrice;
        this.basicStat = stat;
        this.modifier = modifier;
        this.list = list;
    }

    public int getLevel() {
        return level;
    }

    public boolean setLevel(int level) {
        if(level > maxLevel) return false;
        list.totalBought -= this.level;
        list.totalBought += level;
        this.level = level;
        return true;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public boolean raiseLevel(){
        return setLevel(level+1);
    }

    public boolean canLevelUp(){
        return level<maxLevel;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice(){
        if(level == maxLevel) return -1;
        int basePrice = initialPrice*(1+level);
        int fees = list.totalBought == 0 ? 0 : (int)(20*Math.pow(1.1, list.totalBought));
        return basePrice + fees;
    }

    public PlayerStatsType getType(){
        return basicStat.getStatType();
    }

    public double getFinalValue(){
        Stat<PlayerStatsType> finalStat = new Stat<>(basicStat);
        for(int i = 0; i<level; i++){
            Log.d("stats", finalStat.getStatType().name() + " " +finalStat.getFinalValue());
            finalStat.applyModifier(modifier);
        }
        return finalStat.getFinalValue();
    }
}
