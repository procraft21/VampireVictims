package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class ElectroMagnet extends Item{
    public ElectroMagnet() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Magnet;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
    }
}
