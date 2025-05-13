package com.example.projectcyber.GameActivity.Equipment.Items;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class DiamondArmor extends Item{
    public DiamondArmor() {
        super();
        this.maxLevel = 5;
        this.name = "Diamond Armor";
        this.statType = PlayerStatsType.Armor;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
        this.initialDesc = modifier.getDesc(statType);
    }
}
