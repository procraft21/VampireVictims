package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Electromagnet extends Item{
    public Electromagnet() {
        super();
        this.maxLevel = 5;
        this.name = "Electromagnet";
        this.statType = PlayerStatsType.Magnet;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
        this.initialDesc = modifier.getDesc(statType);
    }
}
