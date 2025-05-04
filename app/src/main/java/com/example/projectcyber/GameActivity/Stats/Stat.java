package com.example.projectcyber.GameActivity.Stats;

import java.util.ArrayList;

public class Stat {
    private StatType statType;
    private double value;
    private double percentileBonus;
    private double rawBonus;

    public Stat(StatType statType, double defaultValue){
        this.statType = statType;
        this.value = defaultValue;
        percentileBonus = 0;
        rawBonus = 0;
    }

    public Stat(Stat other) {
        this.statType = other.statType;
        this.value = other.value;
        this.percentileBonus = other.percentileBonus;;
        this.rawBonus = other.rawBonus;
    }

    public void applyModifier(StatModifier modifier){
        switch (modifier.type){
            case percentile:
                percentileBonus += modifier.amount/100;
                break;
            case bonus:
                rawBonus += modifier.amount;
                break;
        }
    }

    public double getFinalValue(){
        return (value+rawBonus)*(1+percentileBonus);
    }

    public StatType getStatType() {
        return statType;
    }
}
