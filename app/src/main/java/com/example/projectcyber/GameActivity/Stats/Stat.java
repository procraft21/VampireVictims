package com.example.projectcyber.GameActivity.Stats;

import java.io.Serializable;

/**
 * Represents a generic stat with modifiers and final value computation.
 *
 * @param <T> A type that extends StatType, used to distinguish stat categories.
 */
public class Stat<T extends StatType> implements Serializable {
    private T statType;
    private double value;
    private double percentileBonus;
    private double rawBonus;

    /**
     * Constructs a new Stat with a base value and stat type.
     *
     * @param statType     The type of the stat (e.g., HP, Damage, Speed).
     * @param defaultValue The base (unmodified) value of the stat.
     */
    public Stat(T statType, double defaultValue){
        this.statType = statType;
        this.value = defaultValue;
        percentileBonus = 0;
        rawBonus = 0;
    }

    /**
     * Copy constructor to duplicate an existing stat instance.
     *
     * @param other The stat to copy.
     */
    public Stat(Stat<T> other) {
        this.statType = other.statType;
        this.value = other.value;
        this.percentileBonus = other.percentileBonus;
        this.rawBonus = other.rawBonus;
    }

    /**
     * Applies a modifier (either raw or percentile) to this stat.
     *
     * @param modifier The modifier to apply.
     */
    public void applyModifier(StatModifier modifier){
        switch (modifier.type){
            case percentile:
                percentileBonus += modifier.amount / 100;
                break;
            case bonus:
                rawBonus += modifier.amount;
                break;
        }
    }

    /**
     * Computes the final stat value after applying all modifiers.
     *
     * @return The final stat value.
     */
    public double getFinalValue(){
        return (value + rawBonus) * (1 + percentileBonus);
    }

    /**
     * Returns the stat type associated with this stat.
     *
     * @return The stat type.
     */
    public T getStatType() {
        return statType;
    }
}
