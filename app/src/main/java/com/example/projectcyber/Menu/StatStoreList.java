package com.example.projectcyber.Menu;

import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;

import java.util.ArrayList;
import java.util.HashMap;

public class StatStoreList {

    private HashMap<PlayerStatsType, StatItem> stats;
    protected int totalBought;

    public StatStoreList(){
        totalBought = 0;
        stats = new HashMap<>();
        stats.put(PlayerStatsType.MaxHp,new StatItem("Max HP", 0, 3, 200,
                new Stat<>(PlayerStatsType.MaxHp, 100), new StatModifier(StatModifier.Type.percentile, 10), this));
        stats.put(PlayerStatsType.MoveSpd, new StatItem("Movement Speed", 0, 2, 300,
                new Stat<>(PlayerStatsType.MoveSpd, 200), new StatModifier(StatModifier.Type.percentile, 15),this));
        stats.put(PlayerStatsType.Duration, new StatItem("Duration", 0, 2, 300,
                new Stat<>(PlayerStatsType.Duration, 1.0), new StatModifier(StatModifier.Type.percentile, 15),this));
        stats.put(PlayerStatsType.Might, new StatItem("Might",0,5,200,
                new Stat<>(PlayerStatsType.Might, 1), new StatModifier(StatModifier.Type.percentile, 5), this));
    }

    public ArrayList<StatItem> getList(){
        return new ArrayList<>(stats.values());

    }

    public int getMaxHpLvl() {
        return stats.get(PlayerStatsType.MaxHp).getLevel();
    }

    public void setMaxHpLvl(int maxHpLvl) {

        this.stats.get(PlayerStatsType.MaxHp).setLevel(maxHpLvl);
    }

    public int getMoveSpdLvl() {
        return this.stats.get(PlayerStatsType.MoveSpd).getLevel();
    }

    public void setMoveSpdLvl(int moveSpdLvl) {
        this.stats.get(PlayerStatsType.MoveSpd).setLevel(moveSpdLvl);
    }

    public int getDurationLvl(){
        return stats.get(PlayerStatsType.Duration).getLevel();
    }

    public void setDurationLvl(int level){
        stats.get(PlayerStatsType.Duration).setLevel(level);
    }

    public int getMightLvl(){
        return stats.get(PlayerStatsType.Might).getLevel();
    }

    public void setMightLvl(int level){
        stats.get(PlayerStatsType.Might).setLevel(level);
    }

    public int getTotalBought(){
        return totalBought;
    }


}
