package Menu;

public class StatItem {
    private String name;
    private int level;
    private int maxLevel;
    private int initialPrice;
    private StatStoreList list;

    public StatItem(String name, int level, int maxLevel, int initialPrice, StatStoreList list){
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.initialPrice = initialPrice;
        this.list = list;
    }

    public int getLevel() {
        return level;
    }

    public boolean setLevel(int level) {
        if(level > maxLevel) return false;
        list.totalBought -= this.level;
        list.totalBought += level;
        this.level = level;
        return true;
    }

    public int getMaxLevel(){
        return maxLevel;
    }

    public boolean raiseLevel(){
        return setLevel(level+1);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice(){
        int basePrice = initialPrice*(1+level);
        int totalBought = list.getTotalBought();
        int fees = level == 0 ? 0 : (int)(20*Math.pow(1.1, list.totalBought));
        return basePrice + fees;
    }
}
