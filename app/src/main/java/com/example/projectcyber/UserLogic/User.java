package com.example.projectcyber.UserLogic;

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

    public int getMaxHpLvl() {
        return stats.getMaxHpLvl();
    }

    public void setMaxHpLvl(int maxHpLvl) {
        this.stats.setMaxHpLvl(maxHpLvl);
    }

    public int getMoveSpdLvl() {
        return this.stats.getMoveSpdLvl();
    }

    public void setMoveSpdLvl(int moveSpdLvl) {
        stats.setMoveSpdLvl(moveSpdLvl);
    }

    public int getDurationLvl(){return this.stats.getDurationLvl();}

    public void setDurationLvl(int durationLvl){stats.setDurationLvl(durationLvl);}

    public int getMightLvl() {return stats.getMightLvl();}

    public void setMightLvl(int mightLvl){stats.setMightLvl(mightLvl);}

    public int getAmountLvl() {return stats.getAmountLvl();}

    public void setAmountLvl(int amountLvl) {stats.setAmountLvl(amountLvl);}

    public int getArmorLvl() {return stats.getArmorLvl();}

    public void setArmorLvl(int armorLvl) {stats.setArmorLvl(armorLvl);}
}
