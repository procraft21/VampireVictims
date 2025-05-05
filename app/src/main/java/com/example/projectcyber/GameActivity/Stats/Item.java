package com.example.projectcyber.GameActivity.Stats;

import java.util.ArrayList;

public class Item {
    final ArrayList<StatModifier> levelEffect;
    PlayerStatsType playerStat;
    int currLevel;
    int maxLevel;

    public Item(PlayerStatsType playerStat, ArrayList<StatModifier> levelEffect, int currLevel){
        this.playerStat = playerStat;
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
