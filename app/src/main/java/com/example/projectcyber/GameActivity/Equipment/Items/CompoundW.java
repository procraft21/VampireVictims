package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class CompoundW extends Item{
    public CompoundW(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Compound W";
        this.statType = PlayerStatsType.ProjectileSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_compound_w);
    }
}
