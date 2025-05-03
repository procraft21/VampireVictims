package com.example.projectcyber.GameActivity.Stats;

import java.util.ArrayList;

public class Item {
    final ArrayList<StatModifier> levelEffect;
    StatType statType;
    int currLevel;
    int maxLevel;

    public Item(StatType statType, ArrayList<StatModifier> levelEffect, int currLevel){
        this.statType = statType;
        this.levelEffect = levelEffect;
        this.maxLevel = levelEffect.size();
        this.currLevel = currLevel;
    }

    public StatModifier raiseLevel(){
        if(currLevel < maxLevel){
            currLevel++;
            return levelEffect.get(currLevel);
        }
        return null;
    }


}
