package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.R;

public class CoffeeDonut extends Item{
    public CoffeeDonut(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Coffee Donut";
        this.statType = PlayerStatsType.MaxHp;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_coffee_donut);

    }
}
