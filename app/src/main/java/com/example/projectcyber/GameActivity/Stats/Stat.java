package com.example.projectcyber.GameActivity.Stats;

public class Stat<T> {
    private T statType;
    private double value;
    private double percentileBonus;
    private double rawBonus;

    public Stat(T statType, double defaultValue){
        this.statType = statType;
        this.value = defaultValue;
        percentileBonus = 0;
        rawBonus = 0;
    }

    public Stat(Stat<T> other) {
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

    public T getStatType() {
        return statType;
    }
}
