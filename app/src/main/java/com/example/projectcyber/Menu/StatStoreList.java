package com.example.projectcyber.Menu;

import android.graphics.Rect;

import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class StatStoreList implements Serializable {

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
        stats.put(PlayerStatsType.Amount, new StatItem("Amount", 0, 1, 5000,
                new Stat<>(PlayerStatsType.Amount, 0), new StatModifier(StatModifier.Type.bonus, 1), this));
        stats.put(PlayerStatsType.Armor, new StatItem("Armor", 0, 3, 600,
                new Stat<>(PlayerStatsType.Armor, 0), new StatModifier(StatModifier.Type.bonus, 1),this));
        stats.put(PlayerStatsType.Recovery, new StatItem("Recovery", 0, 5, 200,
                new Stat<>(PlayerStatsType.Recovery, 0), new StatModifier(StatModifier.Type.bonus, 0.1), this));
        stats.put(PlayerStatsType.ProjectileSpd, new StatItem("Projectile Speed", 0, 2, 300,
                new Stat<>(PlayerStatsType.ProjectileSpd, 1), new StatModifier(StatModifier.Type.percentile, 10), this));
        stats.put(PlayerStatsType.Cooldown, new StatItem("Cooldown", 0, 2, 900,
                new Stat<>(PlayerStatsType.Cooldown, 1), new StatModifier(StatModifier.Type.percentile, -5), this));
        stats.put(PlayerStatsType.Magnet, new StatItem("Magnet", 0, 2, 300,
                new Stat<>(PlayerStatsType.Magnet, 300), new StatModifier(StatModifier.Type.percentile, 25), this));
    }

    public ArrayList<StatItem> getList(){
        return new ArrayList<>(stats.values());

    }

    public int getStatLvl(PlayerStatsType type) { return stats.get(type).getLevel();}
    public void setStatLvl(PlayerStatsType type, int level) {stats.get(type).setLevel(level);}

    public int getTotalBought(){
        return totalBought;
    }




}
