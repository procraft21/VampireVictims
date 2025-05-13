package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class GoldenCarrot extends Item{
    public GoldenCarrot() {
        super();
        this.maxLevel = 5;
        this.name = "Golden Carrot";
        this.statType = PlayerStatsType.Recovery;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 0.2);
        this.initialDesc = modifier.getDesc(statType);
    }
}
