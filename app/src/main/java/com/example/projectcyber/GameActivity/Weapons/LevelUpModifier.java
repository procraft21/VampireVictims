package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.google.android.gms.common.api.internal.IStatusCallback;

import java.util.HashSet;

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
