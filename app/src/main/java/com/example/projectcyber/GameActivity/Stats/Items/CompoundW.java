package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class CompoundW extends Item{
    public CompoundW() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.ProjectileSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
    }
}
