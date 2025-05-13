package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Duplicator extends Item{
    public Duplicator() {
        super();
        this.maxLevel = 2;
        this.name = "Duplicator";
        this.statType = PlayerStatsType.Amount;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
        this.initialDesc = modifier.getDesc(statType);
    }
}
