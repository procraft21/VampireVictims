package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class CoffeeTorus extends Item{
    public CoffeeTorus() {
        super();
        this.maxLevel = 5;
        this.name = "Coffee Torus";
        this.statType = PlayerStatsType.MaxHp;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
        this.initialDesc = modifier.getDesc(statType);
    }
}
