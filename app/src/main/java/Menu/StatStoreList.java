package Menu;

import java.util.ArrayList;

import UserLogic.User;

public class StatStoreList {

    enum Stats{
        MaxHp, MoveSpd
    }
    private ArrayList<StatItem> stats;
    protected int totalBought;

    public StatStoreList(){
        totalBought = 0;
        stats = new ArrayList<>();
        stats.add(new StatItem("Max HP", 0, 3, 200, this));
        stats.add(new StatItem("Movement Speed", 0, 2, 300, this));
    }

    public ArrayList<StatItem> getList(){
        return stats;
    }

    public int getMaxHpLvl() {
        return stats.get(Stats.MaxHp.ordinal()).getLevel();
    }

    public void setMaxHpLvl(int maxHpLvl) {

        this.stats.get(Stats.MaxHp.ordinal()).setLevel(maxHpLvl);
    }

    public int getMoveSpdLvl() {
        return this.stats.get(Stats.MoveSpd.ordinal()).getLevel();
    }

    public void setMoveSpdLvl(int moveSpdLvl) {
        this.stats.get(Stats.MoveSpd.ordinal()).setLevel(moveSpdLvl);
    }

    public int getTotalBought(){
        return totalBought;
    }


}
