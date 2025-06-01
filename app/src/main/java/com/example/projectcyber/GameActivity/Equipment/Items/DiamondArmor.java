package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class DiamondArmor extends Item{
    public DiamondArmor(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Diamond Armor";
        this.statType = PlayerStatsType.Armor;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_diamond_armor);
    }
}
