package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class GreenBull extends Item{
    public GreenBull() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.MoveSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
    }
}
