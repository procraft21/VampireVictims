package UserLogic;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.HashMap;

import Menu.StatItem;
import Menu.StatStoreList;

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
}
