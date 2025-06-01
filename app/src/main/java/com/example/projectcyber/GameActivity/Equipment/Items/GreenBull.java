package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.Menu.PlayerStatsType;
import com.example.projectcyber.Menu.StatModifier;
import com.example.projectcyber.R;

public class GreenBull extends Item{
    public GreenBull(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "GreenBull";
        this.statType = PlayerStatsType.MoveSpd;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_green_bull);

    }
}
