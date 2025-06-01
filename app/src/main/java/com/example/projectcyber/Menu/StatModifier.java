package com.example.projectcyber.Menu;

import java.io.Serializable;


public class StatModifier implements Serializable {

    /**
     * Defines the type of stat modification:
     * - percentile: A percentage-based increase or decrease.
     * - bonus: A raw value increase or decrease.
     */
    public enum Type {
        percentile, bonus
    }

    private Type type;
    private double amount;

    /**
     * Constructs a StatModifier with a given type and amount.
     *
     * @param type   The type of modification (percentile or bonus).
     * @param amount The value of the modification (positive or negative).
     */
    public StatModifier(Type type, double amount) {
        this.type = type;
        this.amount = amount;
    }


    //-------------------- getters ---------------------------------

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    /**
     * Returns a descriptive string explaining the effect of the modifier on a given stat.
     *
     * @param statType The stat type being modified.
     * @return A user-friendly description of the modifier.
     */
    public String getDesc(StatType statType) {
        String name = statType.getName();

        if (amount > 0) {
            switch (type) {
                case percentile:
                    return "Raises " + name + " by " + amount + "%";
                case bonus:
                    return "Raises " + name + " by " + amount;
            }
        } else {
            switch (type) {
                case percentile:
                    return "Lowers " + name + " by " + -amount + "%";
                case bonus:
                    return "Lowers " + name + " by " + -amount;
            }
        }

        return null;
    }

}
