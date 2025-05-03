package com.example.projectcyber.GameActivity.Stats;

import java.util.HashMap;

public class StatsContainer {
    HashMap<StatType, Stat> stats;
    public StatsContainer(){
        stats = new HashMap<>();
        stats.put(StatType.MaxHp, new Stat(StatType.MaxHp, 100));
        stats.put(StatType.MoveSpd, new Stat(StatType.MoveSpd, 200));
    }
}
