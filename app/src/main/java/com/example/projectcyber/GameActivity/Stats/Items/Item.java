package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public abstract class Item {


    int currLevel;
    int maxLevel;

    StatModifier modifier;
    PlayerStatsType statType;

    public Item(){
    }

    public void raiseLevel(){
        if(currLevel < maxLevel){
            currLevel++;
        }
    }

    public StatModifier getModifier() {
        return modifier;
    }

    public double getAmount(){
        switch (modifier.getType()){
            case bonus:
                return modifier.getAmount()*currLevel;
            case percentile:
                return 1+modifier.getAmount()*currLevel/100;
        }
        return -1;
    }

    public PlayerStatsType getStatType(){
        return statType;
    }
}
