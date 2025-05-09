package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class WirelessTransmiter extends Item{
    public WirelessTransmiter() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Duration;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
    }
}
