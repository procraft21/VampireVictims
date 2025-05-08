package com.example.projectcyber.UserLogic;

import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

import com.example.projectcyber.Menu.StatItem;
import com.example.projectcyber.Menu.StatStoreList;

public class User {
    private int coins;
    private StatStoreList stats;

    public User(){
        coins = 0;
        stats = new StatStoreList();
    }

    @Exclude
    public ArrayList<StatItem> getStats(){

        return stats.getList();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    //The getters and setters for the firebase

    public int getMaxHpLvl() {
        return stats.getStatLvl(PlayerStatsType.MaxHp);
    }
    public void setMaxHpLvl(int maxHpLvl) {
        this.stats.setStatLvl(PlayerStatsType.MaxHp, maxHpLvl);
    }

    public int getMoveSpdLvl() {
        return stats.getStatLvl(PlayerStatsType.MoveSpd);
    }
    public void setMoveSpdLvl(int level) {
        stats.setStatLvl(PlayerStatsType.MoveSpd, level);
    }

    public int getDurationLvl() {
        return stats.getStatLvl(PlayerStatsType.Duration);
    }
    public void setDurationLvl(int level) {
        stats.setStatLvl(PlayerStatsType.Duration, level);
    }

    public int getMightLvl() {
        return stats.getStatLvl(PlayerStatsType.Might);
    }
    public void setMightLvl(int level) {
        stats.setStatLvl(PlayerStatsType.Might, level);
    }

    public int getAmountLvl() {
        return stats.getStatLvl(PlayerStatsType.Amount);
    }
    public void setAmountLvl(int level) {
        stats.setStatLvl(PlayerStatsType.Amount, level);
    }

    public int getArmorLvl() {
        return stats.getStatLvl(PlayerStatsType.Armor);
    }
    public void setArmorLvl(int level) {
        stats.setStatLvl(PlayerStatsType.Armor, level);
    }

    public int getRecoveryLvl() {return stats.getStatLvl(PlayerStatsType.Recovery);}
    public void setRecoveryLvl(int level) {stats.setStatLvl(PlayerStatsType.Recovery, level);}

    public int getProjectileSpdLvl() {return stats.getStatLvl(PlayerStatsType.ProjectileSpd);}
    public void setProjectileSpdLvl(int level) {stats.setStatLvl(PlayerStatsType.ProjectileSpd, level);}

    public int getCooldownLvl() {return stats.getStatLvl(PlayerStatsType.Cooldown);}
    public void setCooldownLvl(int level) {stats.setStatLvl(PlayerStatsType.Cooldown, level);}

    public int getMagnetLvl() {return stats.getStatLvl(PlayerStatsType.Magnet);}
    public void setMagnetLvl(int level) {stats.setStatLvl(PlayerStatsType.Magnet, level);}
}
