package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.projectcyber.GameActivity.GameView;
import com.example.projectcyber.GameActivity.Equipment.Items.Item;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsType;
import com.example.projectcyber.GameActivity.Stats.PlayerStatsContainer;
import com.example.projectcyber.GameActivity.Equipment.Weapons.Weapon;
import com.example.projectcyber.GameActivity.gameObjects.Enemy.Enemy;
import com.example.projectcyber.GameActivity.uiObjects.Joystick;
import com.example.projectcyber.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends Mob{


    private static final int PLAYER_WIDTH = 150;
    private static final int PLAYER_HEIGHT = 150;

    private Bitmap bitmap;

    private PlayerStatsContainer stats;

    private HashSet<Weapon> weapons;
    private HashMap<PlayerStatsType,Item> items;

    private final int MAX_WEAPONS = 3;
    private final int MAX_ITEMS = 3;

    private long lastTimeSinceHeal = 0;

    private int level;

    private int xpRequired;
    private int xpAcquired;


    public Player(GameView gameView, HashMap<PlayerStatsType, Double> startingStats){
        super(0, 0, gameView);
        gameView.addToGrid(this);
        mass = Double.POSITIVE_INFINITY;

        weapons = new HashSet<>();
        items = new HashMap<>();

        stats = new PlayerStatsContainer(startingStats);
        currHP = getStatValue(PlayerStatsType.MaxHp);



        this.level = 1;
        this.xpAcquired = 0;
        this.xpRequired = 5;

        if(bitmap == null){
            bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.player_img);
            bitmap = Bitmap.createScaledBitmap(bitmap,PLAYER_WIDTH, PLAYER_HEIGHT, false);
        }

    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void raiseXp(int xp){
        xpAcquired += xp;
        while(xpAcquired >= xpRequired){
            levelUp();
            xpAcquired-=xpRequired;
            xpRequired += level<=20 ? 10 : level<=40 ? 13 : 16;
        }
    }

    public void levelUp(){
        gameView.pauseEntities();
        gameView.showLevelUpDialog();
    }

    public int getXpRequired(){
        return xpRequired;
    }

    public int getXpAcquired(){
        return xpAcquired;
    }

    @Override
    protected void drawRelative(Canvas canvas, double relX, double relY) {
        canvas.drawBitmap(bitmap, (int)(relX - bitmap.getWidth()/2),(int)(relY - bitmap.getHeight()/2), null);
    }

    public int getHeight(){
        return bitmap.getHeight();
    }

    public int getWidth(){
        return bitmap.getWidth();
    }

    @Override
    public void update(long deltaTime) {
        savePrevPos();
        Joystick joystick = gameView.getJoystick();
        velX = joystick.getDirX() * getStatValue(PlayerStatsType.MoveSpd);
        velY = joystick.getDirY() * getStatValue(PlayerStatsType.MoveSpd);



        lastTimeSinceHeal += deltaTime;
        while(lastTimeSinceHeal > 1000){
            heal(getStatValue(PlayerStatsType.Recovery));
            lastTimeSinceHeal -= 1000;
            printEquipment();
        }

        super.update(deltaTime);
        for(Weapon weapon : weapons){
            weapon.update(deltaTime);
        }


    }

    @Override
    public double getCollisionRadius() {
        return bitmap.getWidth()/2;
    }

    @Override
    protected void resolveEntityCollision(Entity entity){
        super.resolveEntityCollision(entity);
        if(entity instanceof Enemy && !immunityList.inList(entity)){
            Enemy enemy = (Enemy)entity;
            double damage = enemy.getMight() - getStatValue(PlayerStatsType.Armor);
            if(damage > 0) takeDamage(damage);
        }
    }

    public void heal(double heal){
        currHP += heal;
        if(currHP > getStatValue(PlayerStatsType.MaxHp)){
            currHP = getStatValue(PlayerStatsType.MaxHp);
        }
    }


    public double getCurrentHP(){
        return currHP;
    }

    public double getStatValue(PlayerStatsType type) {

        if(items.get(type) != null){
            Item item = items.get(type);
            switch (item.getModifier().getType()){
                case percentile:
                    return stats.getStat(type).getFinalValue() * item.getAmount();
                case bonus:
                    return stats.getStat(type).getFinalValue() + item.getAmount();

            }
        }
        return stats.getStat(type).getFinalValue();

    }

    public void addWeapon(Weapon weapon){
        weapons.add(weapon);
        if(weapon.getLevel() == 0)
            weapon.raiseLevel();
    }

    public void addItem(Item item){
        items.put(item.getStatType(), item);
        if(item.getLevel() == 0)
            item.raiseLevel();
    }

    public PlayerStatsContainer getStats(){
        return stats;
    }

    public HashSet<Weapon> getWeapons() {
        return weapons;
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public int getAmountOfUpgradableWeapons(){
        int count = 0;
        for(Weapon weapon : weapons){
            if(weapon.canRaiseLevel()) count++;
        }
        return count;
    }

    public int getAmountOfUpgradableItems(){
        int count = 0;
        for(Item item : getItems()){
            if(item.canRaiseLevel()) count++;
        }
        return count;
    }

    public int getOpenWeaponSlots(){
        return this.MAX_WEAPONS - getWeapons().size();
    }

    public int getOpenItemSlots(){
        return MAX_ITEMS - getItems().size();
    }

    public boolean haveSpace(){
        return getOpenItemSlots() + getOpenWeaponSlots() > 0;
    }

    public void printEquipment(){
        String str = "\tWeapons : \n";
        for(Weapon weapon : weapons){
            str += weapon.getName() + " : " + weapon.getLevel() + "\n";
        }
        str += "\titems : \n";
        for(Item item : items.values()){
            str += item.getName() + " : " + item.getLevel() + "\n";
        }
        Log.d("Equipment", str);

    }

    @Override
    public void takeDamage(double damage) {
        if(damage < 0)
            return;
        currHP -= damage;
        if(currHP <= 0){
            currHP = 0;
            playerDeath();
        }
    }

    private void playerDeath(){
        gameView.pauseEntities();
        gameView.showResultDialog(false);
    }
}

