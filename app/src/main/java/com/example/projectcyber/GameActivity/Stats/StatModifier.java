package com.example.projectcyber.GameActivity.Stats;

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

    //public applyTo()
}
