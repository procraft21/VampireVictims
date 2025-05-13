package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class CompoundW extends Item{
    public CompoundW() {
        super();
        this.maxLevel = 5;
        this.name = "Compound W";
        this.statType = PlayerStatsType.ProjectileSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);
    }
}
