package com.example.projectcyber.GameActivity.Weapons;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.util.HashMap;

public class WeaponStatsContainer {
    Player player;

    static HashMap<WeaponStatsType, PlayerStatsType> modifierTable;

    HashMap<WeaponStatsType, Stat<WeaponStatsType>> stats;

    public WeaponStatsContainer(HashMap<WeaponStatsType, Double> startingStats, Player player){
        if(modifierTable == null){
            setModifierTable();
        }
        this.player = player;
        stats = new HashMap<>();
        stats.put(WeaponStatsType.Duration, new Stat<>(WeaponStatsType.Duration, startingStats.get(WeaponStatsType.Duration)));
        stats.put(WeaponStatsType.Damage, new Stat<>(WeaponStatsType.Damage, startingStats.get(WeaponStatsType.Damage)));
    }

    private void setModifierTable(){
        modifierTable = new HashMap<>();
        modifierTable.put(WeaponStatsType.Duration, PlayerStatsType.Duration);
        modifierTable.put(WeaponStatsType.Damage, PlayerStatsType.Might);
    }

    public double getStatValue(WeaponStatsType type){
        if(modifierTable.get(type) != null)
            return stats.get(type).getFinalValue() * player.getStat(modifierTable.get(type)).getFinalValue();
        return stats.get(type).getFinalValue();
    }
}
