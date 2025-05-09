package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Gauntlets extends Item{
    public Gauntlets() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Might;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
    }
}
