package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class GoldenCarrot extends Item{
    public GoldenCarrot() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Recovery;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 0.2);
    }
}
