package com.example.projectcyber.GameActivity.Equipment.Weapons;

import com.example.projectcyber.Menu.StatModifier;

public class LevelUpModifier {
    private WeaponStatsType statType;
    private StatModifier modifier;

    public LevelUpModifier(WeaponStatsType type, StatModifier modifier){
        this.setStatType(type);
        this.setModifier(modifier);
    }

    public void apply(WeaponStatsContainer container){
        container.getStat(statType).applyModifier(modifier);
    }

    public WeaponStatsType getStatType() {
        return statType;
    }

    public void setStatType(WeaponStatsType statType) {
        this.statType = statType;
    }

    public StatModifier getModifier() {
        return modifier;
    }

    public void setModifier(StatModifier modifier) {
        this.modifier = modifier;
    }
}
