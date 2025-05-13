package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class GreenBull extends Item{
    public GreenBull() {
        super();
        this.maxLevel = 5;
        this.name = "GreenBull";
        this.statType = PlayerStatsType.MoveSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);
    }
}
