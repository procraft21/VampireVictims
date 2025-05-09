package com.example.projectcyber.GameActivity.Stats.Items;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;

public class DiamondArmor extends Item{
    public DiamondArmor() {
        super();
        this.currLevel = 1;
        this.maxLevel = 5;
        this.statType = PlayerStatsType.Armor;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
    }
}
