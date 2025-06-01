package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class Duplicator extends Item{
    public Duplicator(GameView gameView) {
        super();
        this.maxLevel = 2;
        this.name = "Duplicator";
        this.statType = PlayerStatsType.Amount;
        this.modifier = new StatModifier(StatModifier.Type.bonus, 1);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_duplicator);
    }
}
