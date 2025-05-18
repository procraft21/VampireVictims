package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.R;

public class Electromagnet extends Item{
    public Electromagnet(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Electromagnet";
        this.statType = PlayerStatsType.Magnet;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 20);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_electromagnet);

    }
}
