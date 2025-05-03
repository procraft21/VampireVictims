package com.example.projectcyber.GameActivity.Stats;

import java.util.ArrayList;

public class Stat {
    StatType statType;
    double value;
    double percentileBonus;
    double rawBonus;

    public Stat(StatType statType, double defaultValue){
        this.statType = statType;
        this.value = defaultValue;
        percentileBonus = 0;
        rawBonus = 0;
    }

    public void applyModifier(StatModifier modifier){
        switch (modifier.type){
            case percentile:
                percentileBonus += modifier.amount;
                break;
            case bonus:
                rawBonus += modifier.amount;
                break;
        }
    }

    public double getFinalValue(){
        return (value+rawBonus)*(1+percentileBonus);
    }

}
