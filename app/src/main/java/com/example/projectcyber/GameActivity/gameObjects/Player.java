package com.example.projectcyber.GameActivity.gameObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Represents the player character in the game. Handles movement, XP/leveling,
 * stats management, item and weapon equipment, and health recovery.
 */
public class Player extends Mob {

    private static final int PLAYER_WIDTH = 150;
    private static final int PLAYER_HEIGHT = 150;

    private PlayerStatsContainer stats;
    private HashSet<Weapon> weapons;
    private HashMap<PlayerStatsType, Item> items;

    private final int MAX_EQUIPMENT_PER_TYPE = 3;
    private long lastTimeSinceHeal = 0;

    private int level;
    private int xpRequired;
    private int xpAcquired;

    /**
     * Constructs the Player instance with given initial stats.
     *
     * @param gameView      Reference to the game view.
     * @param startingStats Initial stats for the player.
     */
    public Player(GameView gameView, HashMap<PlayerStatsType, Double> startingStats) {
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

        imgRight = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.player_img);
        imgRight = Bitmap.createScaledBitmap(imgRight, PLAYER_WIDTH, PLAYER_HEIGHT, false);
    }

    /**
     * Adds XP to the player and handles level-up logic.
     *
     * @param xp The XP gained.
     */
    public void raiseXp(int xp) {
        xpAcquired += xp;
        while (xpAcquired >= xpRequired) {
            levelUp();
            xpAcquired -= xpRequired;
            xpRequired += level <= 20 ? 10 : level <= 40 ? 13 : 16;
        }
    }

    /**
     * Triggers the level-up UI and logic.
     */
    public void levelUp() {
        gameView.pauseEntities();
        gameView.showLevelUpDialog();
    }

    public int getMaxEquipmentPerType() {
        return MAX_EQUIPMENT_PER_TYPE;
    }

    public int getXpRequired() {
        return xpRequired;
    }

    public int getXpAcquired() {
        return xpAcquired;
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    /**
     * Updates the player state each frame including movement and healing.
     */
    @Override
    public void update(long deltaTime) {
        savePrevPos();
        Joystick joystick = gameView.getJoystick();
        velX = joystick.getDirX() * getStatValue(PlayerStatsType.MoveSpd);
        velY = joystick.getDirY() * getStatValue(PlayerStatsType.MoveSpd);

        lastTimeSinceHeal += deltaTime;
        while (lastTimeSinceHeal > 1000) {
            heal(getStatValue(PlayerStatsType.Recovery));
            lastTimeSinceHeal -= 1000;
            printEquipment();
        }

        super.update(deltaTime);
        for (Weapon weapon : weapons) {
            weapon.update(deltaTime);
        }
    }

    /**
     * Resolves collision with other entities, including taking damage from enemies.
     */
    @Override
    protected void resolveEntityCollision(Entity entity) {
        super.resolveEntityCollision(entity);
        if (entity instanceof Enemy && !immunityList.inList(entity)) {
            Enemy enemy = (Enemy) entity;
            double damage = enemy.getMight() - getStatValue(PlayerStatsType.Armor);
            if (damage > 0) takeDamage(damage);
        }
    }

    /**
     * Heals the player by a certain amount, not exceeding max HP.
     */
    public void heal(double heal) {
        currHP += heal;
        double maxHp = getStatValue(PlayerStatsType.MaxHp);
        if (currHP > maxHp) {
            currHP = maxHp;
        }
    }

    public double getCurrentHP() {
        return currHP;
    }

    /**
     * Retrieves a stat value with any item bonuses applied.
     */
    public double getStatValue(PlayerStatsType type) {
        if (items.get(type) != null) {
            Item item = items.get(type);
            switch (item.getModifier().getType()) {
                case percentile:
                    return stats.getStat(type).getFinalValue() * item.getAmount();
                case bonus:
                    return stats.getStat(type).getFinalValue() + item.getAmount();
            }
        }
        return stats.getStat(type).getFinalValue();
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
        if (weapon.getLevel() == 0)
            weapon.raiseLevel();
    }

    public void addItem(Item item) {
        items.put(item.getStatType(), item);
        if (item.getLevel() == 0)
            item.raiseLevel();
    }

    public PlayerStatsContainer getStats() {
        return stats;
    }

    public HashSet<Weapon> getWeapons() {
        return weapons;
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public int getAmountOfUpgradableWeapons() {
        int count = 0;
        for (Weapon weapon : weapons) {
            if (weapon.canRaiseLevel()) count++;
        }
        return count;
    }

    public int getAmountOfUpgradableItems() {
        int count = 0;
        for (Item item : getItems()) {
            if (item.canRaiseLevel()) count++;
        }
        return count;
    }

    public int getOpenWeaponSlots() {
        return this.MAX_EQUIPMENT_PER_TYPE - getWeapons().size();
    }

    public int getOpenItemSlots() {
        return MAX_EQUIPMENT_PER_TYPE - getItems().size();
    }

    public boolean haveWeaponSpace() {
        return getOpenWeaponSlots() > 0;
    }

    public boolean haveItemSpace() {
        return getOpenItemSlots() > 0;
    }

    /**
     * Logs the player's current weapons and items.
     */
    public void printEquipment() {
        String str = "\tWeapons : \n";
        for (Weapon weapon : weapons) {
            str += weapon.getName() + " : " + weapon.getLevel() + "\n";
        }
        str += "\titems : \n";
        for (Item item : items.values()) {
            str += item.getName() + " : " + item.getLevel() + "\n";
        }
        Log.d("Equipment", str);
    }

    /**
     * Reduces player HP by the given damage amount and handles player death.
     */
    @Override
    public void takeDamage(double damage) {
        if (damage < 0) return;
        currHP -= damage;
        if (currHP <= 0) {
            currHP = 0;
            playerDeath();
        }
    }

    /**
     * Triggers game-over behavior upon player death.
     */
    private void playerDeath() {
        gameView.showResultDialog(false);
    }
}
