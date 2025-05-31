package com.example.projectcyber.GameActivity.Stats;

import com.example.projectcyber.GameActivity.Equipment.Weapons.WeaponStatsType;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.io.Serializable;

/**
 * Represents a modifier that can alter a stat by a flat amount or a percentage.
 */
public class StatModifier implements Serializable {

    /**
     * Defines the type of stat modification:
     * - percentile: A percentage-based increase or decrease.
     * - bonus: A raw value increase or decrease.
     */
    public enum Type {
        percentile, bonus
    }

    Type type;
    double amount;

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

    /**
     * Gets the type of this modifier.
     *
     * @return The modifier type (percentile or bonus).
     */
    public Type getType() {
        return type;
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

    /**
     * Returns the numeric value of the modifier.
     *
     * @return The amount of the modifier.
     */
    public double getAmount() {
        return amount;
    }
}
