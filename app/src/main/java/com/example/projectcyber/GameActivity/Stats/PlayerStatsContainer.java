package com.example.projectcyber.GameActivity.Stats;

import android.util.Log;

import java.util.HashMap;

public class PlayerStatsContainer {
    HashMap<PlayerStatsType, Stat<PlayerStatsType>> stats;

    public PlayerStatsContainer(HashMap<PlayerStatsType, Double> startingStats){
        stats = new HashMap<>();
        stats.put(PlayerStatsType.MaxHp, new Stat<>(PlayerStatsType.MaxHp, startingStats.get(PlayerStatsType.MaxHp)));
        stats.put(PlayerStatsType.MoveSpd, new Stat<>(PlayerStatsType.MoveSpd, startingStats.get(PlayerStatsType.MoveSpd)));
        stats.put(PlayerStatsType.Duration, new Stat<>(PlayerStatsType.Duration, startingStats.get(PlayerStatsType.Duration)));
        stats.put(PlayerStatsType.Might, new Stat<>(PlayerStatsType.Might, startingStats.get(PlayerStatsType.Might)));
        stats.put(PlayerStatsType.Amount, new Stat<>(PlayerStatsType.Amount, startingStats.get(PlayerStatsType.Amount)));
        stats.put(PlayerStatsType.Armor, new Stat<>(PlayerStatsType.Armor, startingStats.get(PlayerStatsType.Armor)));
        stats.put(PlayerStatsType.Recovery, new Stat<>(PlayerStatsType.Recovery, startingStats.get(PlayerStatsType.Recovery)));
        stats.put(PlayerStatsType.ProjectileSpd, new Stat<>(PlayerStatsType.ProjectileSpd, startingStats.get(PlayerStatsType.ProjectileSpd)));
        stats.put(PlayerStatsType.Cooldown, new Stat<>(PlayerStatsType.Cooldown, startingStats.get(PlayerStatsType.Cooldown)));
        stats.put(PlayerStatsType.Magnet, new Stat<>(PlayerStatsType.Magnet, startingStats.get(PlayerStatsType.Magnet)));
    }

    public Stat getStat(PlayerStatsType type){
        return stats.get(type);
    }
}
