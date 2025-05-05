package com.example.projectcyber.GameActivity.Stats;

import java.util.HashMap;

public class PlayerStatsContainer {
    HashMap<PlayerStatsType, Stat<PlayerStatsType>> stats;

    public PlayerStatsContainer(HashMap<PlayerStatsType, Double> startingStats){
        stats = new HashMap<>();
        stats.put(PlayerStatsType.MaxHp, new Stat<>(PlayerStatsType.MaxHp, startingStats.get(PlayerStatsType.MaxHp)));
        stats.put(PlayerStatsType.MoveSpd, new Stat<>(PlayerStatsType.MoveSpd, startingStats.get(PlayerStatsType.MoveSpd)));
        stats.put(PlayerStatsType.Duration, new Stat<>(PlayerStatsType.Duration, startingStats.get(PlayerStatsType.Duration)));
        stats.put(PlayerStatsType.Might, new Stat<>(PlayerStatsType.Might, startingStats.get(PlayerStatsType.Might)));
    }

    public Stat getStat(PlayerStatsType type){
        return stats.get(type);
    }
}
