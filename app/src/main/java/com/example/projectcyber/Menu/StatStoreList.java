package com.example.projectcyber.Menu;

import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.Stats.StatType;

import java.util.ArrayList;
import java.util.HashMap;

public class StatStoreList {

    private HashMap<StatType, StatItem> stats;
    protected int totalBought;

    public StatStoreList(){
        totalBought = 0;
        stats = new HashMap<>();
        stats.put(StatType.MaxHp,new StatItem("Max HP", 0, 3, 200,
                new Stat(StatType.MaxHp, 100), new StatModifier(StatModifier.Type.percentile, 10), this));
        stats.put(StatType.MoveSpd, new StatItem("Movement Speed", 0, 2, 300,
                new Stat(StatType.MoveSpd, 200), new StatModifier(StatModifier.Type.percentile, 15),this));
    }

    public ArrayList<StatItem> getList(){
        return new ArrayList<StatItem>(stats.values());
    }

    public int getMaxHpLvl() {
        return stats.get(StatType.MaxHp).getLevel();
    }

    public void setMaxHpLvl(int maxHpLvl) {

        this.stats.get(StatType.MaxHp).setLevel(maxHpLvl);
    }

    public int getMoveSpdLvl() {
        return this.stats.get(StatType.MoveSpd).getLevel();
    }

    public void setMoveSpdLvl(int moveSpdLvl) {
        this.stats.get(StatType.MoveSpd).setLevel(moveSpdLvl);
    }

    public int getTotalBought(){
        return totalBought;
    }


}
