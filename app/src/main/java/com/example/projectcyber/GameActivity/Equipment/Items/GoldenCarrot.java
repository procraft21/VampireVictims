package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class GoldenCarrot extends Item{
    public GoldenCarrot(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Golden Carrot";
        this.statType = PlayerStatsType.Recovery;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 0.2);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_golden_carrot);

    }
}
