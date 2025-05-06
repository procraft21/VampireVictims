package com.example.projectcyber.GameActivity.Weapons;

import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.Stat;
import com.example.projectcyber.GameActivity.Stats.StatModifier;
import com.example.projectcyber.GameActivity.gameObjects.Player;

import java.util.HashMap;

public class WeaponStatsContainer {
    GameView gameView;

    static HashMap<WeaponStatsType, PlayerStatsType> modifierTable;
    static HashMap<WeaponStatsType, StatModifier.Type> typeTable;

    HashMap<WeaponStatsType, Stat<WeaponStatsType>> stats;

    public WeaponStatsContainer(HashMap<WeaponStatsType, Double> startingStats, GameView gameView){
        if(modifierTable == null){
            setModifierTable();
        }
        if(typeTable == null){
            setTypeTable();
        }
        this.gameView = gameView;
        stats = new HashMap<>();
        stats.put(WeaponStatsType.Duration, new Stat<>(WeaponStatsType.Duration, startingStats.get(WeaponStatsType.Duration)));
        stats.put(WeaponStatsType.Damage, new Stat<>(WeaponStatsType.Damage, startingStats.get(WeaponStatsType.Damage)));
        stats.put(WeaponStatsType.Cooldown, new Stat<>(WeaponStatsType.Cooldown, startingStats.get(WeaponStatsType.Cooldown)));
        stats.put(WeaponStatsType.Speed, new Stat<>(WeaponStatsType.Speed, startingStats.get(WeaponStatsType.Speed)));
        stats.put(WeaponStatsType.Amount, new Stat<>(WeaponStatsType.Amount, startingStats.get(WeaponStatsType.Amount)));
        stats.put(WeaponStatsType.Pierce, new Stat<>(WeaponStatsType.Pierce, startingStats.get(WeaponStatsType.Pierce)));
        stats.put(WeaponStatsType.ProjectileInterval, new Stat<>(WeaponStatsType.ProjectileInterval, startingStats.get(WeaponStatsType.ProjectileInterval)));
    }

    private void setModifierTable(){
        modifierTable = new HashMap<>();
        modifierTable.put(WeaponStatsType.Duration, PlayerStatsType.Duration);
        modifierTable.put(WeaponStatsType.Damage, PlayerStatsType.Might);
        modifierTable.put(WeaponStatsType.Amount,PlayerStatsType.Amount);
    }

    private void setTypeTable(){
        typeTable = new HashMap<>();
        typeTable.put(WeaponStatsType.Duration, StatModifier.Type.percentile);
        typeTable.put(WeaponStatsType.Damage, StatModifier.Type.percentile);
        typeTable.put(WeaponStatsType.Amount, StatModifier.Type.bonus);
    }

    public double getStatValue(WeaponStatsType type){
        Player player = gameView.getPlayer();
        if(modifierTable.get(type) != null && player != null){
            switch (typeTable.get(type)){
                case percentile:
                    return stats.get(type).getFinalValue() * player.getStat(modifierTable.get(type)).getFinalValue();
                case bonus:
                    return stats.get(type).getFinalValue() + player.getStat(modifierTable.get(type)).getFinalValue();
            }
        }
        return stats.get(type).getFinalValue();
    }
}
