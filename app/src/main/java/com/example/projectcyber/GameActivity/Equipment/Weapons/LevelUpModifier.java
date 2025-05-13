package com.example.projectcyber.GameActivity.Equipment.Weapons;

import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class LevelUpModifier {
    WeaponStatsType statType;
    StatModifier modifier;

    public LevelUpModifier(WeaponStatsType type, StatModifier modifier){
        this.statType = type;
        this.modifier = modifier;
    }

    public void apply(WeaponStatsContainer container){
        container.getStat(statType).applyModifier(modifier);
    }
}
