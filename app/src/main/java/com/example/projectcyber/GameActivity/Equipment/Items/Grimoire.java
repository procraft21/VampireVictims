package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Grimoire extends Item{
    public Grimoire() {
        super();
        this.maxLevel = 5;
        this.name = "Grimoire";
        this.statType = PlayerStatsType.Cooldown;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 8);
        this.initialDesc = modifier.getDesc(statType);
    }
}
