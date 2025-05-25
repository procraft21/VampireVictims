package com.example.projectcyber.GameActivity.Stats;

import com.example.projectcyber.GameActivity.Equipment.Weapons.WeaponStatsType;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.io.Serializable;

public class StatModifier implements Serializable {

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

    public String getDesc(StatType statType){
        String name = statType.getName();

        if(amount > 0){
            switch(type){
                case percentile:
                    return "Raises " + name + " by " + amount + "%";
                case bonus:
                    return "Raises " + name + " by " + amount;
            }
        }else{
            switch(type){
                case percentile:
                    return "Lowers " + name + " by " + -amount + "%";
                case bonus:
                    return "Lowers " + name + " by " + -amount;
            }
        }

        return null;
    }



    public double getAmount() {
        return amount;
    }
}
