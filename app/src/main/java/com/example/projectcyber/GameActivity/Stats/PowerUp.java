package com.example.projectcyber.GameActivity.Stats;

public abstract class PowerUp {
    public enum Type{
        percentile, bonus
    }
    Type type;
    double amount;

    public Type getType() {
        return type;
    }
}
