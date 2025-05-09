package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class CoffeeTorus extends Item{
    public CoffeeTorus() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.MaxHp;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
    }
}
