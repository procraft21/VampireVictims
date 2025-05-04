package com.example.projectcyber.GameActivity.Stats;

import java.util.HashMap;

public class StatsContainer {
    HashMap<StatType, Stat> stats;

    public StatsContainer(HashMap<StatType, Double> startingStats){
        stats = new HashMap<>();
        stats.put(StatType.MaxHp, new Stat(StatType.MaxHp, startingStats.get(StatType.MaxHp)));
        stats.put(StatType.MoveSpd, new Stat(StatType.MoveSpd, startingStats.get(StatType.MoveSpd)));
    }

    public Stat getStat(StatType type){
        return stats.get(type);
    }
}
