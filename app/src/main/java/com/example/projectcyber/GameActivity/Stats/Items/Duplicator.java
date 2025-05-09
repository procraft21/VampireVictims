package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class Duplicator extends Item{
    public Duplicator() {
        super();
        this.currLevel = 1;
        this.maxLevel = 2;
        this.statType = PlayerStatsType.Amount;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
    }
}
