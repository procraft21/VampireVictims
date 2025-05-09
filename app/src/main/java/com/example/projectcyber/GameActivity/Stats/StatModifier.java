package com.example.projectcyber.GameActivity.Stats;

import com.example.projectcyber.GameActivity.Weapons.WeaponStatsType;

public class StatModifier {

    public enum Type{
        percentile, bonus
    }

    Type type;
    double amount;

    public StatModifier(Type type, double amount){

        this.type = type;
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public String getDesc(WeaponStatsType statType){
        switch(type){
            case percentile:
                return "Raises " + statType.name() + " by " + amount + "%";
            case bonus:
                return "Raises " + statType.name() + " by " + amount;
        }
        return null;
    }

    public double getAmount() {
        return amount;
    }
}
