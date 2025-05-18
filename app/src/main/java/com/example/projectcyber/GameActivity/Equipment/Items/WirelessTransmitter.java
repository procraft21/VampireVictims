package com.example.projectcyber.GameActivity.Equipment.Items;

import android.graphics.BitmapFactory;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.R;

public class WirelessTransmitter extends Item{
    public WirelessTransmitter(GameView gameView) {
        super();
        this.maxLevel = 5;
        this.name = "Wireless Transmitter";
        this.statType = PlayerStatsType.Duration;
        this.modifier = new StatModifier(StatModifier.Type.percentile, 10);
        this.initialDesc = modifier.getDesc(statType);

        this.equipmentBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.item_wireless_transmitter);

    }
}
