package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Gauntlets extends Item{
    public Gauntlets() {
        super();
        this.maxLevel = 5;
        this.name = "Gauntlets";
        this.statType = PlayerStatsType.Might;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);
    }
}
