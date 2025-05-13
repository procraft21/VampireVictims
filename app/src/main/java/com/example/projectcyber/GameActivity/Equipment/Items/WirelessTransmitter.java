package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class WirelessTransmitter extends Item{
    public WirelessTransmitter() {
        super();
        this.maxLevel = 5;
        this.name = "Wireless Transmitter";
        this.statType = PlayerStatsType.Duration;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);
    }
}
