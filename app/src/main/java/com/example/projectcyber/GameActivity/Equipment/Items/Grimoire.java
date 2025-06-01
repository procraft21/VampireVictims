package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class Grimoire extends Item{
    public Grimoire(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Grimoire";
        this.statType = PlayerStatsType.Cooldown;
        this.modifier = new StatModifier(StatModifier.Type.percentile, -8);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_griomoire);

    }
}
