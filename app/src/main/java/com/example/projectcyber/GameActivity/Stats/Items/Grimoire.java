package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Grimoire extends Item{
    public Grimoire() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Cooldown;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 8);
    }
}
