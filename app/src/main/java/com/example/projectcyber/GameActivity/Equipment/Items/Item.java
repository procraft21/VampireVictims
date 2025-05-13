package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public abstract class Item extends Equipment {


    StatModifier modifier;
    PlayerStatsType statType;

    public Item(){
        level = 0;
    }

    @Override
    public boolean raiseLevel(){
        if(level < maxLevel){
            level++;
            return true;
        }
        return false;
    }

    public StatModifier getModifier() {
        return modifier;
    }

    public double getAmount(){
        switch (modifier.getType()){
            case bonus:
                return modifier.getAmount()* level;
            case percentile:
                return 1+modifier.getAmount()* level /100;
        }
        return -1;
    }

    public PlayerStatsType getStatType(){
        return statType;
    }

    @Override
    protected String getNextLevelStatsDescription() {
        return modifier.getDesc(statType);
    }
}
